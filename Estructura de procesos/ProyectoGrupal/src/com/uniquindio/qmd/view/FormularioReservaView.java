package com.uniquindio.qmd.view;

import com.uniquindio.qmd.controller.ModelController;
import com.uniquindio.qmd.model.Ciudadano;
import com.uniquindio.qmd.model.Producto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

public class FormularioReservaView extends JDialog {

    private Producto producto;
    private Ciudadano ciudadano;
    private ModelController controller;
    private CatalogoView parent;

    public FormularioReservaView(CatalogoView parent, Ciudadano ciudadano, Producto producto, ModelController controller) {
        super(parent, "Diligenciar Reserva", true);
        this.parent = parent;
        this.ciudadano = ciudadano;
        this.producto = producto;
        this.controller = controller;

        setSize(450, 550);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Panel Blanco tipo tarjeta
        JPanel pnlCard = new JPanel();
        pnlCard.setLayout(new BoxLayout(pnlCard, BoxLayout.Y_AXIS));
        pnlCard.setBackground(Color.WHITE);
        pnlCard.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Título
        JLabel lblTitle = new JLabel("Diligenciar Reserva");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(50, 50, 50));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlCard.add(lblTitle);
        pnlCard.add(Box.createVerticalStrut(20));

        // Campos
        agregarCampo(pnlCard, "Fecha de reserva:", LocalDate.now().toString());
        agregarCampo(pnlCard, "Cédula del ciudadano (cliente):", ciudadano.getCedula());
        agregarCampo(pnlCard, "Código del objeto/producto:", producto.getCodigo() + " - " + producto.getNombre());
        
        // Campo Estado 
        JLabel lblEstado = new JLabel("Estado:");
        lblEstado.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField txtEstado = new JTextField();
        txtEstado.setEditable(false);
        txtEstado.setBackground(new Color(230, 230, 230));
        txtEstado.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        pnlCard.add(lblEstado);
        pnlCard.add(txtEstado);
        pnlCard.add(Box.createVerticalStrut(30));

        // Botones
        JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlBtns.setBackground(Color.WHITE);
        
        JButton btnGuardar = new JButton("Guardar reserva");
        btnGuardar.setBackground(new Color(40, 167, 69)); // Verde
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setPreferredSize(new Dimension(150, 40));
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setBackground(new Color(220, 53, 69)); // Rojo
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setPreferredSize(new Dimension(100, 40));

        pnlBtns.add(btnGuardar);
        pnlBtns.add(btnCancelar);
        pnlCard.add(pnlBtns);

        add(pnlCard, BorderLayout.CENTER);
        
        // Borde superior 
        JPanel pnlTopLine = new JPanel();
        pnlTopLine.setBackground(new Color(33, 150, 243));
        pnlTopLine.setPreferredSize(new Dimension(getWidth(), 8));
        add(pnlTopLine, BorderLayout.NORTH);

        // Lógica
        btnCancelar.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> {
            String resultado = controller.crearReserva(ciudadano.getCedula(), producto);
            txtEstado.setText("Solicitada"); 
            JOptionPane.showMessageDialog(this, resultado);
            
            if (resultado.contains("exitosamente")) {
                parent.cargarProductos(""); 
                dispose();
            }
        });
    }

    private void agregarCampo(JPanel p, String label, String valor) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setForeground(Color.GRAY);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JTextField t = new JTextField(valor);
        t.setEditable(false);
        t.setBackground(Color.WHITE);
        t.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        t.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        t.setFont(new Font("SansSerif", Font.PLAIN, 14));

        p.add(l);
        p.add(Box.createVerticalStrut(5));
        p.add(t);
        p.add(Box.createVerticalStrut(15));
    }
}
