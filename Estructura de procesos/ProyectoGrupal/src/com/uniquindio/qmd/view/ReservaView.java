package com.uniquindio.qmd.view;

import com.uniquindio.qmd.controller.ModelController;
import com.uniquindio.qmd.model.Ciudadano;
import com.uniquindio.qmd.model.Producto;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class ReservaView extends JFrame {
    private JComboBox<Producto> cbProductos;
    private JTextField txtEstado;
    private Ciudadano ciudadanoActual;
    private ModelController controller;

    public ReservaView(Ciudadano ciudadano, ModelController controller) {
        this.ciudadanoActual = ciudadano;
        this.controller = controller;

        setTitle("QMD - Diligenciar Reserva - Usuario: " + ciudadano.getNombre());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Componentes
        add(new JLabel("  Fecha Reserva:"));
        JTextField txtFecha = new JTextField(LocalDate.now().toString());
        txtFecha.setEditable(false);
        add(txtFecha);

        add(new JLabel("  Cédula Ciudadano:"));
        JTextField txtCed = new JTextField(ciudadano.getCedula());
        txtCed.setEditable(false);
        add(txtCed);

        add(new JLabel("  Seleccionar Objeto:"));
        cbProductos = new JComboBox<>();
        cargarProductos(); // Carga desde BD
        add(cbProductos);

        add(new JLabel("  Estado Objeto:"));
        txtEstado = new JTextField();
        txtEstado.setEditable(false);
        add(txtEstado);

        JButton btnReservar = new JButton("Confirmar Reserva");
        JButton btnSalir = new JButton("Salir");
        add(new JLabel("")); add(new JLabel("")); // Espacio
        add(btnReservar); add(btnSalir);

        // Eventos
        cbProductos.addActionListener(e -> {
            Producto p = (Producto) cbProductos.getSelectedItem();
            if (p != null) txtEstado.setText(p.getEstado());
        });

        btnReservar.addActionListener(e -> {
            Producto p = (Producto) cbProductos.getSelectedItem();
            String resultado = controller.crearReserva(ciudadano.getCedula(), p);
            JOptionPane.showMessageDialog(this, resultado);
            if (resultado.startsWith("ÉXITO")) {
                cargarProductos(); // Refrescar lista para mostrar cambios
            }
        });

        btnSalir.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });
        
        // Inicializar estado del primer item
        if(cbProductos.getItemCount() > 0) {
            cbProductos.setSelectedIndex(0);
        }
    }

    private void cargarProductos() {
        cbProductos.removeAllItems();
        List<Producto> productos = controller.obtenerProductos();
        for (Producto p : productos) {
            cbProductos.addItem(p);
        }
    }
}
