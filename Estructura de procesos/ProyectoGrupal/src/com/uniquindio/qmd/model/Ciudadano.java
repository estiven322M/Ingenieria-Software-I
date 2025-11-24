package com.uniquindio.qmd.model;

public class Ciudadano {
    private String cedula;
    private String nombre;
    private String password;

    public Ciudadano(String cedula, String nombre, String password) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.password = password;
    }
    // Getters
    public String getCedula() { return cedula; }
    public String getNombre() { return nombre; }
}
