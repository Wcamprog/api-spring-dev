package com.wcamprog.apispringdev.application;

import com.wcamprog.apispringdev.domain.Franquicia;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CrearFranquiciaUseCaseTest {

    @Test
    void ejecutar_creaFranquicia_yGuarda() {
        // Arrange
        FranquiciaRepositoryPort repo = mock(FranquiciaRepositoryPort.class);

        when(repo.save(any(Franquicia.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        CrearFranquiciaUseCase useCase = new CrearFranquiciaUseCase(repo);

        // Act + Assert
        StepVerifier.create(useCase.ejecutar("MiFranquicia"))
                .assertNext(f -> {
                    org.assertj.core.api.Assertions.assertThat(f.getId()).isNotBlank();
                    org.assertj.core.api.Assertions.assertThat(f.getNombre()).isEqualTo("MiFranquicia");
                    org.assertj.core.api.Assertions.assertThat(f.getSucursales()).isNotNull();
                })
                .verifyComplete();

        verify(repo, times(1)).save(any(Franquicia.class));
        verifyNoMoreInteractions(repo);
    }
}