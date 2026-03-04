package com.wcamprog.apispringdev.application;

import com.wcamprog.apispringdev.domain.*;
import com.wcamprog.apispringdev.domain.Sucursal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgregarProductoUseCase {

    private final FranquiciaRepositoryPort repository;

    public Mono<Franquicia> ejecutar(String franquiciaId,
        String sucursalId, String nombreProducto, Integer stock
    ){
        return repository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NotFoundException("No se encontro el franquicia")))
                .flatMap(franquicia -> {

                    Sucursal sucursal = franquicia.getSucursales().stream()
                            .filter(s -> s.getId().equals(sucursalId))
                            .findFirst()
                            .orElseThrow(() -> new NotFoundException("No se encontró la Sucursal"));

                    Producto nuevoProducto = Producto.builder()
                            .id(UUID.randomUUID().toString())
                            .nombre(nombreProducto)
                            .stock(stock)
                            .build();

                    sucursal.getProductos().add(nuevoProducto);

                    return repository.save(franquicia);
                });
    }

}
