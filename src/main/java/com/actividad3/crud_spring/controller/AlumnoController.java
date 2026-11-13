package com.actividad3.crud_spring.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.actividad3.crud_spring.dto.AlumnoRequest;
import com.actividad3.crud_spring.dto.AlumnoResponse;
import com.actividad3.crud_spring.dto.PaginaResponse;
import com.actividad3.crud_spring.service.AlumnoService;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {
    private final AlumnoService service;

    public AlumnoController(AlumnoService service) {
        this.service = service;
    }

    // GET /api/alumnos?page=0&size=5
    @GetMapping
    public ResponseEntity<PaginaResponse<AlumnoResponse>> listar(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscar(id));
    }

    @PostMapping
    public ResponseEntity<AlumnoResponse> crear(@Valid @RequestBody AlumnoRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoResponse> actualizar(@PathVariable Long id,
                                                     @Valid @RequestBody AlumnoRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}