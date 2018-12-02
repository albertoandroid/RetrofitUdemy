package com.androiddesdecero.retrofitudemy.model;

/**
 * Created by albertopalomarrobledo on 2/12/18.
 */

public class Curso {
    private Long id;
    private String nombre;
    private Long profesorId;

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

    public Long getProfesorId() {
        return profesorId;
    }

    public void setProfesorId(Long profesorId) {
        this.profesorId = profesorId;
    }
}
