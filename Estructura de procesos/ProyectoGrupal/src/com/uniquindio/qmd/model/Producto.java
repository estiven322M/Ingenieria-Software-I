package com.uniquindio.qmd.model;

public class Producto {
    private String codigo;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String estado;

    public Producto(String codigo, String nombre, String descripcion, String categoria, String estado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.estado = estado;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getCategoria() { return categoria; }
    public String getEstado() { return estado; }
    
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return nombre;
    }
}
