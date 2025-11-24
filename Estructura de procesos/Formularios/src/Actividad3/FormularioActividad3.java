package Actividad3;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FormularioActividad3 extends JFrame {

    // Componentes de la interfaz
    private JLabel lblCodigoConsolidacion;
    private JLabel lblNombreCliente;
    private JLabel lblCedulaCliente;
    private JLabel lblCantidadPaquetes;
    private JLabel lblDescGeneral;
    private JButton btnImprimirActa;
    private JCheckBox chkFirmaCliente;
    private JCheckBox chkFirmaJefe;
    private JTextArea txtObservaciones;
    private JButton btnFinalizar;

    // Se añade la variable para el callback ---
    private Runnable onCompleteCallback;

    
    public FormularioActividad3(Runnable onCompleteCallback) {
        // Se guarda el callback
        this.onCompleteCallback = onCompleteCallback;
        
        // --- Configuración básica de la ventana ---
        setTitle("Formulario de Firma de Acta de Consolidación - F-U6-01");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 550);
        setLocationRelativeTo(null);

        // --- Panel Principal ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(mainPanel);

        // --- Título ---
        JLabel lblTitulo = new JLabel("FORMULARIO DE FIRMA DE ACTA DE CONSOLIDACIÓN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // --- Panel Central con Secciones ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Sección A: Resumen de la Consolidación ---
        JPanel panelA = new JPanel(new GridBagLayout());
        panelA.setBorder(BorderFactory.createTitledBorder("A. RESUMEN DE LA CONSOLIDACIÓN PARA VERIFICACIÓN"));
        GridBagConstraints gbcA = new GridBagConstraints();
        gbcA.insets = new Insets(5, 5, 5, 5);
        gbcA.anchor = GridBagConstraints.WEST;

        gbcA.gridx = 0; gbcA.gridy = 0; panelA.add(new JLabel("Código de Consolidación:"), gbcA);
        gbcA.gridx = 1; lblCodigoConsolidacion = new JLabel("CON-458-2025");
        panelA.add(lblCodigoConsolidacion, gbcA);

        gbcA.gridx = 0; gbcA.gridy = 1; panelA.add(new JLabel("Nombre Cliente:"), gbcA);
        gbcA.gridx = 1; lblNombreCliente = new JLabel("Jhon Stivenson Méndez");
        panelA.add(lblNombreCliente, gbcA);

        gbcA.gridx = 0; gbcA.gridy = 2; panelA.add(new JLabel("Cédula Cliente:"), gbcA);
        gbcA.gridx = 1; lblCedulaCliente = new JLabel("1094xxxxxx");
        panelA.add(lblCedulaCliente, gbcA);

        gbcA.gridx = 0; gbcA.gridy = 3; panelA.add(new JLabel("Cantidad de Paquetes:"), gbcA);
        gbcA.gridx = 1; lblCantidadPaquetes = new JLabel("3");
        panelA.add(lblCantidadPaquetes, gbcA);

        gbcA.gridx = 0; gbcA.gridy = 4; panelA.add(new JLabel("Descripción General:"), gbcA);
        gbcA.gridx = 1; lblDescGeneral = new JLabel("Documentos, papelería y varios");
        panelA.add(lblDescGeneral, gbcA);

        centerPanel.add(panelA);

        // --- Sección B: Impresión del Acta ---
        JPanel panelB = new JPanel();
        panelB.setBorder(BorderFactory.createTitledBorder("B. IMPRESIÓN DEL ACTA FÍSICA"));
        btnImprimirActa = new JButton("IMPRIMIR ACTA PARA FIRMA");
        panelB.add(btnImprimirActa);
        centerPanel.add(panelB);

        // --- Sección C: Registro de Firmas ---
        JPanel panelC = new JPanel();
        panelC.setBorder(BorderFactory.createTitledBorder(
        	    BorderFactory.createEtchedBorder(), // Crea un borde base grabado
        	    "C. REGISTRO DE FIRMAS...", 
        	    TitledBorder.CENTER, 
        	    TitledBorder.TOP
        	));
        chkFirmaCliente = new JCheckBox("Firma del Cliente Recibida (en documento físico)");
        chkFirmaJefe = new JCheckBox("Contra-firma del Jefe de Acopio Realizada (en documento físico)");
        chkFirmaCliente.setEnabled(false);
        chkFirmaJefe.setEnabled(false);
        panelC.add(chkFirmaCliente);
        panelC.add(chkFirmaJefe);
        centerPanel.add(panelC);
        
        // --- Panel Sur para Observaciones y Finalizar ---
        JPanel southPanel = new JPanel(new BorderLayout(5,5));
        
        // Sección D: Observaciones
        JPanel panelD = new JPanel(new BorderLayout());
        panelD.setBorder(BorderFactory.createTitledBorder("D. OBSERVACIONES ADICIONALES"));
        txtObservaciones = new JTextArea(3, 30);
        panelD.add(new JScrollPane(txtObservaciones), BorderLayout.CENTER);
        southPanel.add(panelD, BorderLayout.CENTER);

        // Botón Finalizar
        btnFinalizar = new JButton("FINALIZAR Y ARCHIVAR ACTA");
        JPanel finalButtonPanel = new JPanel();
        finalButtonPanel.add(btnFinalizar);
        southPanel.add(finalButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        //  Eventos ---

        // Evento para el botón Imprimir
        btnImprimirActa.addActionListener(e -> {
            // Simula la impresión
            JOptionPane.showMessageDialog(this, 
                "El acta se ha impreso. Por favor, proceda con la recolección de firmas.", 
                "Impresión", JOptionPane.INFORMATION_MESSAGE);
            
            // Habilita la sección de firmas
            btnImprimirActa.setEnabled(false);
            chkFirmaCliente.setEnabled(true);
            chkFirmaJefe.setEnabled(true);
        });

        // Evento para el botón Finalizar
        btnFinalizar.addActionListener(e -> finalizarActa());
    }

    private void finalizarActa() {
        // Validar que el proceso de impresión se haya iniciado
        if (btnImprimirActa.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Debe imprimir el acta primero.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Validar que ambas firmas hayan sido confirmadas
        if (!chkFirmaCliente.isSelected() || !chkFirmaJefe.isSelected()) {
            JOptionPane.showMessageDialog(this, "Debe confirmar la recepción de ambas firmas.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lógica para guardar el estado (simulada)
        String resumen = String.format("Acta de Consolidación: %s\nEstado: FIRMADA",
                lblCodigoConsolidacion.getText());

        JOptionPane.showMessageDialog(this, "Proceso finalizado con éxito.\n\n" + resumen, "Acta Archivada", JOptionPane.INFORMATION_MESSAGE);
        
        
        if (onCompleteCallback != null) {
            onCompleteCallback.run(); 
        }
        dispose(); 
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormularioActividad3(() -> {
                System.out.println("Prueba de Main: Actividad 3 completada.");
            }).setVisible(true);
        });
    }
}