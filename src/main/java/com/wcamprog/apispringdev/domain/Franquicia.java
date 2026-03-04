package com.wcamprog.apispringdev.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("franquicias")
public class Franquicia {

    @Id
    private String id;

    @NotBlank
    private String nombre;

    @Builder.Default
    private List<Sucursal> sucursales = new ArrayList<>();
}