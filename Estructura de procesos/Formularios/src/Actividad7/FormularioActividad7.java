
package Actividad7;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormularioActividad7 extends JFrame {

    // Componentes de la interfaz
    private JTextField txtOrdenDespacho;
    private JButton btnBuscarOrden;
    private JLabel lblPlaca;
    private JLabel lblConductor;
    private JLabel lblEstadoVerificacion;
    private JButton btnRegistrarBiometrico;
    private JButton btnFinalizar;

    // Paneles para habilitar/deshabilitar
    private JPanel panelB;
    private JPanel panelC;

    // Se añade la variable para el callback ---
    private Runnable onCompleteCallback;

  
    public FormularioActividad7(Runnable onCompleteCallback) {
        // Se guarda el callback
        this.onCompleteCallback = onCompleteCallback;
        
        // --- Configuración  de la ventana ---
        setTitle("Formulario de Registro de Salida de Flota - F-U10-01");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 450);
        setLocationRelativeTo(null);

        // --- Panel Principal ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(mainPanel);

        // --- Título ---
        JLabel lblTitulo = new JLabel("FORMULARIO DE REGISTRO DE SALIDA DE FLOTA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // --- Panel Central con Secciones ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Sección A: Identificación ---
        JPanel panelA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelA.setBorder(BorderFactory.createTitledBorder("A. IDENTIFICACIÓN DEL DESPACHO"));
        panelA.add(new JLabel("ID de Orden de Despacho:"));
        txtOrdenDespacho = new JTextField(20);
        panelA.add(txtOrdenDespacho);
        btnBuscarOrden = new JButton("Buscar Orden");
        panelA.add(btnBuscarOrden);
        centerPanel.add(panelA);

        // --- Sección B: Datos del Vehículo y Conductor ---
        panelB = new JPanel(new GridLayout(2, 2, 5, 5));
        panelB.setBorder(BorderFactory.createTitledBorder("B. DATOS DEL VEHÍCULO Y CONDUCTOR"));
        panelB.add(new JLabel("  Placa del Vehículo:"));
        lblPlaca = new JLabel();
        panelB.add(lblPlaca);
        panelB.add(new JLabel("  Nombre del Conductor Asignado:"));
        lblConductor = new JLabel();
        panelB.add(lblConductor);
        centerPanel.add(panelB);

        // --- Sección C: Registro de Salida con Biométrico ---
        panelC = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelC.setBorder(BorderFactory.createTitledBorder("C. REGISTRO DE SALIDA CON BIOMÉTRICO"));
        panelC.add(new JLabel("Estado de Verificación del Conductor:"));
        lblEstadoVerificacion = new JLabel("Esperando marcación...");
        lblEstadoVerificacion.setFont(new Font("SansSerif", Font.ITALIC, 12));
        lblEstadoVerificacion.setForeground(Color.BLUE);
        panelC.add(lblEstadoVerificacion);
        btnRegistrarBiometrico = new JButton("Registrar Salida con Biométrico");
        panelC.add(btnRegistrarBiometrico);
        centerPanel.add(panelC);

        // --- Botón Finalizar ---
        btnFinalizar = new JButton("CONFIRMAR Y ARCHIVAR REGISTRO DE SALIDA");
        JPanel southPanel = new JPanel();
        southPanel.add(btnFinalizar);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Estado inicial de los componentes
        setPanelEnabled(panelB, false);
        setPanelEnabled(panelC, false);
        btnFinalizar.setEnabled(false);

        // --- Lógica de Eventos ---
        btnBuscarOrden.addActionListener(e -> buscarOrden());
        txtOrdenDespacho.addActionListener(e -> buscarOrden());
        btnRegistrarBiometrico.addActionListener(e -> registrarSalida());
        btnFinalizar.addActionListener(e -> finalizarRegistro());
    }

    private void buscarOrden() {
        String codigo = txtOrdenDespacho.getText().trim();
        // Simulación: si el código es válido, cargamos los datos
        if (!codigo.isEmpty()) {
            lblPlaca.setText("KFW-482");
            lblConductor.setText("Carlos Rodríguez");
            setPanelEnabled(panelB, true);
            setPanelEnabled(panelC, true);
            JOptionPane.showMessageDialog(this, "Orden de Despacho encontrada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un ID de Orden de Despacho.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarSalida() {
        // Simular la verificación biométrica
        int result = JOptionPane.showConfirmDialog(this,
            "Simulación de Lector Biométrico:\nPor favor, pida al conductor que ponga su huella.\n¿La huella es válida?",
            "Verificación Biométrica", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            lblEstadoVerificacion.setText("Marcación Verificada Correctamente");
            lblEstadoVerificacion.setForeground(new Color(0, 128, 0)); // Verde oscuro
            btnRegistrarBiometrico.setEnabled(false);
            btnFinalizar.setEnabled(true);
        } else {
            lblEstadoVerificacion.setText("Verificación Fallida");
            lblEstadoVerificacion.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this, "La huella no es válida. No se puede registrar la salida.", "Alerta de Seguridad", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void finalizarRegistro() {
        if (btnRegistrarBiometrico.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Debe registrar la salida con el biométrico primero.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String timestamp = LocalDateTime.now().format(dtf);

        JOptionPane.showMessageDialog(this, 
            "Registro de Salida Confirmado y Archivado.\n\nVehículo: " + lblPlaca.getText() + "\nHora de Salida: " + timestamp,
            "Proceso Completado", JOptionPane.INFORMATION_MESSAGE);
        
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
        SwingUtilities.invokeLater(() -> new FormularioActividad7(() -> {
            System.out.println("Prueba de Main: Actividad 7 completada.");
        }).setVisible(true));
    }
}