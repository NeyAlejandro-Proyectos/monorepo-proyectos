package com.taller.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa un Estudiante en la base de datos.
 *
 * @Entity   : le indica a JPA que esta clase es una tabla en la BD
 * @Table    : (opcional) personaliza el nombre de la tabla
 */
@Entity
@Table(name = "estudiantes")
public class Estudiante {

    /**
     * Clave primaria auto-generada por la base de datos (IDENTITY strategy).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo del estudiante. No puede ser nulo.
     */
    @Column(nullable = false)
    private String nombre;

    /**
     * Correo electronico unico del estudiante.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Nombre del curso o grado al que pertenece el estudiante.
     */
    @Column(nullable = false)
    private String curso;

    // ----------------------------------------------------------------
    // Constructores
    // ----------------------------------------------------------------

    /** Constructor vacio requerido por JPA. */
    public Estudiante() {}

    /** Constructor de conveniencia para crear instancias rapidamente. */
    public Estudiante(String nombre, String email, String curso) {
        this.nombre = nombre;
        this.email  = email;
        this.curso  = curso;
    }

    // ----------------------------------------------------------------
    // Getters y Setters
    // ----------------------------------------------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    // ----------------------------------------------------------------
    // toString (util para depuracion)
    // ----------------------------------------------------------------

    @Override
    public String toString() {
        return "Estudiante{" +
               "id="     + id     +
               ", nombre='" + nombre + '\'' +
               ", email='"  + email  + '\'' +
               ", curso='"  + curso  + '\'' +
               '}';
    }
}
