package ru.gb.gbapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.gb.gbapi.category.api.CategoryGateway;
import ru.gb.gbapi.manufacturer.api.ManufacturerGateway;
import ru.gb.gbapi.product.api.ProductGateway;

@Configuration
@EnableFeignClients
@EnableConfigurationProperties(GbApiProperties.class)
@RequiredArgsConstructor
@Import(value = {FeignFactory.class})
public class FeignConfig {

    private final GbApiProperties gbApiProperties;
    private final FeignFactory feignFactory;

    @Bean
    public CategoryGateway categoryGateway() {
        return feignFactory.create(CategoryGateway.class, gbApiProperties.getEndpoint().getCategoryUrl());
    }

    @Bean
    public ManufacturerGateway manufacturerGateway() {
        return feignFactory.create(ManufacturerGateway.class, gbApiProperties.getEndpoint().getManufacturerUrl());
    }

    @Bean
    public ProductGateway productGateway() {
        return feignFactory.create(ProductGateway.class, gbApiProperties.getEndpoint().getProductUrl());
    }

}
