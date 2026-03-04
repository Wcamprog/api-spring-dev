package com.wcamprog.apispringdev.application;

import com.wcamprog.apispringdev.domain.Franquicia;
import com.wcamprog.apispringdev.domain.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EliminarProductoUseCase {

    private final FranquiciaRepositoryPort repository;

    public Mono<Franquicia> ejecutar(String franquiciaId, String sucursalId, String productoId) {
        return repository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franquicia no encontrada")))
                .flatMap(franquicia -> {
                    var sucursal = franquicia.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));

                    boolean removed = sucursal.getProductos().removeIf(p -> p.getId().equals(productoId));
                    if (!removed) throw new NotFoundException("Producto no encontrado");

                    return repository.save(franquicia);
                });
    }

}
