package com.actividad3.crud_spring.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no es válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        String password) {
}