package com.wcamprog.apispringdev.application;

import com.wcamprog.apispringdev.domain.Franquicia;
import com.wcamprog.apispringdev.domain.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ObtenerFranquiciaUseCase {
    private final FranquiciaRepositoryPort repository;

    public Mono<Franquicia> ejecutar(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Franquicia no encontrada")));
    }
}
