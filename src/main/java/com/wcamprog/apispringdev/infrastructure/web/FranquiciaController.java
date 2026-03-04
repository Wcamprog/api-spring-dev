package com.wcamprog.apispringdev.infrastructure.web;

import com.wcamprog.apispringdev.application.*;
import com.wcamprog.apispringdev.domain.Franquicia;
import com.wcamprog.apispringdev.infrastructure.web.dto.ProductoMaxStockPorSucursalUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/franquicias")
@RequiredArgsConstructor
@Validated
public class FranquiciaController {

    private final CrearFranquiciaUseCase crearFranquiciaUseCase;
    private final AgregarSucursalUseCase agregarSucursalUseCase;
    private final AgregarProductoUseCase agregarProductoUseCase;
    private final ActualizarStockUseCase actualizarStockUseCase;
    private final EliminarProductoUseCase eliminarProductoUseCase;
    private final ProductoMaxStockPorSucursalUseCase productoMaxStockPorSucursalUseCase;
    private final ListarFranquiciasUseCase listarFranquiciasUseCase;
    private final ObtenerFranquiciaUseCase obtenerFranquiciaUseCase;
    private final ActualizarNombreFranquiciaUseCase actualizarNombreFranquiciaUseCase;
    private final ActualizarNombreSucursalUseCase actualizarNombreSucursalUseCase;
    private final ActualizarNombreProductoUseCase actualizarNombreProductoUseCase;


    public record CrearFranquiciaRequest(@NotBlank String nombre) {}
    public record AgregarSucursalRequest(@NotBlank String nombre) {}
    public record AgregarProductoRequest(
            @NotBlank String nombre,
            Integer stock
    ) {}
    public record ActualizarStockRequest(Integer stock) {}
    public record ActualizarNombreRequest(@NotBlank String nombre) {}


    @PostMapping
    public Mono<Franquicia> crear(@RequestBody @Valid CrearFranquiciaRequest request) {
        return crearFranquiciaUseCase.ejecutar(request.nombre());
    }

    @GetMapping
    public Flux<Franquicia> listar() {
        return listarFranquiciasUseCase.ejecutar();
    }

    @GetMapping("{franquiciaId}")
    public Mono<Franquicia> obtener(@PathVariable String franquiciaId) {
        return obtenerFranquiciaUseCase.ejecutar(franquiciaId);
    }

    @PostMapping("/{id}/sucursales")
    public Mono<Franquicia> agregarSucursal(
            @PathVariable String id,
            @RequestBody @Valid AgregarSucursalRequest request) {

        return agregarSucursalUseCase.ejecutar(id, request.nombre());
    }

    @PostMapping("/{franquiciaId}/sucursales/{sucursalId}/productos")
    public Mono<Franquicia> agregarProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @RequestBody @Valid AgregarProductoRequest request) {

        return agregarProductoUseCase.ejecutar(
                franquiciaId,
                sucursalId,
                request.nombre(),
                request.stock()
        );
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock")
    public Mono<Franquicia> actualizarStock(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @RequestBody @Valid ActualizarStockRequest request
    ) {
        return actualizarStockUseCase.ejecutar(franquiciaId, sucursalId, productoId, request.stock());
    }


    @DeleteMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}")
    public Mono<Franquicia> eliminarProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId
    ) {
        return eliminarProductoUseCase.ejecutar(franquiciaId, sucursalId, productoId);
    }

    @GetMapping("/{franquiciaId}/productos/max-stock-por-sucursal")
    public Flux<ProductoMaxStockPorSucursalUseCase.Resultado> maxStockPorSucursal(@PathVariable String franquiciaId) {
        return productoMaxStockPorSucursalUseCase.ejecutar(franquiciaId);
    }

    @PatchMapping("/{franquiciaId}/nombre")
    public Mono<Franquicia> actualizarNombreFranquicia(
            @PathVariable String franquiciaId,
            @RequestBody @jakarta.validation.Valid ActualizarNombreRequest request
    ) {
        return actualizarNombreFranquiciaUseCase.ejecutar(franquiciaId, request.nombre());
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/nombre")
    public Mono<Franquicia> actualizarNombreSucursal(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @RequestBody @jakarta.validation.Valid ActualizarNombreRequest request
    ) {
        return actualizarNombreSucursalUseCase.ejecutar(franquiciaId, sucursalId, request.nombre());
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/nombre")
    public Mono<Franquicia> actualizarNombreProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @RequestBody @jakarta.validation.Valid ActualizarNombreRequest request
    ) {
        return actualizarNombreProductoUseCase.ejecutar(franquiciaId, sucursalId, productoId, request.nombre());
    }


}
