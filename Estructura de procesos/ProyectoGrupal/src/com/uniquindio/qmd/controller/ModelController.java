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

	// --- Login y usuarios
	public Ciudadano login(String cedula, String password) {
		String sql = "SELECT * FROM ciudadanos WHERE cedula = ? AND password = ?";
		try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, cedula);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return new Ciudadano(rs.getString("cedula"), rs.getString("nombre"), rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean registrarCiudadanoCompleto(String cedula, String nombre, String apellido, String direccion,
			String telefono, String email, String genero, String fechaNac, String password) {
		String sql = "INSERT INTO ciudadanos (cedula, nombre, apellido, direccion, telefono, email, genero, fecha_nacimiento, password) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, cedula);
			pstmt.setString(2, nombre);
			pstmt.setString(3, apellido);
			pstmt.setString(4, direccion);
			pstmt.setString(5, telefono);
			pstmt.setString(6, email);
			pstmt.setString(7, genero);

			// Validación básica de fecha para evitar errores
			try {
				if (fechaNac == null || fechaNac.trim().isEmpty()) {
					pstmt.setDate(8, Date.valueOf("2000-01-01")); // Fecha por defecto
				} else {
					pstmt.setDate(8, Date.valueOf(fechaNac));
				}
			} catch (IllegalArgumentException ex) {
				System.err.println("--> ERROR FECHA: " + fechaNac + " (Formato incorrecto, se requiere YYYY-MM-DD)");
				return false;
			}

			pstmt.setString(9, password);
			pstmt.executeUpdate();

			return true;
		} catch (SQLException e) {
			System.err.println("--> ERROR SQL AL REGISTRAR: " + e.getMessage()); 
			e.printStackTrace();
			return false;
		}
	}

	// ---CRUD productos

	// READ
	public List<Producto> obtenerProductos(String filtro) {
		List<Producto> lista = new ArrayList<>();
		String sql = "SELECT * FROM productos WHERE LOWER(nombre) LIKE ? OR LOWER(codigo) LIKE ?";
		try (Connection conn = ConexionDB.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			String query = "%" + filtro.toLowerCase() + "%";
			pstmt.setString(1, query);
			pstmt.setString(2, query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				lista.add(new Producto(rs.getString("codigo"), rs.getString("nombre"), rs.getString("descripcion"),
						rs.getString("categoria"), rs.getString("estado")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	// CREATE
	public boolean crearProducto(Producto p) {
		String sql = "INSERT INTO productos (codigo, nombre, descripcion, categoria, estado) VALUES (?, ?, ?, ?, ?)";
		try (Connection conn = ConexionDB.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, p.getCodigo());
			ps.setString(2, p.getNombre());
			ps.setString(3, p.getDescripcion());
			ps.setString(4, p.getCategoria());
			ps.setString(5, p.getEstado());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println("Error Crear: " + e.getMessage());
			return false;
		}
	}

	// UPDATE
	public boolean actualizarProducto(Producto p) {
		String sql = "UPDATE productos SET nombre=?, descripcion=?, categoria=?, estado=? WHERE codigo=?";
		try (Connection conn = ConexionDB.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, p.getNombre());
			ps.setString(2, p.getDescripcion());
			ps.setString(3, p.getCategoria());
			ps.setString(4, p.getEstado());
			ps.setString(5, p.getCodigo());
			int filas = ps.executeUpdate();
			return filas > 0;
		} catch (SQLException e) {
			System.err.println("Error Actualizar: " + e.getMessage());
			return false;
		}
	}

	// DELETE
	public boolean eliminarProducto(String codigo) {
		// Primero verificamos si tiene reservas asociadas para evitar errores de
		// integridad
		if (tieneReservasActivas(codigo)) {
			return false;
		}

		String sql = "DELETE FROM productos WHERE codigo = ?";
		try (Connection conn = ConexionDB.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, codigo);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	private boolean tieneReservasActivas(String codigoProducto) {
		String sql = "SELECT count(*) FROM reservas WHERE codigo_producto = ?";
		try (Connection conn = ConexionDB.conectar(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, codigoProducto);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return rs.getInt(1) > 0;
		} catch (SQLException e) {
		}
		return false;
	}

	public String crearReserva(String cedula, Producto producto) {
		if (!"Disponible".equalsIgnoreCase(producto.getEstado())) {
			return "Error: El producto no está disponible.";
		}
		String sqlReserva = "INSERT INTO reservas (fecha, cedula_ciudadano, codigo_producto, estado) VALUES (?, ?, ?, ?)";
		String sqlUpdateProd = "UPDATE productos SET estado = 'Reservado' WHERE codigo = ?";

		try (Connection conn = ConexionDB.conectar()) {
			conn.setAutoCommit(false);
			try (PreparedStatement pstmtRes = conn.prepareStatement(sqlReserva);
					PreparedStatement pstmtProd = conn.prepareStatement(sqlUpdateProd)) {

				pstmtRes.setDate(1, Date.valueOf(LocalDate.now()));
				pstmtRes.setString(2, cedula);
				pstmtRes.setString(3, producto.getCodigo());
				pstmtRes.setString(4, "Solicitada");
				pstmtRes.executeUpdate();

				pstmtProd.setString(1, producto.getCodigo());
				pstmtProd.executeUpdate();

				conn.commit();
				return "¡Reserva guardada exitosamente!";
			} catch (SQLException e) {
				conn.rollback();
				return "Error: " + e.getMessage();
			}
		} catch (SQLException e) {
			return "Error DB: " + e.getMessage();
		}
	}

}
