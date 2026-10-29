package com.taller.backend.controller;

import com.taller.backend.model.Estudiante;
import com.taller.backend.service.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping
    public ResponseEntity<List<Estudiante>> obtenerTodos() {
        List<Estudiante> estudiantes = estudianteService.obtenerTodos();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerPorId(@PathVariable Long id) {
        return estudianteService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Estudiante> crearEstudiante(@RequestBody Estudiante estudiante) {
        Estudiante nuevo = estudianteService.crearEstudiante(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarEstudiante(
            @PathVariable Long id,
            @RequestBody Estudiante datosActualizados) {

        return estudianteService.actualizarEstudiante(id, datosActualizados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        if (estudianteService.eliminarEstudiante(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
