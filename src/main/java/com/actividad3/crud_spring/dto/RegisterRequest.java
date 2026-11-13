package com.actividad3.crud_spring.dto;

import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre admite máximo 100 caracteres")
        String nombre,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no es válido")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
                 message = "Mínimo 8 caracteres, una mayúscula, un número y un carácter especial")
        String password) {
}