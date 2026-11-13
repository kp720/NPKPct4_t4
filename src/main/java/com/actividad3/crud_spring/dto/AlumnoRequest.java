package com.actividad3.crud_spring.dto;

import jakarta.validation.constraints.*;

public record AlumnoRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 100, message = "El nombre admite máximo 100 caracteres")
        String nombre,

        @NotBlank(message = "La matrícula es obligatoria")
        @Size(max = 30, message = "La matrícula admite máximo 30 caracteres")
        String matricula,

        @NotNull(message = "La escuela es obligatoria")
        Long escuelaId) {
}