package com.actividad3.crud_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.actividad3.crud_spring.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}