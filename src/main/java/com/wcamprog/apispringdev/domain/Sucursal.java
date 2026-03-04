package com.wcamprog.apispringdev.domain;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sucursal {
    @NotBlank
    private String id;
    @NotBlank
    private String nombre;

    @Builder.Default
    private List<Producto> productos = new ArrayList<>();
}
