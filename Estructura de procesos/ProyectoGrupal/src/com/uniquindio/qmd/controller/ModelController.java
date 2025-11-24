package com.uniquindio.qmd.controller;

import com.uniquindio.qmd.model.Ciudadano;
import com.uniquindio.qmd.model.Producto;
import com.uniquindio.qmd.util.ConexionDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModelController {

    public ModelController() {
        ConexionDB.inicializarBD();
    }

    // --- Gestión de Ciudadanos ---
    public Ciudadano login(String cedula, String password) {
        String sql = "SELECT * FROM ciudadanos WHERE cedula = ? AND password = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cedula);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Ciudadano(rs.getString("cedula"), rs.getString("nombre"), rs.getString("password"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean registrarCiudadano(String cedula, String nombre, String password) {
        String sql = "INSERT INTO ciudadanos (cedula, nombre, password) VALUES (?, ?, ?)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cedula);
            pstmt.setString(2, nombre);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) { return false; }
    }

    // --- Gestión de Productos ---
    public List<Producto> obtenerProductos() {
        List<Producto> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM productos");
            while (rs.next()) {
                lista.add(new Producto(rs.getString("codigo"), rs.getString("nombre"), rs.getString("estado")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }

    // --- Gestión de Reservas (Transacción) ---
    public String crearReserva(String cedula, Producto producto) {
        if (!"Disponible".equalsIgnoreCase(producto.getEstado())) {
            return "Error: El producto no está disponible.";
        }

        String sqlReserva = "INSERT INTO reservas (fecha, cedula_ciudadano, codigo_producto, estado) VALUES (?, ?, ?, ?)";
        String sqlUpdateProd = "UPDATE productos SET estado = 'Reservado' WHERE codigo = ?";

        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false); // Transacción

            try (PreparedStatement pstmtRes = conn.prepareStatement(sqlReserva);
                 PreparedStatement pstmtProd = conn.prepareStatement(sqlUpdateProd)) {
                
                // 1. Insertar Reserva
                pstmtRes.setDate(1, Date.valueOf(LocalDate.now()));
                pstmtRes.setString(2, cedula);
                pstmtRes.setString(3, producto.getCodigo());
                pstmtRes.setString(4, "Solicitada");
                pstmtRes.executeUpdate();

                // 2. Actualizar Producto
                pstmtProd.setString(1, producto.getCodigo());
                pstmtProd.executeUpdate();

                conn.commit(); // Confirmar
                return "¡Reserva creada con éxito! Código: RES-" + System.currentTimeMillis();
            } catch (SQLException e) {
                conn.rollback();
                return "Error en transacción: " + e.getMessage();
            }
        } catch (SQLException e) {
            return "Error de conexión: " + e.getMessage();
        }
    }
}
