package com.uniquindio.qmd.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    
    // Base de datos en archivo local "qmd_reservas"
	private static final String URL = "jdbc:h2:./data/qmd_base_v3"; 
    private static final String USER = "sa";
    private static final String PASS = "";
    
    private static void crearCarpetaData() {
        File directorio = new File("./data");
        if (!directorio.exists()) {
            if (directorio.mkdir()) {
                System.out.println("--> Carpeta './data' creada para la base de datos local.");
            }
        }
    }
    
   
   public static Connection conectar() {
	   crearCarpetaData(); // Verificar carpeta antes de conectar
        Connection conn = null;
        try {
        	// Carga del driver H2
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
        	System.err.println("--> ERROR CRITICO: No se encontro el Driver H2 (h2-*.jar).");
            System.err.println("    Asegurate de exportar el JAR usando 'Extract required libraries'.");
            e.printStackTrace();
        } catch(SQLException e) {
        	System.err.println("--> ERROR SQL: No se pudo establecer conexión con: " + URL);
            e.printStackTrace();
        }
        return conn;
    }

    public static void inicializarBD() {
    	Connection conn = conectar();
    	
    	if (conn == null) {
            System.err.println("--> FATAL: La conexión es nula. No se puede inicializar la BD.");
            return;
        }
    	
    	// Tabla Ciudadanos con las 9 columnas necesarias
        String sqlCiudadano = "CREATE TABLE IF NOT EXISTS ciudadanos ("
                + "cedula VARCHAR(20) PRIMARY KEY, "
                + "nombre VARCHAR(100), "
                + "apellido VARCHAR(100), "
                + "direccion VARCHAR(255), " // Aumenté el tamaño por si acaso
                + "telefono VARCHAR(20), "
                + "email VARCHAR(100), "
                + "genero VARCHAR(20), "
                + "fecha_nacimiento DATE, "
                + "password VARCHAR(50))";

        String sqlProducto = "CREATE TABLE IF NOT EXISTS productos ("
                + "codigo VARCHAR(20) PRIMARY KEY, "
                + "nombre VARCHAR(100), "
                + "descripcion VARCHAR(200), "
                + "categoria VARCHAR(50), "
                + "estado VARCHAR(20))";

        String sqlReserva = "CREATE TABLE IF NOT EXISTS reservas ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "fecha DATE, "
                + "cedula_ciudadano VARCHAR(20), "
                + "codigo_producto VARCHAR(20), "
                + "estado VARCHAR(20), "
                + "FOREIGN KEY (cedula_ciudadano) REFERENCES ciudadanos(cedula), "
                + "FOREIGN KEY (codigo_producto) REFERENCES productos(codigo))";

        try (Statement stmt = conn.createStatement()) {
            System.out.println("--> Verificando/Creando tablas en base de datos local...");
            stmt.execute(sqlCiudadano);
            stmt.execute(sqlProducto);
            stmt.execute(sqlReserva);
            
            // Datos semilla (si no existen productos)
            var rs = stmt.executeQuery("SELECT count(*) FROM productos");
            rs.next();
            if (rs.getInt(1) == 0) {
            	System.out.println("--> Base de datos V2 nueva. Insertando datos...");
            	
            	//Productos
            	stmt.execute("INSERT INTO productos VALUES ('OBJ-001', 'Taladro Percutor', 'Industrial, 500W', 'Herramientas', 'Disponible')");
                stmt.execute("INSERT INTO productos VALUES ('OBJ-002', 'Escalera Telescópica', 'Aluminio, 3 metros', 'Herramientas', 'Prestado')");
                stmt.execute("INSERT INTO productos VALUES ('OBJ-003', 'Proyector HD', 'Epson, con control', 'Tecnología', 'Disponible')");
                stmt.execute("INSERT INTO productos VALUES ('OBJ-004', 'Silla Rimax', 'Plástica, blanca', 'Mobiliario', 'Mantenimiento')");
                
             // Usuarios de prueba (especificando columnas para evitar errores)
                stmt.execute("INSERT INTO ciudadanos (cedula, nombre, password) VALUES ('admin', 'Administrador', 'admin123')");
                stmt.execute("INSERT INTO ciudadanos (cedula, nombre, password) VALUES ('12345', 'Usuario Prueba', '123')");
            }
            System.out.println("--> Inicialización completada con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
        	try {
        		if (conn != null) conn.close(); 
        	}catch(SQLException e){
        		e.printStackTrace(); 
        	}
        }
    }
}
