package com.androiddesdecero.retrofitudemy.model;

/**
 * Created by albertopalomarrobledo on 1/12/18.
 */

public class Profesor {

    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String foto;

    public Profesor(){

    }

    public Profesor(Long id, String nombre, String email, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.foto = foto;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
