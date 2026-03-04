package com.wcamprog.apispringdev.application;

import com.wcamprog.apispringdev.domain.Franquicia;
import com.wcamprog.apispringdev.domain.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ActualizarNombreFranquiciaUseCase {

    private final FranquiciaRepositoryPort repository;

    public Mono<Franquicia> ejecutar(String franquiciaId, String nuevoNombre) {
        return repository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franquicia no encontrada")))
                .flatMap(franquicia -> {
                    franquicia.setNombre(nuevoNombre);
                    return repository.save(franquicia);
                });
    }
}