package ru.gb.gbapi.config;

import feign.*;
import feign.codec.ErrorDecoder;
import feign.optionals.OptionalDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static feign.FeignException.errorStatus;

@Service
@RequiredArgsConstructor
public class FeignFactory {

    private final GbApiProperties gbApiProperties;
    private final ObjectFactory<HttpMessageConverters> messageConverters;


    public <T> T create(Class<T> type, String url) {
        return Feign.builder()
                .encoder(new SpringEncoder(this.messageConverters))
                .decoder(new OptionalDecoder(new ResponseEntityDecoder(new SpringDecoder(this.messageConverters))))
                .errorDecoder(errorDecoder())
                .options(new Request.Options(
                        gbApiProperties.getConnection().getConnectTimeout(), TimeUnit.SECONDS,
                        gbApiProperties.getConnection().getReadTimeout(), TimeUnit.SECONDS,
                        true
                ))
                .logger(new Slf4jLogger(type))
                .logLevel(Logger.Level.FULL)
                .retryer(new Retryer.Default(
                        gbApiProperties.getConnection().getPeriod(),
                        gbApiProperties.getConnection().getMaxPeriod(),
                        gbApiProperties.getConnection().getMaxAttempts()
                ))
                .contract(new SpringMvcContract())
                .target(type, url);
    }

    private ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            FeignException exception = errorStatus(methodKey, response);
            if (exception.status() == 500 || exception.status() == 503) {
                return new RetryableException(
                        response.status(),
                        exception.getMessage(),
                        response.request().httpMethod(),
                        exception,
                        null,
                        response.request());
            }
            return exception;
        };
    }
}
