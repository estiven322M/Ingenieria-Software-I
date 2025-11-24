package com.uniquindio.qmd.view;

import com.uniquindio.qmd.controller.ModelController;
import javax.swing.*;
import java.awt.*;

public class RegistroView extends JFrame {
    public RegistroView(ModelController controller) {
        setTitle("Registro Nuevo Ciudadano");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 5, 5));

        JTextField txtCed = new JTextField();
        JTextField txtNom = new JTextField();
        JPasswordField txtPass = new JPasswordField();
        JButton btnGuardar = new JButton("Guardar");

        add(new JLabel("  Cédula:")); add(txtCed);
        add(new JLabel("  Nombre:")); add(txtNom);
        add(new JLabel("  Contraseña:")); add(txtPass);
        add(new JLabel("")); add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            if(controller.registrarCiudadano(txtCed.getText(), txtNom.getText(), new String(txtPass.getPassword()))) {
                JOptionPane.showMessageDialog(this, "Registro exitoso. Ya puede ingresar.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error: La cédula ya existe.");
            }
        });
    }
}
