package Actividad5;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FormularioActividad5 extends JFrame {

    // Componentes de la interfaz
    private JTextField txtCodigoBulto;
    private JButton btnBuscarBulto;
    private JTextField txtPeso;
    private JTextField txtDimensiones;
    private JCheckBox chkContenidoCoincide;
    private JTextArea txtObservacionesInspector;
    private JRadioButton rbtnAprobada;
    private JRadioButton rbtnRechazada;
    private JButton btnGuardarVerificacion;
    private JCheckBox chkAceptacionCliente;
    private JButton btnFinalizar;
    
    // Paneles para habilitar/deshabilitar
    private JPanel panelB;
    private JPanel panelC;
    private JPanel panelD;
    
    // Se añade la variable para el callback ---
    private Runnable onCompleteCallback;

    
    public FormularioActividad5(Runnable onCompleteCallback) {
        // Se guarda el callback
        this.onCompleteCallback = onCompleteCallback;
        
        // --- Configuración básica de la ventana ---
        setTitle("Formulario de Verificación de Carga - F-U5-01");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 650);
        setLocationRelativeTo(null);

        // --- Panel Principal ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(mainPanel);

        // --- Título ---
        JLabel lblTitulo = new JLabel("FORMULARIO DE VERIFICACIÓN DE CARGA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // --- Panel Central con Secciones ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Sección A: Identificación de la Carga ---
        JPanel panelA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelA.setBorder(BorderFactory.createTitledBorder("A. IDENTIFICACIÓN DE LA CARGA"));
        panelA.add(new JLabel("Código de Bulto a Verificar:"));
        txtCodigoBulto = new JTextField(20);
        panelA.add(txtCodigoBulto);
        btnBuscarBulto = new JButton("Buscar Bulto");
        panelA.add(btnBuscarBulto);
        centerPanel.add(panelA);

        // --- Sección B: Verificación Física ---
        panelB = new JPanel(new GridBagLayout());
        panelB.setBorder(BorderFactory.createTitledBorder("B. VERIFICACIÓN FÍSICA"));
        GridBagConstraints gbcB = new GridBagConstraints();
        gbcB.insets = new Insets(5, 5, 5, 5);
        gbcB.anchor = GridBagConstraints.WEST;

        gbcB.gridx = 0; gbcB.gridy = 0; panelB.add(new JLabel("Peso Registrado (kg):"), gbcB);
        gbcB.gridx = 1; gbcB.fill = GridBagConstraints.HORIZONTAL;
        txtPeso = new JTextField(10);
        panelB.add(txtPeso, gbcB);

        gbcB.gridx = 0; gbcB.gridy = 1; gbcB.fill = GridBagConstraints.NONE;
        panelB.add(new JLabel("Dimensiones Verificadas (cm):"), gbcB);
        gbcB.gridx = 1; gbcB.fill = GridBagConstraints.HORIZONTAL;
        txtDimensiones = new JTextField(15);
        panelB.add(txtDimensiones, gbcB);

        gbcB.gridx = 0; gbcB.gridy = 2; gbcB.gridwidth = 2;
        chkContenidoCoincide = new JCheckBox("El contenido y la cantidad coinciden con la documentación.");
        panelB.add(chkContenidoCoincide, gbcB);
        centerPanel.add(panelB);

        // --- Sección C: Registro y Decisión del Inspector ---
        panelC = new JPanel(new BorderLayout(5, 5));
        panelC.setBorder(BorderFactory.createTitledBorder("C. REGISTRO Y DECISIÓN DEL INSPECTOR"));
        panelC.add(new JLabel("Observaciones de la Inspección:"), BorderLayout.NORTH);
        txtObservacionesInspector = new JTextArea(3, 30);
        panelC.add(new JScrollPane(txtObservacionesInspector), BorderLayout.CENTER);

        JPanel decisionPanel = new JPanel();
        decisionPanel.add(new JLabel("Decisión:"));
        rbtnAprobada = new JRadioButton("Carga APROBADA");
        rbtnRechazada = new JRadioButton("Carga RECHAZADA");
        ButtonGroup group = new ButtonGroup();
        group.add(rbtnAprobada);
        group.add(rbtnRechazada);
        decisionPanel.add(rbtnAprobada);
        decisionPanel.add(rbtnRechazada);
        btnGuardarVerificacion = new JButton("Guardar y Presentar a Cliente");
        decisionPanel.add(btnGuardarVerificacion);
        panelC.add(decisionPanel, BorderLayout.SOUTH);
        centerPanel.add(panelC);

        // --- Sección D y Botón Finalizar ---
        JPanel southPanel = new JPanel(new BorderLayout());
        panelD = new JPanel();
        panelD.setBorder(BorderFactory.createTitledBorder("D. ACEPTACIÓN DEL CLIENTE"));
        chkAceptacionCliente = new JCheckBox("Acepto y firmo los resultados de la verificación de carga.");
        panelD.add(chkAceptacionCliente);
        southPanel.add(panelD, BorderLayout.CENTER);

        btnFinalizar = new JButton("FINALIZAR VERIFICACIÓN");
        JPanel finalButtonPanel = new JPanel();
        finalButtonPanel.add(btnFinalizar);
        southPanel.add(finalButtonPanel, BorderLayout.SOUTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Estado inicial de los componentes
        setPanelEnabled(panelB, false);
        setPanelEnabled(panelC, false);
        setPanelEnabled(panelD, false);

        // --- Lógica de Eventos ---
        btnBuscarBulto.addActionListener(e -> buscarBulto());
        txtCodigoBulto.addActionListener(e -> buscarBulto());
        btnGuardarVerificacion.addActionListener(e -> guardarVerificacion());
        btnFinalizar.addActionListener(e -> finalizarProceso());
    }

    private void buscarBulto() {
        String codigo = txtCodigoBulto.getText().trim();
        // Simulación: si el código empieza con "BTO"
        if (codigo.toUpperCase().startsWith("BTO-")) {
            setPanelEnabled(panelB, true);
            setPanelEnabled(panelC, true);
            JOptionPane.showMessageDialog(this, "Bulto encontrado. Proceda con la verificación.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Código de bulto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarVerificacion() {
        if (!rbtnAprobada.isSelected() && !rbtnRechazada.isSelected()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una decisión (Aprobada o Rechazada).", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Simular guardado y notificación
        JOptionPane.showMessageDialog(this, 
            "Verificación guardada. Por favor, solicite la aceptación del cliente.", 
            "Verificación Guardada", JOptionPane.INFORMATION_MESSAGE);
            
        // Habilitar la sección del cliente y deshabilitar la del inspector
        setPanelEnabled(panelB, false);
        setPanelEnabled(panelC, false);
        setPanelEnabled(panelD, true);
    }

    private void finalizarProceso() {
        if (!chkAceptacionCliente.isSelected()) {
            JOptionPane.showMessageDialog(this, "El cliente debe aceptar los resultados de la verificación.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Proceso de verificación de carga finalizado con éxito.", "Proceso Completado", JOptionPane.INFORMATION_MESSAGE);
        
        // --- 3. Se llama al callback (si es exitoso) y se cierra ---
        // Asumimos que si se finaliza, fue APROBADO 
        if (rbtnAprobada.isSelected() && onCompleteCallback != null) {
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
        SwingUtilities.invokeLater(() -> new FormularioActividad5(() -> {
            System.out.println("Prueba de Main: Actividad 5 completada.");
        }).setVisible(true));
    }
}