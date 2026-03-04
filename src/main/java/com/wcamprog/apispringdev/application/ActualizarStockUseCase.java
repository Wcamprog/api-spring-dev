package com.wcamprog.apispringdev.application;

import com.wcamprog.apispringdev.domain.Franquicia;
import com.wcamprog.apispringdev.domain.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ActualizarStockUseCase {
    private final FranquiciaRepositoryPort repository;
    public Mono<Franquicia> ejecutar(String franquiciaId, String sucursalId, String productoId, Integer nuevoStock) {
        return repository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franquicia no encontrada")))
                .flatMap(franquicia -> {
                    var sucursal = franquicia.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundException("Sucursal no encontrada"));

                    var producto = sucursal.getProductos().stream()
                            .filter(p -> p.getId().equals(productoId))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

                    producto.setStock(nuevoStock);

                    return repository.save(franquicia);
                });
    }
}
