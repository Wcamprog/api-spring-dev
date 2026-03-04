package com.wcamprog.apispringdev.infrastructure.persistence;

import com.wcamprog.apispringdev.application.FranquiciaRepositoryPort;
import com.wcamprog.apispringdev.domain.Franquicia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class FranquiciaRepositoryAdapter implements FranquiciaRepositoryPort {

    private final FranquiciaReactiveRepository repo;

    @Override
    public Mono<Franquicia> save(Franquicia franquicia) {
        return repo.save(franquicia);
    }

    @Override
    public Mono<Franquicia> findById(String id) {
        return repo.findById(id);
    }

    @Override
    public Flux<Franquicia> findAll() {
        return repo.findAll();
    }

}
