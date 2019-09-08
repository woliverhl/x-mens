package com.demo.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate() {

        SimpleClientHttpRequestFactory req = new SimpleClientHttpRequestFactory();
        req.setConnectTimeout(10000);
        req.setReadTimeout(10000);

        return new RestTemplate(req);
    }

}
