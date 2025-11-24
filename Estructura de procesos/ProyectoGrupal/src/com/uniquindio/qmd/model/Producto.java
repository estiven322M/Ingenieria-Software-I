package com.uniquindio.qmd.model;

public class Producto {
    private String codigo;
    private String nombre;
    private String estado;

    public Producto(String codigo, String nombre, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
    }
    // Getters y toString para el JComboBox
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getEstado() { return estado; }
    
    @Override
    public String toString() {
        return nombre + " (" + estado + ")";
    }
}
