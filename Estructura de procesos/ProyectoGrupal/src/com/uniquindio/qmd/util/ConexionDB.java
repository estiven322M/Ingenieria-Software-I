package com.uniquindio.qmd.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionDB {
    
    // Base de datos en archivo local "qmd_reservas"
    private static final String URL = "jdbc:h2:./data/qmd_reservas;DB_CLOSE_ON_EXIT=FALSE;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static Connection conectar() {
        Connection conn = null;
        try {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void inicializarBD() {
        String sqlCiudadano = "CREATE TABLE IF NOT EXISTS ciudadanos ("
                + "cedula VARCHAR(20) PRIMARY KEY, "
                + "nombre VARCHAR(100) NOT NULL, "
                + "password VARCHAR(50) NOT NULL)";

        String sqlProducto = "CREATE TABLE IF NOT EXISTS productos ("
                + "codigo VARCHAR(20) PRIMARY KEY, "
                + "nombre VARCHAR(100) NOT NULL, "
                + "estado VARCHAR(20) NOT NULL)"; // Disponible, Reservado

        String sqlReserva = "CREATE TABLE IF NOT EXISTS reservas ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "fecha DATE, "
                + "cedula_ciudadano VARCHAR(20), "
                + "codigo_producto VARCHAR(20), "
                + "estado VARCHAR(20), "
                + "FOREIGN KEY (cedula_ciudadano) REFERENCES ciudadanos(cedula), "
                + "FOREIGN KEY (codigo_producto) REFERENCES productos(codigo))";

        try (Connection conn = conectar(); Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCiudadano);
            stmt.execute(sqlProducto);
            stmt.execute(sqlReserva);
            
            // Datos semilla (si no existen productos)
            var rs = stmt.executeQuery("SELECT count(*) FROM productos");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("Inicializando datos de prueba...");
                stmt.execute("INSERT INTO productos VALUES ('OBJ-001', 'Taladro Percutor', 'Disponible')");
                stmt.execute("INSERT INTO productos VALUES ('OBJ-002', 'Escalera Telescópica', 'Disponible')");
                stmt.execute("INSERT INTO productos VALUES ('OBJ-003', 'Proyector HD', 'Prestado')");
                // Usuario de prueba
                stmt.execute("INSERT INTO ciudadanos VALUES ('123', 'Usuario Prueba', '123')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
