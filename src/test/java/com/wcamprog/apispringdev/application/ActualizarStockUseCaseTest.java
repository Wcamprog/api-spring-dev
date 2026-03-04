package com.wcamprog.apispringdev.application;

import com.wcamprog.apispringdev.domain.Franquicia;
import com.wcamprog.apispringdev.domain.Producto;
import com.wcamprog.apispringdev.domain.Sucursal;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ActualizarStockUseCaseTest {

    @Test
    void ejecutar_actualizaStock_yGuarda() {

        FranquiciaRepositoryPort repo = mock(FranquiciaRepositoryPort.class);

        var producto = Producto.builder().id("p1").nombre("P1").stock(10).build();
        var sucursal = Sucursal.builder()
                .id("s1").nombre("S1")
                .productos(new ArrayList<>(List.of(producto)))
                .build();

        var franquicia = Franquicia.builder()
                .id("f1").nombre("F1")
                .sucursales(new ArrayList<>(List.of(sucursal)))
                .build();

        when(repo.findById("f1")).thenReturn(Mono.just(franquicia));
        when(repo.save(any(Franquicia.class))).thenAnswer(inv -> Mono.just(inv.getArgument(0)));

        var useCase = new ActualizarStockUseCase(repo);

        // Act + Assert
        StepVerifier.create(useCase.ejecutar("f1", "s1", "p1", 99))
                .assertNext(f -> {
                    var p = f.getSucursales().get(0).getProductos().get(0);
                    org.assertj.core.api.Assertions.assertThat(p.getStock()).isEqualTo(99);
                })
                .verifyComplete();

        verify(repo).findById("f1");
        verify(repo).save(any(Franquicia.class));
        verifyNoMoreInteractions(repo);
    }
}