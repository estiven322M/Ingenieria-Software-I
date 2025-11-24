package com.uniquindio.qmd.view;

import com.uniquindio.qmd.controller.ModelController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class RegistroView extends JFrame {
    
    private ModelController controller;
    // Colores de la imagen
    private Color colorHeader = new Color(63, 81, 181); // Azul indigo
    private Color colorBtn = new Color(80, 70, 230); // Azul violeta botón

    // Componentes globales para poder limpiarlos
    private JTextField txtCedula, txtFecha, txtNombre, txtApellido, txtDireccion, txtTelefono, txtEmail, txtEstado;
    private JPasswordField txtPass;
    private JComboBox<String> cbGenero;

    public RegistroView(ModelController controller) {
        this.controller = controller;
        setTitle("Registro de Nuevo Ciudadano");
        setSize(750, 700); // Un poco más alto para que quepa todo bien
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- HEADER ---
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(colorHeader);
        pnlHeader.setLayout(new BoxLayout(pnlHeader, BoxLayout.Y_AXIS));
        pnlHeader.setBorder(new EmptyBorder(20, 0, 20, 0));

        JLabel lblTitle = new JLabel("Registro de nuevo ciudadano");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Sistema QMD - Gestión de Usuarios");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSub.setForeground(new Color(200, 200, 255));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlHeader.add(lblTitle);
        pnlHeader.add(Box.createVerticalStrut(5));
        pnlHeader.add(lblSub);
        add(pnlHeader, BorderLayout.NORTH);

        // --- FORMULARIO ---
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(new EmptyBorder(20, 40, 20, 40));
        add(new JScrollPane(pnlForm), BorderLayout.CENTER); // Scroll por si la pantalla es pequeña

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 2, 10); // Espaciado (top, left, bottom, right)
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;

        // Inicialización de componentes
        txtCedula = crearInput();
        txtFecha = crearInput(); txtFecha.setText("2000-01-01");
        txtNombre = crearInput();
        txtApellido = crearInput();
        txtDireccion = crearInput();
        txtTelefono = crearInput();
        txtEmail = crearInput();
        txtPass = new JPasswordField();
        estilizarInput(txtPass); 
        
        cbGenero = new JComboBox<>(new String[]{"Seleccionar...", "Masculino", "Femenino", "Otro"});
        cbGenero.setBackground(Color.WHITE);
        cbGenero.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        txtEstado = crearInput();
        txtEstado.setEditable(false);
        txtEstado.setBackground(new Color(245, 245, 245));

        // --- FILA 1: cedula y fecha ---
        addLabel(pnlForm, "Cédula / Identificación", 0, 0, gbc);
        addLabel(pnlForm, "Fecha de nacimiento (YYYY-MM-DD)", 1, 0, gbc);
        
        addComponent(pnlForm, txtCedula, 0, 1, gbc);
        addComponent(pnlForm, txtFecha, 1, 1, gbc);

        // --- FILA 2: nombre y apellido---
        addLabel(pnlForm, "Nombres", 0, 2, gbc);
        addLabel(pnlForm, "Apellidos", 1, 2, gbc);
        
        addComponent(pnlForm, txtNombre, 0, 3, gbc);
        addComponent(pnlForm, txtApellido, 1, 3, gbc);

        // --- FILA 3: direccion ---
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JLabel lblDir = new JLabel("Dirección de residencia");
        lblDir.setFont(new Font("SansSerif", Font.BOLD, 12)); 
        pnlForm.add(lblDir, gbc);
        
        gbc.gridy = 5;
        pnlForm.add(txtDireccion, gbc);
        gbc.gridwidth = 1; 

        // --- FILA 4: teléfono y email ---
        addLabel(pnlForm, "Teléfono / Celular", 0, 6, gbc);
        addLabel(pnlForm, "Correo electrónico", 1, 6, gbc);
        
        addComponent(pnlForm, txtTelefono, 0, 7, gbc);
        addComponent(pnlForm, txtEmail, 1, 7, gbc);

        // --- FILA 5: género y estado---
        addLabel(pnlForm, "Género", 0, 8, gbc);
        addLabel(pnlForm, "Estado", 1, 8, gbc);
        
        addComponent(pnlForm, cbGenero, 0, 9, gbc);
        addComponent(pnlForm, txtEstado, 1, 9, gbc);
        
        // --- FILA 6 contraseña---
        addLabel(pnlForm, "Contraseña (para ingresar)", 0, 10, gbc);
        addComponent(pnlForm, txtPass, 0, 11, gbc);


        // --- BOTONES ---
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlBtns.setBackground(Color.WHITE);

        JButton btnGuardar = new JButton("Registrar Ciudadano");
        estilizarBoton(btnGuardar, colorBtn, Color.WHITE);
        
        JButton btnLimpiar = new JButton("Limpiar");
        estilizarBoton(btnLimpiar, Color.WHITE, Color.BLACK);
        btnLimpiar.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        pnlBtns.add(btnGuardar);
        pnlBtns.add(btnLimpiar);
        add(pnlBtns, BorderLayout.SOUTH);

        // --- LÓGICA DE EVENTOS ---
        
        // Guardar
        btnGuardar.addActionListener(e -> {
            String pass = new String(txtPass.getPassword());
            if(pass.isEmpty()) pass = "123"; 

            boolean exito = controller.registrarCiudadanoCompleto(
                txtCedula.getText(), txtNombre.getText(), txtApellido.getText(), 
                txtDireccion.getText(), txtTelefono.getText(), txtEmail.getText(), 
                (String)cbGenero.getSelectedItem(), txtFecha.getText(), pass
            );

            if(exito) {
                txtEstado.setText("Activo");
                txtEstado.setBackground(new Color(220, 255, 220)); // Verde claro
                JOptionPane.showMessageDialog(this, "Ciudadano registrado con éxito");
                // Opcional: dispose(); si quieres que se cierre al guardar
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar. Verifique que la cédula no exista.");
            }
        });

        // Limpia
        btnLimpiar.addActionListener(e -> {
            txtCedula.setText("");
            txtFecha.setText("2000-01-01");
            txtNombre.setText("");
            txtApellido.setText("");
            txtDireccion.setText("");
            txtTelefono.setText("");
            txtEmail.setText("");
            txtPass.setText("");
            txtEstado.setText("");
            txtEstado.setBackground(new Color(245, 245, 245));
            cbGenero.setSelectedIndex(0);
        });
    }

    // Métodos auxiliares para limpiar el código del constructor
    
    private void addLabel(JPanel p, String text, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x; gbc.gridy = y;
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 12)); 
        p.add(l, gbc);
    }

    private void addComponent(JPanel p, Component c, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x; gbc.gridy = y;
        p.add(c, gbc);
    }

    private JTextField crearInput() {
        JTextField t = new JTextField();
        estilizarInput(t);
        return t;
    }
    
    private void estilizarInput(JTextField t) {
        t.setPreferredSize(new Dimension(200, 35));
        t.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200,200,200)), 
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    private void estilizarBoton(JButton btn, Color bg, Color fg) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(200, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}
