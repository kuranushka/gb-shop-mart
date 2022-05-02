package ru.gb.gbshopmart.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JmsProperties {

    @Value("${gb-jms.properties.message.link}")
    private String orderChangedQueue;
}

