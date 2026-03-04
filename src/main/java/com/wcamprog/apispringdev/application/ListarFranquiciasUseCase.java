package com.wcamprog.apispringdev.application;

import com.wcamprog.apispringdev.domain.Franquicia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ListarFranquiciasUseCase {
    private final FranquiciaRepositoryPort repository;

    public Flux<Franquicia> ejecutar() {
        return repository.findAll();
    }
}
