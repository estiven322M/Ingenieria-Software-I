package Actividad2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class FormularioActividad2 extends JFrame {

    // Componentes de la interfaz
    private JTextField txtCodigoComprobante;
    private JTextField txtNombreCliente;
    private JTextField txtCedulaCliente;
    private JTextField txtIdPaquete;
    private JTextField txtDescPaquete;
    private JCheckBox chkValido;
    private JCheckBox chkInvalido;
    private JCheckBox chkConfirmacionAgente;
    private JTextArea txtObservaciones;
    private JButton btnBuscar;
    private JButton btnFinalizar;
    
    //  Se añade la variable para el callback ---
    private Runnable onCompleteCallback;

    
    public FormularioActividad2(Runnable onCompleteCallback) {
        // Se guarda el callback
        this.onCompleteCallback = onCompleteCallback;
        
        // --- Configuración básica de la ventana ---
        setTitle("Formulario de Admisión en Ventanilla - F-U4-01");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 600);
        setLocationRelativeTo(null);

        // --- Panel Principal ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(mainPanel);

        // --- Título ---
        JLabel lblTitulo = new JLabel("FORMULARIO DE ADMISIÓN EN VENTANILLA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // --- Panel Central con Secciones ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Sección A: Recepción del Comprobante ---
        JPanel panelA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelA.setBorder(BorderFactory.createTitledBorder("A. RECEPCIÓN DEL COMPROBANTE"));
        panelA.add(new JLabel("Código del Comprobante (escaneado):"));
        txtCodigoComprobante = new JTextField(20);
        panelA.add(txtCodigoComprobante);
        btnBuscar = new JButton("Escanear/Buscar");
        panelA.add(btnBuscar);
        centerPanel.add(panelA);

        // --- Sección B: Verificación en Sistema ---
        JPanel panelB = new JPanel(new GridBagLayout());
        panelB.setBorder(BorderFactory.createTitledBorder("B. VERIFICACIÓN EN SISTEMA"));
        GridBagConstraints gbcB = new GridBagConstraints();
        gbcB.insets = new Insets(5, 5, 5, 5);
        gbcB.anchor = GridBagConstraints.WEST;

        // Datos del Cliente
        gbcB.gridx = 0; gbcB.gridy = 0;
        panelB.add(new JLabel("Nombre Cliente:"), gbcB);
        gbcB.gridx = 1; gbcB.gridwidth = 3; gbcB.fill = GridBagConstraints.HORIZONTAL;
        txtNombreCliente = new JTextField();
        txtNombreCliente.setEditable(false);
        panelB.add(txtNombreCliente, gbcB);

        gbcB.gridx = 0; gbcB.gridy = 1; gbcB.gridwidth = 1;
        panelB.add(new JLabel("Cédula Cliente:"), gbcB);
        gbcB.gridx = 1;
        txtCedulaCliente = new JTextField();
        txtCedulaCliente.setEditable(false);
        panelB.add(txtCedulaCliente, gbcB);

        // Datos del Paquete
        gbcB.gridx = 0; gbcB.gridy = 2;
        panelB.add(new JLabel("ID Paquete:"), gbcB);
        gbcB.gridx = 1;
        txtIdPaquete = new JTextField();
        txtIdPaquete.setEditable(false);
        panelB.add(txtIdPaquete, gbcB);

        gbcB.gridx = 0; gbcB.gridy = 3;
        panelB.add(new JLabel("Descripción Paquete:"), gbcB);
        gbcB.gridx = 1;
        txtDescPaquete = new JTextField();
        txtDescPaquete.setEditable(false);
        panelB.add(txtDescPaquete, gbcB);
        
        // Estado del Registro
        gbcB.gridx = 0; gbcB.gridy = 4;
        panelB.add(new JLabel("Estado del Registro:"), gbcB);
        chkValido = new JCheckBox("Válido");
        chkInvalido = new JCheckBox("Inválido / No encontrado");
        chkValido.setEnabled(false);
        chkInvalido.setEnabled(false);
        JPanel panelEstado = new JPanel();
        panelEstado.add(chkValido);
        panelEstado.add(chkInvalido);
        gbcB.gridx = 1;
        panelB.add(panelEstado, gbcB);
        
        centerPanel.add(panelB);

        // --- Sección C: Decisión de la Actividad ---
        JPanel panelC = new JPanel(new BorderLayout(5, 5));
        panelC.setBorder(BorderFactory.createTitledBorder("C. DECISIÓN DE LA ACTIVIDAD"));

        chkConfirmacionAgente = new JCheckBox("Los datos del sistema coinciden con el documento físico.");
        panelC.add(chkConfirmacionAgente, BorderLayout.NORTH);

        panelC.add(new JLabel("Observaciones (si aplica):"), BorderLayout.CENTER);
        txtObservaciones = new JTextArea(4, 30);
        panelC.add(new JScrollPane(txtObservaciones), BorderLayout.SOUTH);

        centerPanel.add(panelC);

        // --- Botón Finalizar ---
        btnFinalizar = new JButton("FINALIZAR ADMISIÓN");
        JPanel southPanel = new JPanel();
        southPanel.add(btnFinalizar);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        // --- Lógica de Eventos ---

        // Evento para el botón Buscar/Escanear
        btnBuscar.addActionListener(e -> buscarComprobante());
        
        // También al presionar Enter en el campo de texto
        txtCodigoComprobante.addActionListener(e -> buscarComprobante());

        // Evento para el botón Finalizar
        btnFinalizar.addActionListener(e -> finalizarAdmision());
        
        // Poner foco en el campo de código al abrir la ventana
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                txtCodigoComprobante.requestFocusInWindow();
            }
        });
    }

    private void buscarComprobante() {
        String codigo = txtCodigoComprobante.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese o escanee un código de comprobante.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Simulación de búsqueda en base de datos
        // Si el código empieza con "FA" (Ficha Admisión), se consideramos válido
        if (codigo.toUpperCase().startsWith("FA-")) {
            txtNombreCliente.setText("Jhon Stivenson Méndez");
            txtCedulaCliente.setText("1094xxxxxx");
            txtIdPaquete.setText("PKG-84391");
            txtDescPaquete.setText("Documentos y papelería");
            chkValido.setSelected(true);
            chkInvalido.setSelected(false);
            chkConfirmacionAgente.requestFocusInWindow();
        } else {
            // Si no se encuentra, limpiar los campos
            txtNombreCliente.setText("");
            txtCedulaCliente.setText("");
            txtIdPaquete.setText("");
            txtDescPaquete.setText("");
            chkValido.setSelected(false);
            chkInvalido.setSelected(true);
            JOptionPane.showMessageDialog(this, "Comprobante no encontrado en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void finalizarAdmision() {
        // Validar que se haya buscado un comprobante válido
        if (!chkValido.isSelected()) {
            JOptionPane.showMessageDialog(this, "Debe buscar y validar un comprobante primero.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Validar que el agente confirme la correspondencia
        if (!chkConfirmacionAgente.isSelected()) {
            JOptionPane.showMessageDialog(this, "Debe confirmar que los datos coinciden con el documento físico.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lógica para guardar la confirmación (simulada)
        String resumen = String.format("Comprobante: %s\nCliente: %s\nConfirmado por Agente: Sí",
                txtCodigoComprobante.getText(),
                txtNombreCliente.getText());

        JOptionPane.showMessageDialog(this, "Admisión finalizada con éxito.\n\n" + resumen, "Proceso Completado", JOptionPane.INFORMATION_MESSAGE);
        
        // Se llama al callback y se cierra ---
        if (onCompleteCallback != null) {
            onCompleteCallback.run(); // Avisa al menú principal
        }
        dispose(); // Cierra esta ventana
    }


    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormularioActividad2(() -> {
                System.out.println("Prueba de Main: Actividad 2 completada.");
            }).setVisible(true);
        });
    }
}