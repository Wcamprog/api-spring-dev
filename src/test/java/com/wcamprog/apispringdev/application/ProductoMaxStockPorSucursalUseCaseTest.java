package com.wcamprog.apispringdev.application;

import com.wcamprog.apispringdev.domain.Franquicia;
import com.wcamprog.apispringdev.domain.Producto;
import com.wcamprog.apispringdev.domain.Sucursal;
import com.wcamprog.apispringdev.infrastructure.web.dto.ProductoMaxStockPorSucursalUseCase;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class ProductoMaxStockPorSucursalUseCaseTest {

    @Test
    void ejecutar_devuelveProductoMayorStock_yNullSiNoHayProductos() {
        // Arrange
        FranquiciaRepositoryPort repo = mock(FranquiciaRepositoryPort.class);

        var s1 = Sucursal.builder()
                .id("s1").nombre("S1")
                .productos(new ArrayList<>(List.of(
                        Producto.builder().id("p1").nombre("P1").stock(10).build(),
                        Producto.builder().id("p2").nombre("P2").stock(50).build()
                )))
                .build();

        var s2 = Sucursal.builder()
                .id("s2").nombre("S2")
                .productos(new ArrayList<>()) // sin productos => nulls
                .build();

        var franquicia = Franquicia.builder()
                .id("f1").nombre("F1")
                .sucursales(new ArrayList<>(List.of(s1, s2)))
                .build();

        when(repo.findById("f1")).thenReturn(Mono.just(franquicia));

        var useCase = new ProductoMaxStockPorSucursalUseCase(repo);

        // Act + Assert
        StepVerifier.create(useCase.ejecutar("f1").collectList())
                .assertNext(list -> {
                    // S1 => P2
                    var r1 = list.get(0);
                    org.assertj.core.api.Assertions.assertThat(r1.sucursalId()).isEqualTo("s1");
                    org.assertj.core.api.Assertions.assertThat(r1.productoNombre()).isEqualTo("P2");
                    org.assertj.core.api.Assertions.assertThat(r1.stock()).isEqualTo(50);

                    // S2 => null (porque no hay productos)
                    var r2 = list.get(1);
                    org.assertj.core.api.Assertions.assertThat(r2.sucursalId()).isEqualTo("s2");
                    org.assertj.core.api.Assertions.assertThat(r2.productoId()).isNull();
                    org.assertj.core.api.Assertions.assertThat(r2.productoNombre()).isNull();
                    org.assertj.core.api.Assertions.assertThat(r2.stock()).isNull();
                })
                .verifyComplete();

        verify(repo).findById("f1");
        verifyNoMoreInteractions(repo);
    }
}