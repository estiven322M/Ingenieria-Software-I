package Actividad8;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FormularioActividad8 extends JFrame {

    // Componentes de la interfaz
    private JTextField txtOrdenDespacho;
    private JButton btnCargarDatos;
    private JLabel lblDestinatario;
    private JLabel lblMedioContacto;
    private JTextArea txtCuerpoMensaje;
    private JButton btnEnviar;
    private JLabel lblEstadoEnvio;

    // Paneles para habilitar/deshabilitar
    private JPanel panelB;
    private JPanel panelC;

    // Se añade la variable para el callback ---
    private Runnable onCompleteCallback;

    //  Se modifica el constructor para aceptar el callback ---
    public FormularioActividad8(Runnable onCompleteCallback) {
        // Se guarda el callback
        this.onCompleteCallback = onCompleteCallback;
        
        // --- Configuración básica de la ventana ---
        setTitle("Formulario de Notificación de Salida - F-U9-03");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 550);
        setLocationRelativeTo(null);

        // --- Panel Principal ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(mainPanel);

        // --- Título ---
        JLabel lblTitulo = new JLabel("FORMULARIO DE NOTIFICACIÓN DE SALIDA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // --- Panel Central con Secciones ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Sección A: Selección del Despacho ---
        JPanel panelA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelA.setBorder(BorderFactory.createTitledBorder("A. SELECCIÓN DEL DESPACHO A NOTIFICAR"));
        panelA.add(new JLabel("ID de Orden de Despacho:"));
        txtOrdenDespacho = new JTextField(20);
        panelA.add(txtOrdenDespacho);
        btnCargarDatos = new JButton("Cargar Datos para Notificación");
        panelA.add(btnCargarDatos);
        centerPanel.add(panelA);

        // --- Sección B: Pre-visualización de la Notificación ---
        panelB = new JPanel(new GridBagLayout());
        panelB.setBorder(BorderFactory.createTitledBorder("B. PRE-VISUALIZACIÓN DE LA NOTIFICACIÓN"));
        GridBagConstraints gbcB = new GridBagConstraints();
        gbcB.insets = new Insets(5, 5, 5, 5);
        gbcB.anchor = GridBagConstraints.WEST;

        gbcB.gridx = 0; gbcB.gridy = 0; panelB.add(new JLabel("Destinatario (Cliente):"), gbcB);
        gbcB.gridx = 1; lblDestinatario = new JLabel();
        panelB.add(lblDestinatario, gbcB);

        gbcB.gridx = 0; gbcB.gridy = 1; panelB.add(new JLabel("Medio de Contacto:"), gbcB);
        gbcB.gridx = 1; lblMedioContacto = new JLabel();
        panelB.add(lblMedioContacto, gbcB);

        gbcB.gridx = 0; gbcB.gridy = 2; panelB.add(new JLabel("Asunto del Mensaje:"), gbcB);
        gbcB.gridx = 1; panelB.add(new JLabel("Notificación de Despacho: Su envío está en tránsito"), gbcB);
        
        gbcB.gridx = 0; gbcB.gridy = 3; gbcB.gridwidth = 2;
        panelB.add(new JLabel("Cuerpo del Mensaje:"), gbcB);
        
        gbcB.gridy = 4; gbcB.fill = GridBagConstraints.HORIZONTAL;
        txtCuerpoMensaje = new JTextArea(6, 50);
        txtCuerpoMensaje.setEditable(false);
        txtCuerpoMensaje.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtCuerpoMensaje.setLineWrap(true);
        txtCuerpoMensaje.setWrapStyleWord(true);
        panelB.add(new JScrollPane(txtCuerpoMensaje), gbcB);

        centerPanel.add(panelB);

        // --- Sección C: Envío y Confirmación ---
        panelC = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelC.setBorder(BorderFactory.createTitledBorder("C. ENVÍO Y CONFIRMACIÓN"));
        panelC.add(new JLabel("Estado del Envío:"));
        lblEstadoEnvio = new JLabel("Pendiente de envío...");
        lblEstadoEnvio.setForeground(Color.ORANGE.darker());
        panelC.add(lblEstadoEnvio);
        btnEnviar = new JButton("ENVIAR NOTIFICACIÓN");
        panelC.add(btnEnviar);
        mainPanel.add(panelC, BorderLayout.SOUTH);

        // Estado inicial
        setPanelEnabled(panelB, false);
        setPanelEnabled(panelC, false);

        // --- Lógica de Eventos ---
        btnCargarDatos.addActionListener(e -> cargarDatosParaNotificacion());
        txtOrdenDespacho.addActionListener(e -> cargarDatosParaNotificacion());
        btnEnviar.addActionListener(e -> enviarNotificacion());
    }

    private void cargarDatosParaNotificacion() {
        String codigo = txtOrdenDespacho.getText().trim();
        // Simulación: si el código es válido, preparamos la notificación
        if (!codigo.isEmpty()) {
            lblDestinatario.setText("Jhon Stivenson Méndez");
            lblMedioContacto.setText("Correo Electrónico: j******@*****.com");
            String cuerpo = String.format(
                "Estimado %s,\n\n" +
                "Le informamos que su envío con código de bulto %s " +
                "ha sido despachado y se encuentra EN TRÁNSITO.\n\n" +
                "Puede realizar el seguimiento con su código de orden.\n\n" +
                "Gracias por confiar en Verde en Movimiento.",
                lblDestinatario.getText(),
                "BTO-ARM-774-2025" // Simulado
            );
            txtCuerpoMensaje.setText(cuerpo);
            
            setPanelEnabled(panelB, true);
            setPanelEnabled(panelC, true);
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un ID de Orden de Despacho.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void enviarNotificacion() {
        // Simular el envío de la notificación
        lblEstadoEnvio.setText("Enviando...");
        
        // Usamos un Timer para simular un retraso de red
        Timer timer = new Timer(1500, e -> {
            lblEstadoEnvio.setText("Notificación Enviada Exitosamente");
            lblEstadoEnvio.setForeground(new Color(0, 128, 0)); // Verde oscuro
            btnEnviar.setEnabled(false);
            JOptionPane.showMessageDialog(this, "La notificación ha sido enviada al cliente.", "Proceso Finalizado", JOptionPane.INFORMATION_MESSAGE);
            
            // Se llama al callback y se cierra ---
            if (onCompleteCallback != null) {
                onCompleteCallback.run(); // Avisa al menú principal
            }
            dispose(); // Cierra esta ventana
        });
        timer.setRepeats(false); // Para que se ejecute solo una vez
        timer.start();
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
        SwingUtilities.invokeLater(() -> new FormularioActividad8(() -> {
            System.out.println("Prueba de Main: Actividad 8 completada.");
        }).setVisible(true));
    }
}