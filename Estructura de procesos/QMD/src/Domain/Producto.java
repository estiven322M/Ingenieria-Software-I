package Domain;

public class Producto {
    private String codigo;
    private String estado;

    public Producto(String codigo) { 
        this.codigo = codigo; 
        this.estado = "Disponible"; // Estado inicial
    }
    
    public void setEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }
    
    public String getEstado() {
        return this.estado;
    }
    
    public boolean estaDisponible() {
        return "Disponible".equals(this.estado);
    }
}
