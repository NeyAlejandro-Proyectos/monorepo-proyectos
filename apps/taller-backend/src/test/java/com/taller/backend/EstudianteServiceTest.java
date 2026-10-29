package com.taller.backend;

import com.taller.backend.model.Estudiante;
import com.taller.backend.repository.EstudianteRepository;
import com.taller.backend.service.EstudianteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de integracion basicas para {@link EstudianteService}.
 *
 * @SpringBootTest carga el contexto completo de Spring (incluyendo H2 en memoria),
 * permitiendo probar la logica junto con la base de datos real.
 */
@SpringBootTest
class EstudianteServiceTest {

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @BeforeEach
    void limpiarBaseDeDatos() {
        // Asegura un estado limpio antes de cada prueba
        estudianteRepository.deleteAll();
    }

    @Test
    @DisplayName("Debe crear y recuperar un estudiante correctamente")
    void testCrearYObtenerEstudiante() {
        // ARRANGE
        Estudiante nuevo = new Estudiante("Ana Lopez", "ana@taller.com", "10A");

        // ACT
        Estudiante guardado = estudianteService.crearEstudiante(nuevo);
        Optional<Estudiante> encontrado = estudianteService.obtenerPorId(guardado.getId());

        // ASSERT
        assertTrue(encontrado.isPresent());
        assertEquals("Ana Lopez",     encontrado.get().getNombre());
        assertEquals("ana@taller.com", encontrado.get().getEmail());
        assertEquals("10A",            encontrado.get().getCurso());
    }

    @Test
    @DisplayName("Debe retornar todos los estudiantes")
    void testObtenerTodos() {
        estudianteService.crearEstudiante(new Estudiante("Pedro Ruiz",   "pedro@taller.com", "9B"));
        estudianteService.crearEstudiante(new Estudiante("Maria Gomez",  "maria@taller.com", "10A"));

        List<Estudiante> lista = estudianteService.obtenerTodos();

        assertEquals(2, lista.size());
    }

    @Test
    @DisplayName("Debe actualizar un estudiante existente")
    void testActualizarEstudiante() {
        Estudiante original  = estudianteService.crearEstudiante(new Estudiante("Carlos V", "carlos@taller.com", "8C"));
        Estudiante cambios   = new Estudiante("Carlos Vera", "carlos.vera@taller.com", "9C");

        Optional<Estudiante> actualizado = estudianteService.actualizarEstudiante(original.getId(), cambios);

        assertTrue(actualizado.isPresent());
        assertEquals("Carlos Vera",            actualizado.get().getNombre());
        assertEquals("carlos.vera@taller.com", actualizado.get().getEmail());
    }

    @Test
    @DisplayName("Debe eliminar un estudiante existente")
    void testEliminarEstudiante() {
        Estudiante estudiante = estudianteService.crearEstudiante(new Estudiante("Luis M", "luis@taller.com", "11D"));

        boolean eliminado = estudianteService.eliminarEstudiante(estudiante.getId());

        assertTrue(eliminado);
        assertFalse(estudianteService.obtenerPorId(estudiante.getId()).isPresent());
    }

    @Test
    @DisplayName("Debe retornar false al intentar eliminar un id inexistente")
    void testEliminarEstudianteNoExistente() {
        boolean resultado = estudianteService.eliminarEstudiante(9999L);
        assertFalse(resultado);
    }
}
