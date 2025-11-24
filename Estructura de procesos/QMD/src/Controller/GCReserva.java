package Controller;
import Domain.Ciudadano;
import Domain.Producto;
import Domain.Reserva;

public class GCReserva {

    /**
     * Este método corresponde al mensaje 'crearReserva' 
     */
    public boolean crearReserva(String cedula, String codigoProducto) {
        System.out.println("--- Controlador (GCReserva) Iniciando proceso ---");

        // 1. buscar() a la entidad Ciudadano
        System.out.println("1. GCReserva -> Ciudadano: buscar(" + cedula + ")");
        Ciudadano ciudadano = new Ciudadano(cedula); 

        // 2. validarDisponibilidad() a la entidad Producto
        System.out.println("2. GCReserva -> Producto: validarDisponibilidad(" + codigoProducto + ")");
        Producto producto = new Producto(codigoProducto); 
        
     // --- PRUEBA ---
        // Si el código ingresado es "NO-DISP", forzamos el estado a "Ocupado"
        if (codigoProducto.equalsIgnoreCase("NO-DISP")) {
            producto.setEstado("Ocupado"); 
            System.out.println("   [Simulación] El producto se encontró en estado: Ocupado");
        }
        
        if (producto.estaDisponible()) {
            // 3. Fragmento 'Alt': Crear la reserva
            System.out.println("3. Validación Exitosa. Creando reserva...");
            Reserva nuevaReserva = new Reserva(ciudadano, producto);
            
            return true; // Retorna éxito a la vista
        } else {
            System.out.println("3. Fallo: Producto no disponible.");
            return false;
        }
    }
}
