package com.wcamprog.apispringdev.domain;

import lombok.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @NotBlank
    private String id;
    @NotBlank
    private String nombre;

    @Min(0)
    private Integer stock;

}
