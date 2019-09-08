package com.demo.consumer.controller;

import com.demo.consumer.service.SequenceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class SequenceController {

    private static final String API_REST_V_1_TRAFFIC = "/api/rest/v1";

    @Bean
    public RouterFunction<ServerResponse> router(SequenceService service) {
        return route(POST(API_REST_V_1_TRAFFIC + "/mutant"), service::mutant)
                .andRoute(GET(API_REST_V_1_TRAFFIC + "/stats"), service::stats)
                .andRoute(GET(API_REST_V_1_TRAFFIC + "/health"), service::health);
    }

}
