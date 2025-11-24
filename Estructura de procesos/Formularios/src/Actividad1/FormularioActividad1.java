package Actividad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormularioActividad1 extends JFrame {
    private JTextField txtCodigo;
    private JCheckBox chkRegistrado;
    private JCheckBox chkNoEncontrado;
    private JTextField txtInfoLote;
    private JRadioButton rbtnCoincideSi;
    private JRadioButton rbtnCoincideNo;
    private JTextField txtJefe;
    private JTextField txtFechaHora;
    private JTextField txtCentro;
    private JRadioButton rbtnAceptar;
    private JRadioButton rbtnRechazar;
    private JTextArea txtObservaciones;
    private JButton btnFinalizar;

    // --- 1. Se añade la variable para el callback ---
    private Runnable onCompleteCallback;

    // --- 2. Constructor con Callback
    public FormularioActividad1(String usuarioLogueado, Runnable onCompleteCallback) {
        // Se guarda el callback
        this.onCompleteCallback = onCompleteCallback;
        
        setTitle("Formulario de Verificación de Paquete en Acopio - F-U6-03");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 640);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel main = new JPanel(new BorderLayout(8,8));
        getContentPane().add(main);

        // Título
        JLabel lblTitulo = new JLabel("FORMULARIO DE VERIFICACIÓN DE PAQUETE EN ACOPIO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        main.add(lblTitulo, BorderLayout.NORTH);

        // Centro: panel con GridBag para secciones
        JPanel center = new JPanel(new GridBagLayout());
        main.add(center, BorderLayout.CENTER);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,6,6,6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // A. Identificación
        JPanel panelA = new JPanel(new GridBagLayout());
        panelA.setBorder(BorderFactory.createTitledBorder("A. IDENTIFICACIÓN DEL PAQUETE"));
        gbc.gridy = 0;
        center.add(panelA, gbc);

        GridBagConstraints a = new GridBagConstraints();
        a.insets = new Insets(4,4,4,4);
        a.gridx = 0; a.gridy = 0;
        panelA.add(new JLabel("Código de Ecoetiqueta (escaneado): "), a);
        a.gridx = 1; a.weightx = 1.0; a.fill = GridBagConstraints.HORIZONTAL;
        txtCodigo = new JTextField();
        panelA.add(txtCodigo, a);
        a.gridx = 2; a.weightx = 0; a.fill = GridBagConstraints.NONE;
        JButton btnLeer = new JButton("Leer");
        panelA.add(btnLeer, a);

        // B. Validación
        JPanel panelB = new JPanel(new GridBagLayout());
        panelB.setBorder(BorderFactory.createTitledBorder("B. VALIDACIÓN EN SISTEMA"));
        gbc.gridy = 1;
        center.add(panelB, gbc);

        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(4,4,4,4);
        b.gridx = 0; b.gridy = 0;
        chkRegistrado = new JCheckBox("Registrado");
        chkRegistrado.setEnabled(false);
        panelB.add(chkRegistrado, b);
        b.gridx = 1;
        chkNoEncontrado = new JCheckBox("No Encontrado");
        chkNoEncontrado.setEnabled(false);
        panelB.add(chkNoEncontrado, b);

        b.gridx = 0; b.gridy = 1; b.gridwidth = 3; b.fill = GridBagConstraints.HORIZONTAL;
        txtInfoLote = new JTextField();
        txtInfoLote.setEditable(false);
        txtInfoLote.setPreferredSize(new Dimension(400, 24));
        panelB.add(new JLabel("Información del Lote Asociado:"), new GridBagConstraints(){{
            insets = new Insets(4,4,4,4); gridx=0; gridy=1; gridwidth=3; anchor=GridBagConstraints.WEST;
        }});
        b.gridy = 2;
        panelB.add(txtInfoLote, b);

        b.gridy = 3; b.gridwidth = 1; b.fill = GridBagConstraints.NONE;
        panelB.add(new JLabel("¿La información coincide con el paquete físico?"), b);
        b.gridx = 1;
        rbtnCoincideSi = new JRadioButton("Sí");
        panelB.add(rbtnCoincideSi, b);
        b.gridx = 2;
        rbtnCoincideNo = new JRadioButton("No");
        panelB.add(rbtnCoincideNo, b);
        ButtonGroup grupoCoincide = new ButtonGroup();
        grupoCoincide.add(rbtnCoincideSi);
        grupoCoincide.add(rbtnCoincideNo);

        // C. Datos de recepción
        JPanel panelC = new JPanel(new GridBagLayout());
        panelC.setBorder(BorderFactory.createTitledBorder("C. DATOS DE LA RECEPCIÓN"));
        gbc.gridy = 2;
        center.add(panelC, gbc);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.gridx = 0; c.gridy = 0;
        panelC.add(new JLabel("Jefe de Acopio Responsable:"), c);
        c.gridx = 1; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL;
        txtJefe = new JTextField(usuarioLogueado);
        txtJefe.setEditable(false);
        panelC.add(txtJefe, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0; c.fill = GridBagConstraints.NONE;
        panelC.add(new JLabel("Fecha/Hora de Verificación:"), c);
        c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL;
        txtFechaHora = new JTextField();
        txtFechaHora.setEditable(false);
        panelC.add(txtFechaHora, c);

        c.gridx = 0; c.gridy = 2; c.fill = GridBagConstraints.NONE;
        panelC.add(new JLabel("Centro de Acopio:"), c);
        c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL;
        txtCentro = new JTextField();
        panelC.add(txtCentro, c);

        // D. Resultado
        JPanel panelD = new JPanel(new GridBagLayout());
        panelD.setBorder(BorderFactory.createTitledBorder("D. RESULTADO DE LA ACTIVIDAD"));
        main.add(panelD, BorderLayout.SOUTH);

        GridBagConstraints d = new GridBagConstraints();
        d.insets = new Insets(6,6,6,6);
        d.gridx = 0; d.gridy = 0;
        panelD.add(new JLabel("Decisión:"), d);
        d.gridx = 1;
        rbtnAceptar = new JRadioButton("ACEPTAR PAQUETE");
        panelD.add(rbtnAceptar, d);
        d.gridx = 2;
        rbtnRechazar = new JRadioButton("RECHAZAR PAQUETE");
        panelD.add(rbtnRechazar, d);
        ButtonGroup grupoDecision = new ButtonGroup();
        grupoDecision.add(rbtnAceptar);
        grupoDecision.add(rbtnRechazar);

        d.gridx = 0; d.gridy = 1; d.gridwidth = 3; d.fill = GridBagConstraints.HORIZONTAL;
        txtObservaciones = new JTextArea(3, 60);
        panelD.add(new JScrollPane(txtObservaciones), d);

        btnFinalizar = new JButton("FINALIZAR VERIFICACIÓN");
        d.gridy = 2; d.gridwidth = 3; d.anchor = GridBagConstraints.CENTER;
        panelD.add(btnFinalizar, d);

        // Inicializar fecha/hora
        actualizarFechaHora();

        // Eventos
        // Cuando presionen Enter en txtCodigo (lectura por escáner)
        txtCodigo.addActionListener(e -> procesarCodigo());

        // Botón leer (simula lectura)
        btnLeer.addActionListener(e -> procesarCodigo());

        // Finalizar
        btnFinalizar.addActionListener(e -> accionFinalizar());

        // Poner foco en campo de codigo al abrir
        addWindowListener(new WindowAdapter() {
            public void windowOpened(WindowEvent e) {
                txtCodigo.requestFocusInWindow();
            }
        });
    }

    private void actualizarFechaHora() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        txtFechaHora.setText(LocalDateTime.now().format(fmt));
    }

    // Simulación: buscar en "sistema" un lote por código
    private void procesarCodigo() {
        String codigo = txtCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese o escanee un código.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Simular búsqueda: si el código empieza por "ECO" lo consideramos registrado
        if (codigo.toUpperCase().startsWith("ECO")) {
            chkRegistrado.setSelected(true);
            chkNoEncontrado.setSelected(false);
            txtInfoLote.setText("Lote: L-" + codigo.substring(Math.max(0, codigo.length()-4)) + " | Producto: Ejemplo");
            rbtnCoincideSi.setSelected(true);
        } else {
            chkRegistrado.setSelected(false);
            chkNoEncontrado.setSelected(true);
            txtInfoLote.setText("");
            rbtnCoincideNo.setSelected(true);
        }
        // Actualizar fecha/hora de verificación
        actualizarFechaHora();
    }

    private void accionFinalizar() {
        // Validaciones 
        if (!rbtnAceptar.isSelected() && !rbtnRechazar.isSelected()) {
            JOptionPane.showMessageDialog(this, "Seleccione una decisión: Aceptar o Rechazar.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (rbtnRechazar.isSelected() && txtObservaciones.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "En caso de rechazo, ingrese observaciones.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        
        String resumen = String.format("Código: %s\nRegistrado: %s\nDecisión: %s\nJefe: %s\nFecha: %s",
                txtCodigo.getText(),
                chkRegistrado.isSelected() ? "Sí" : "No",
                rbtnAceptar.isSelected() ? "ACEPTAR" : "RECHAZAR",
                txtJefe.getText(),
                txtFechaHora.getText());

        JOptionPane.showMessageDialog(this, "Verificación finalizada:\n\n" + resumen, "OK", JOptionPane.INFORMATION_MESSAGE);

        // Se llama al callback 
        if (rbtnAceptar.isSelected() && onCompleteCallback != null) {
            onCompleteCallback.run(); // Avisa al menú principal
        }
        dispose(); 
    }

    private void limpiarFormulario() {
        txtCodigo.setText("");
        chkRegistrado.setSelected(false);
        chkNoEncontrado.setSelected(false);
        txtInfoLote.setText("");
        rbtnCoincideSi.setSelected(false);
        rbtnCoincideNo.setSelected(false);
        txtCentro.setText("");
        rbtnAceptar.setSelected(false);
        rbtnRechazar.setSelected(false);
        txtObservaciones.setText("");
        txtCodigo.requestFocusInWindow();
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           
            FormularioActividad1 f = new FormularioActividad1(System.getProperty("user.name"), () -> {
                System.out.println("Prueba de Main: Actividad 1 completada.");
            });
            f.setVisible(true);
        });
    }
}