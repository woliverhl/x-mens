package com.demo.consumer.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface SequenceService {

    Mono<ServerResponse> mutant(final ServerRequest request);

    Mono<ServerResponse> stats(final ServerRequest request);

    Mono<ServerResponse> health(final ServerRequest request);

}
