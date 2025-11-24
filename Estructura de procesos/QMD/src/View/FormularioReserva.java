package View;

import  Controller.GCReserva;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// :VReserva
public class FormularioReserva extends JFrame {

    private JTextField txtFecha, txtCedula, txtNombreObjeto, txtEstado;
    private JButton btnGuardar, btnCancelar;
    
    // Referencia al Controller (Asociación)
    private GCReserva controlador;

    public FormularioReserva() {
        // Inicia Controller
        controlador = new GCReserva();
        
        inicializarComponentes(); 
    }

    // Este método corresponde al mensaje 'diligenciarReserva()' del actor
    private void accionGuardar() {
        String cedula = txtCedula.getText();
        String codigo = txtNombreObjeto.getText();

        if (cedula.isEmpty() || codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Faltan datos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        // El View delega al Controller. 
        // Mensaje: VReserva -> GCReserva : crearReserva(...)
        boolean exito = controlador.crearReserva(cedula, codigo);

        if (exito) {
            // Respuesta final del sistema
            JOptionPane.showMessageDialog(this, "¡Reserva Exitosa!");
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo reservar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtCedula.setText("");
        txtNombreObjeto.setText("");
    }
    
    private void inicializarComponentes() {
        setTitle("QMD - Diligenciar Reserva");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("  Fecha de reserva:"));
        txtFecha = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
        txtFecha.setEditable(false);
        add(txtFecha);

        add(new JLabel("  Cédula ciudadano:"));
        txtCedula = new JTextField();
        add(txtCedula);

        add(new JLabel("  Código producto:"));
        txtNombreObjeto = new JTextField();
        add(txtNombreObjeto);

        add(new JLabel("  Estado:"));
        txtEstado = new JTextField("Reservado");
        txtEstado.setEditable(false);
        add(txtEstado);

        btnGuardar = new JButton("Guardar reserva");
        btnCancelar = new JButton("Cancelar");
        
        add(new JLabel("")); add(new JLabel(""));
        add(btnGuardar); add(btnCancelar);

        btnGuardar.addActionListener(e -> accionGuardar());
        btnCancelar.addActionListener(e -> limpiarCampos());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormularioReserva().setVisible(true));
    }
}
