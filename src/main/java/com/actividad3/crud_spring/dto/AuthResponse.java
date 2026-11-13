package com.actividad3.crud_spring.dto;

public record AuthResponse(String token, String tipo, String email, String nombre) {
}