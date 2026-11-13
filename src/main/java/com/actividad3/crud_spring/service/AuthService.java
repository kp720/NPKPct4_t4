package com.actividad3.crud_spring.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.actividad3.crud_spring.dto.AuthResponse;
import com.actividad3.crud_spring.dto.LoginRequest;
import com.actividad3.crud_spring.dto.RegisterRequest;
import com.actividad3.crud_spring.dto.UsuarioResponse;
import com.actividad3.crud_spring.model.Rol;
import com.actividad3.crud_spring.model.Usuario;
import com.actividad3.crud_spring.repository.UsuarioRepository;
import com.actividad3.crud_spring.security.JwtService;
import com.actividad3.crud_spring.security.UsuarioDetailsService;

@Service
public class AuthService {
    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsuarioDetailsService detailsService;

    public AuthService(UsuarioRepository repo, PasswordEncoder encoder, AuthenticationManager authManager,
                       JwtService jwtService, UsuarioDetailsService detailsService) {
        this.repo = repo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.detailsService = detailsService;
    }

    public UsuarioResponse registrar(RegisterRequest req) {
        String email = req.email().trim().toLowerCase();
        if (repo.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El correo ya está registrado");
        }
        Usuario u = new Usuario();
        u.setNombre(req.nombre().trim());
        u.setEmail(email);
        u.setPassword(encoder.encode(req.password()));
        u.setRol(Rol.USER);
        Usuario guardado = repo.save(u);
        return new UsuarioResponse(guardado.getId(), guardado.getNombre(), guardado.getEmail());
    }

    public AuthResponse login(LoginRequest req) {
        String email = req.email().trim().toLowerCase();
        // Si la contraseña es incorrecta lanza BadCredentialsException (responde 401)
        authManager.authenticate(new UsernamePasswordAuthenticationToken(email, req.password()));
        UserDetails user = detailsService.loadUserByUsername(email);
        Usuario u = repo.findByEmail(email).orElseThrow();
        return new AuthResponse(jwtService.generarToken(user), "Bearer", u.getEmail(), u.getNombre());
    }
}