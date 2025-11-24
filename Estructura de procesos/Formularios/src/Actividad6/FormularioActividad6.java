package Actividad6;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormularioActividad6 extends JFrame {

    // Componentes de la interfaz
    private JTextField txtCodigoBulto;
    private JButton btnCargarDatos;
    private JLabel lblCliente;
    private JLabel lblDestino;
    private JTextField txtRutaDespacho;
    private JLabel lblFechaSalida;
    private JButton btnGenerarOrden;
    private JCheckBox chkAceptacionCliente;
    private JButton btnFinalizar;

    // Paneles para habilitar/deshabilitar
    private JPanel panelB;
    private JPanel panelC;

    // Se añade la variable para el callback ---
    private Runnable onCompleteCallback;

    
    public FormularioActividad6(Runnable onCompleteCallback) {
        // Se guarda el callback
        this.onCompleteCallback = onCompleteCallback;
        
        // --- Configuración básica de la ventana ---
        setTitle("Formulario de Orden de Despacho - F-U9-02");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 500);
        setLocationRelativeTo(null);

        // --- Panel Principal ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(mainPanel);

        // --- Título ---
        JLabel lblTitulo = new JLabel("FORMULARIO DE ORDEN DE DESPACHO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // --- Panel Central con Secciones ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Sección A: Identificación ---
        JPanel panelA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelA.setBorder(BorderFactory.createTitledBorder("A. IDENTIFICACIÓN DE LA CARGA VERIFICADA"));
        panelA.add(new JLabel("Código de Bulto Verificado:"));
        txtCodigoBulto = new JTextField(20);
        panelA.add(txtCodigoBulto);
        btnCargarDatos = new JButton("Cargar Datos de Carga");
        panelA.add(btnCargarDatos);
        centerPanel.add(panelA);

        // --- Sección B: Revisión de Ruta y Autorización ---
        panelB = new JPanel(new GridBagLayout());
        panelB.setBorder(BorderFactory.createTitledBorder("B. REVISIÓN DE RUTA Y AUTORIZACIÓN"));
        GridBagConstraints gbcB = new GridBagConstraints();
        gbcB.insets = new Insets(5, 5, 5, 5);
        gbcB.anchor = GridBagConstraints.WEST;

        gbcB.gridx = 0; gbcB.gridy = 0; panelB.add(new JLabel("Cliente:"), gbcB);
        gbcB.gridx = 1; lblCliente = new JLabel();
        panelB.add(lblCliente, gbcB);

        gbcB.gridx = 0; gbcB.gridy = 1; panelB.add(new JLabel("Destino:"), gbcB);
        gbcB.gridx = 1; lblDestino = new JLabel();
        panelB.add(lblDestino, gbcB);

        gbcB.gridx = 0; gbcB.gridy = 2; panelB.add(new JLabel("Ruta de Despacho Asignada:"), gbcB);
        gbcB.gridx = 1; gbcB.fill = GridBagConstraints.HORIZONTAL;
        txtRutaDespacho = new JTextField(20);
        panelB.add(txtRutaDespacho, gbcB);
        
        gbcB.gridx = 0; gbcB.gridy = 3; gbcB.fill = GridBagConstraints.NONE;
        panelB.add(new JLabel("Fecha/Hora de Salida Programada:"), gbcB);
        gbcB.gridx = 1; lblFechaSalida = new JLabel();
        panelB.add(lblFechaSalida, gbcB);
        
        gbcB.gridx = 0; gbcB.gridy = 4; gbcB.gridwidth = 2; gbcB.anchor = GridBagConstraints.CENTER;
        btnGenerarOrden = new JButton("Generar Orden y Presentar a Cliente");
        panelB.add(btnGenerarOrden, gbcB);
        
        centerPanel.add(panelB);

        // --- Sección C y Botón Finalizar ---
        JPanel southPanel = new JPanel(new BorderLayout());
        panelC = new JPanel();
        panelC.setBorder(BorderFactory.createTitledBorder("C. ACEPTACIÓN FINAL DEL CLIENTE"));
        chkAceptacionCliente = new JCheckBox("El cliente revisa la Orden de Despacho, está de acuerdo y firma para autorizar la salida.");
        panelC.add(chkAceptacionCliente);
        southPanel.add(panelC, BorderLayout.CENTER);

        btnFinalizar = new JButton("FINALIZAR Y CONFIRMAR DESPACHO");
        JPanel finalButtonPanel = new JPanel();
        finalButtonPanel.add(btnFinalizar);
        southPanel.add(finalButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        // Estado inicial de los componentes
        setPanelEnabled(panelB, false);
        setPanelEnabled(panelC, false);

        // --- Lógica de Eventos ---
        btnCargarDatos.addActionListener(e -> cargarDatos());
        txtCodigoBulto.addActionListener(e -> cargarDatos());
        btnGenerarOrden.addActionListener(e -> generarOrden());
        btnFinalizar.addActionListener(e -> finalizarDespacho());
    }

    private void cargarDatos() {
        String codigo = txtCodigoBulto.getText().trim();
        // Simulación: si el código empieza con "BTO", lo encontramos
        if (codigo.toUpperCase().startsWith("BTO-")) {
            lblCliente.setText("Jhon Stivenson Méndez");
            lblDestino.setText("Armenia - Quindío");
            txtRutaDespacho.setText("RUTA-QND-04-CENTRO");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
            lblFechaSalida.setText(LocalDateTime.now().plusHours(2).format(dtf));
            
            setPanelEnabled(panelB, true);
            JOptionPane.showMessageDialog(this, "Datos de carga verificada cargados.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Código de bulto no encontrado o no verificado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generarOrden() {
        if (txtRutaDespacho.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe asignar una ruta de despacho.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Simular generación y notificación
        JOptionPane.showMessageDialog(this, 
            "Orden de Despacho generada. Por favor, presente al cliente para su aceptación y firma final.", 
            "Orden Generada", JOptionPane.INFORMATION_MESSAGE);
            
        // Habilitar la sección del cliente y deshabilitar la del coordinador
        btnGenerarOrden.setEnabled(false);
        txtRutaDespacho.setEditable(false);
        setPanelEnabled(panelC, true);
    }
    
    private void finalizarDespacho() {
        if (!chkAceptacionCliente.isSelected()) {
            JOptionPane.showMessageDialog(this, "El cliente debe aceptar la Orden de Despacho.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Orden de Despacho confirmada. El paquete está listo para salir.", "Proceso Completado", JOptionPane.INFORMATION_MESSAGE);
        
        // Se llama al callback y se cierra ---
        if (onCompleteCallback != null) {
            onCompleteCallback.run(); // Avisa al menú principal
        }
        dispose(); // Cierra esta ventana
    }

    private void setPanelEnabled(JPanel panel, boolean isEnabled) {
        panel.setEnabled(isEnabled);
        for (Component comp : panel.getComponents()) {
            
            if (comp instanceof JPanel) {
                setPanelEnabled((JPanel) comp, isEnabled);
            } else {
                comp.setEnabled(isEnabled);
            }
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormularioActividad6(() -> {
            System.out.println("Prueba de Main: Actividad 6 completada.");
        }).setVisible(true));
    }
}