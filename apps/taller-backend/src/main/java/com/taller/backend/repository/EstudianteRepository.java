package com.taller.backend.repository;

import com.taller.backend.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de acceso a datos para la entidad {@link Estudiante}.
 *
 * Al extender {@link JpaRepository}, Spring Data JPA genera automaticamente
 * la implementacion con los metodos CRUD basicos:
 *
 *   save(entity)          -> INSERT / UPDATE
 *   findById(id)          -> SELECT por id
 *   findAll()             -> SELECT *
 *   deleteById(id)        -> DELETE por id
 *   existsById(id)        -> SELECT COUNT(*) > 0
 *   count()               -> SELECT COUNT(*)
 *
 * No es necesario escribir ningun SQL ni implementar nada.
 *
 * @param Estudiante  tipo de la entidad gestionada
 * @param Long        tipo del campo @Id de la entidad
 */
@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    // --- Metodos de consulta personalizados (Spring Data los implementa por convencion de nombre) ---

    /**
     * Busca un estudiante por su email.
     * Spring Data genera: SELECT * FROM estudiantes WHERE email = ?
     */
    java.util.Optional<Estudiante> findByEmail(String email);

    /**
     * Busca todos los estudiantes que pertenecen a un curso especifico.
     * Spring Data genera: SELECT * FROM estudiantes WHERE curso = ?
     */
    java.util.List<Estudiante> findByCurso(String curso);
}
