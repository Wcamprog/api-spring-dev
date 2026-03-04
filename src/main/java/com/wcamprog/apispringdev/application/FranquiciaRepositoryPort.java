package com.wcamprog.apispringdev.application;

import com.wcamprog.apispringdev.domain.Franquicia;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

public interface FranquiciaRepositoryPort {
    Mono<Franquicia> save(Franquicia franquicia);
    Mono<Franquicia> findById(String id);
    Flux<Franquicia> findAll();
}
