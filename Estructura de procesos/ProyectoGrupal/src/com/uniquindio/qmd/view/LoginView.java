package com.uniquindio.qmd.view;

import com.uniquindio.qmd.controller.ModelController;
import com.uniquindio.qmd.model.Ciudadano;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private ModelController controller;
    private JTextField txtCedula;
    private JPasswordField txtPass;

    public LoginView() {
        controller = new ModelController();
        setTitle("QMD - Acceso al Sistema");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Bienvenido a QMD");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        // Inputs
        gbc.gridwidth = 1; gbc.gridy = 1;
        add(new JLabel("Cédula:"), gbc);
        
        gbc.gridx = 1;
        txtCedula = new JTextField(15);
        add(txtCedula, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Contraseña:"), gbc);
        
        gbc.gridx = 1;
        txtPass = new JPasswordField(15);
        add(txtPass, gbc);

        // Botones
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(new Color(63, 81, 181));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(100, 40));
        btnLogin.setFocusPainted(false);
        add(btnLogin, gbc);

        gbc.gridy = 4;
        JButton btnReg = new JButton("Registrar nuevo ciudadano");
        btnReg.setBorderPainted(false);
        btnReg.setContentAreaFilled(false);
        btnReg.setForeground(Color.BLUE);
        btnReg.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btnReg, gbc);

        // Eventos
        btnLogin.addActionListener(e -> {
            String ced = txtCedula.getText().trim();
            String pass = new String(txtPass.getPassword());
            
            // Lógica de Roles
            
            // 1. ADMINISTRADOR (Backdoor o credenciales fijas)
            if(ced.equals("admin") && pass.equals("admin123")) {
                // Abre la nueva vista de gestión para el Administrador
                new GestionProductosView(controller).setVisible(true);
                dispose(); // Cierra la ventana de login
                return;
            }

            // 2. CIUDADANO 
            Ciudadano c = controller.login(ced, pass);
            if (c != null) {
                // Abre la vista de catálogo para ciudadanos
                new CatalogoView(c, controller).setVisible(true);
                dispose(); // Cierra la ventana de login
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnReg.addActionListener(e -> {
            new RegistroView(controller).setVisible(true);
        });
    }

    public static void main(String[] args) {
        // Look and Feel del sistema para que los bordes se vean mejor
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
