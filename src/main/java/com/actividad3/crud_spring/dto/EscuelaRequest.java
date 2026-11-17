package com.actividad3.crud_spring.dto;

import jakarta.validation.constraints.*;

public record EscuelaRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre admite máximo 100 caracteres")
        String nombre,

        @Size(max = 100, message = "La ciudad admite máximo 100 caracteres")
        String ciudad) {
}