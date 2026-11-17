package com.actividad3.crud_spring.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.actividad3.crud_spring.dto.EscuelaRequest;
import com.actividad3.crud_spring.dto.EscuelaResponse;
import com.actividad3.crud_spring.models.Escuela;
import com.actividad3.crud_spring.repository.EscuelaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/escuelas")
public class EscuelaController {

    private final EscuelaRepository repository;

    public EscuelaController(EscuelaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<EscuelaResponse> listar() {
        return repository.findAll().stream().map(this::aResponse).toList();
    }

    @GetMapping("/{id}")
    public EscuelaResponse obtener(@PathVariable Long id) {
        return repository.findById(id)
                .map(this::aResponse)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Escuela no encontrada"));
    }

    @PostMapping
    public ResponseEntity<EscuelaResponse> crear(@Valid @RequestBody EscuelaRequest request) {
        Escuela escuela = new Escuela();
        escuela.setNombre(request.nombre());
        escuela.setCiudad(request.ciudad());
        Escuela guardada = repository.save(escuela);
        return ResponseEntity.status(HttpStatus.CREATED).body(aResponse(guardada));
    }

    private EscuelaResponse aResponse(Escuela e) {
        return new EscuelaResponse(e.getId(), e.getNombre(), e.getCiudad());
    }
}