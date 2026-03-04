package com.wcamprog.apispringdev.infrastructure.web.dto;

import com.wcamprog.apispringdev.application.FranquiciaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductoMaxStockPorSucursalUseCase {

    private final FranquiciaRepositoryPort repository;

    public record Resultado(String sucursalId, String sucursalNombre, String productoId, String productoNombre, Integer stock) {}

    public Flux<Resultado> ejecutar(String franquiciaId) {
        return repository.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new RuntimeException("Franquicia no encontrada")))
                .flatMapMany(franquicia -> Flux.fromIterable(franquicia.getSucursales())
                        .map(sucursal -> {
                            var max = sucursal.getProductos().stream()
                                    .max((a, b) -> Integer.compare(
                                            a.getStock() == null ? 0 : a.getStock(),
                                            b.getStock() == null ? 0 : b.getStock()
                                    ))
                                    .orElse(null);

                            if (max == null) {
                                return new Resultado(sucursal.getId(), sucursal.getNombre(), null, null, null);
                            }
                            return new Resultado(sucursal.getId(), sucursal.getNombre(), max.getId(), max.getNombre(), max.getStock());
                        })
                );
    }

}
