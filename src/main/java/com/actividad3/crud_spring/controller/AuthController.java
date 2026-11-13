package com.actividad3.crud_spring.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.actividad3.crud_spring.dto.AuthResponse;
import com.actividad3.crud_spring.dto.LoginRequest;
import com.actividad3.crud_spring.dto.RegisterRequest;
import com.actividad3.crud_spring.dto.UsuarioResponse;
import com.actividad3.crud_spring.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(req));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(service.login(req));
    }
}