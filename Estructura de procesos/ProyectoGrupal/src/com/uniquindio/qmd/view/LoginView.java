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
        setTitle("QMD - Acceso Ciudadano");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JPanel pnlCed = new JPanel(); pnlCed.add(new JLabel("Cédula:")); 
        txtCedula = new JTextField(15); pnlCed.add(txtCedula);
        
        JPanel pnlPass = new JPanel(); pnlPass.add(new JLabel("Clave:")); 
        txtPass = new JPasswordField(15); pnlPass.add(txtPass);

        JButton btnLogin = new JButton("Ingresar");
        JButton btnReg = new JButton("Registrarse");

        add(new JLabel("Bienvenido a QMD", SwingConstants.CENTER));
        add(pnlCed);
        add(pnlPass);
        
        JPanel pnlBtn = new JPanel();
        pnlBtn.add(btnLogin); pnlBtn.add(btnReg);
        add(pnlBtn);

        // Eventos
        btnLogin.addActionListener(e -> {
            String ced = txtCedula.getText();
            String pass = new String(txtPass.getPassword());
            Ciudadano c = controller.login(ced, pass);
            if (c != null) {
                new ReservaView(c, controller).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
            }
        });

        btnReg.addActionListener(e -> {
            new RegistroView(controller).setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}
