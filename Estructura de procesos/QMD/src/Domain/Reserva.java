package Domain;

import java.util.Date;

public class Reserva {
    private Date fecha;
    private Ciudadano ciudadano;
    private Producto producto;
    private String estado;

    
    public Reserva(Ciudadano ciudadano, Producto producto) { // logica del metodo Reserva
        this.fecha = new Date();
        this.ciudadano = ciudadano;
        this.producto = producto;
        this.estado = "Reservado";
        System.out.println("--> Entidad Reserva");
    }
    
    public String getEstado() { return estado; }
}
