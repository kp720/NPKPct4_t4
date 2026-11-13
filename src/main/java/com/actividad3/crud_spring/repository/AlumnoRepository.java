package com.actividad3.crud_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.actividad3.crud_spring.models.Alumno;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    @EntityGraph(attributePaths = "escuela")
    Page<Alumno> findAll(Pageable pageable);
}