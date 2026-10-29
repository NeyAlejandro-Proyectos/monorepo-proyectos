package com.taller.backend.service;

import com.taller.backend.model.Estudiante;
import com.taller.backend.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Capa de Servicio para la entidad {@link Estudiante}.
 *
 * Esta clase concentra toda la logica de negocio. El controlador
 * nunca accede directamente al repositorio; siempre pasa por aqui.
 *
 * @Service : marca el bean para que Spring lo gestione como componente de servicio
 */
@Service
public class EstudianteService {

    // Inyeccion de dependencias por constructor (practica recomendada)
    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    // ----------------------------------------------------------------
    // CREATE
    // ----------------------------------------------------------------

    /**
     * Guarda un nuevo estudiante en la base de datos.
     *
     * @param estudiante objeto con los datos a persistir
     * @return el estudiante guardado (con el id generado)
     */
    public Estudiante crearEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    // ----------------------------------------------------------------
    // READ
    // ----------------------------------------------------------------

    /**
     * Retorna la lista completa de todos los estudiantes.
     */
    public List<Estudiante> obtenerTodos() {
        return estudianteRepository.findAll();
    }

    /**
     * Busca un estudiante por su id.
     *
     * @param id identificador unico
     * @return Optional con el estudiante, o vacio si no existe
     */
    public Optional<Estudiante> obtenerPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    /**
     * Busca un estudiante por su email.
     *
     * @param email correo electronico del estudiante
     * @return Optional con el estudiante, o vacio si no existe
     */
    public Optional<Estudiante> obtenerPorEmail(String email) {
        return estudianteRepository.findByEmail(email);
    }

    /**
     * Retorna todos los estudiantes de un curso especifico.
     *
     * @param curso nombre del curso a filtrar
     */
    public List<Estudiante> obtenerPorCurso(String curso) {
        return estudianteRepository.findByCurso(curso);
    }

    // ----------------------------------------------------------------
    // UPDATE
    // ----------------------------------------------------------------

    /**
     * Actualiza los datos de un estudiante existente.
     *
     * Estrategia: busca el registro, actualiza sus campos y vuelve a guardar.
     *
     * @param id                 id del estudiante a actualizar
     * @param datosActualizados  objeto con los nuevos valores
     * @return el estudiante actualizado, o Optional vacio si no se encontro
     */
    public Optional<Estudiante> actualizarEstudiante(Long id, Estudiante datosActualizados) {
        return estudianteRepository.findById(id).map(estudianteExistente -> {
            estudianteExistente.setNombre(datosActualizados.getNombre());
            estudianteExistente.setEmail(datosActualizados.getEmail());
            estudianteExistente.setCurso(datosActualizados.getCurso());
            return estudianteRepository.save(estudianteExistente);
        });
    }

    // ----------------------------------------------------------------
    // DELETE
    // ----------------------------------------------------------------

    /**
     * Elimina un estudiante por su id.
     *
     * @param id identificador del estudiante a eliminar
     * @return true si fue eliminado, false si no existia
     */
    public boolean eliminarEstudiante(Long id) {
        if (estudianteRepository.existsById(id)) {
            estudianteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
