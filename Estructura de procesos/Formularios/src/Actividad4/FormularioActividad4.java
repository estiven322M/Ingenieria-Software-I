package Actividad4;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.UUID;

public class FormularioActividad4 extends JFrame {

    // Componentes de la interfaz
    private JTextField txtCodigoConsolidacion;
    private JButton btnBuscar;
    private JLabel lblDatosCliente;
    private JLabel lblCantidadPaquetes;
    private JLabel lblDestino;
    private JTextField txtCodigoBulto;
    private JButton btnGenerarImprimir;
    private JCheckBox chkEntregaCliente;
    private JButton btnFinalizar;

    // Se añade la variable para el callback ---
    private Runnable onCompleteCallback;

    
    public FormularioActividad4(Runnable onCompleteCallback) {
        // Se guarda el callback
        this.onCompleteCallback = onCompleteCallback;
        
        // --- Configuración básica de la ventana ---
        setTitle("Formulario de Generación de Tarjeta de Bulto - F-U8-01");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 500);
        setLocationRelativeTo(null);

        // --- Panel Principal ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(mainPanel);

        // --- Título ---
        JLabel lblTitulo = new JLabel("FORMULARIO DE GENERACIÓN DE TARJETA DE BULTO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        // --- Panel Central con Secciones ---
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Sección A: Búsqueda ---
        JPanel panelA = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelA.setBorder(BorderFactory.createTitledBorder("A. BÚSQUEDA DE LA CONSOLIDACIÓN"));
        panelA.add(new JLabel("Código de Consolidación:"));
        txtCodigoConsolidacion = new JTextField(20);
        panelA.add(txtCodigoConsolidacion);
        btnBuscar = new JButton("Buscar");
        panelA.add(btnBuscar);
        centerPanel.add(panelA);

        // --- Sección B: Información del Bulto ---
        JPanel panelB = new JPanel(new GridLayout(3, 2, 5, 5));
        panelB.setBorder(BorderFactory.createTitledBorder("B. INFORMACIÓN DEL BULTO A ETIQUETAR"));
        panelB.add(new JLabel("  Datos del Cliente:"));
        lblDatosCliente = new JLabel();
        panelB.add(lblDatosCliente);
        panelB.add(new JLabel("  Cantidad de Paquetes:"));
        lblCantidadPaquetes = new JLabel();
        panelB.add(lblCantidadPaquetes);
        panelB.add(new JLabel("  Destino Sugerido:"));
        lblDestino = new JLabel();
        panelB.add(lblDestino);
        centerPanel.add(panelB);

        // --- Sección C: Generación de Tarjeta ---
        JPanel panelC = new JPanel(new GridBagLayout());
        panelC.setBorder(BorderFactory.createTitledBorder(
            "C. GENERACIÓN DE TARJETA (Regla: Identificación unívoca de bulto en tránsito)"));
        GridBagConstraints gbcC = new GridBagConstraints();
        gbcC.insets = new Insets(5, 5, 5, 5);
        gbcC.gridx = 0; gbcC.gridy = 0; panelC.add(new JLabel("Código de Bulto Asignado:"), gbcC);
        gbcC.gridx = 1; gbcC.fill = GridBagConstraints.HORIZONTAL; gbcC.weightx = 1.0;
        txtCodigoBulto = new JTextField();
        txtCodigoBulto.setEditable(false);
        txtCodigoBulto.setFont(new Font("SansSerif", Font.BOLD, 12));
        panelC.add(txtCodigoBulto, gbcC);
        gbcC.gridx = 2; gbcC.fill = GridBagConstraints.NONE; gbcC.weightx = 0;
        btnGenerarImprimir = new JButton("Generar e Imprimir Tarjeta");
        panelC.add(btnGenerarImprimir, gbcC);
        centerPanel.add(panelC);
        
        // --- Sección D y Botón Finalizar ---
        JPanel southPanel = new JPanel(new BorderLayout());
        JPanel panelD = new JPanel();
        panelD.setBorder(BorderFactory.createTitledBorder("D. CONFIRMACIÓN DE ENTREGA"));
        chkEntregaCliente = new JCheckBox("Se entrega la tarjeta de bulto al cliente para que la adhiera al paquete.");
        panelD.add(chkEntregaCliente);
        southPanel.add(panelD, BorderLayout.CENTER);

        btnFinalizar = new JButton("FINALIZAR PROCESO DE ETIQUETADO");
        JPanel finalButtonPanel = new JPanel();
        finalButtonPanel.add(btnFinalizar);
        southPanel.add(finalButtonPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);
        
        // Estado inicial de los componentes
        setPanelEnabled(panelB, false);
        setPanelEnabled(panelC, false);
        setPanelEnabled(panelD, false);

        // --- Lógica de Eventos ---
        btnBuscar.addActionListener(e -> buscarConsolidacion());
        txtCodigoConsolidacion.addActionListener(e -> buscarConsolidacion());
        btnGenerarImprimir.addActionListener(e -> generarEImprimir());
        btnFinalizar.addActionListener(e -> finalizarEtiquetado());
    }

    private void buscarConsolidacion() {
        String codigo = txtCodigoConsolidacion.getText().trim();
        // Simulación de búsqueda: si el código empieza con "CON", lo encontramos
        if (codigo.toUpperCase().startsWith("CON-")) {
            lblDatosCliente.setText("Jhon Stivenson Méndez - C.C. 1094xxxxxx");
            lblCantidadPaquetes.setText("3");
            lblDestino.setText("Armenia - Quindío");
            
            setPanelEnabled((JPanel) lblDatosCliente.getParent(), true); // Habilita panelB
            setPanelEnabled((JPanel) txtCodigoBulto.getParent(), true); // Habilita panelC
            JOptionPane.showMessageDialog(this, "Consolidación encontrada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Código de consolidación no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generarEImprimir() {
        // Generar un código único para el bulto
        String uniqueID = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        txtCodigoBulto.setText("BTO-ARM-" + uniqueID);

        // Simular la impresión
        JOptionPane.showMessageDialog(this, 
            "Enviando a la etiquetadora... Por favor, retire la tarjeta impresa y entréguela al cliente.", 
            "Imprimiendo Tarjeta", JOptionPane.INFORMATION_MESSAGE);

        // Habilitar la confirmación
        setPanelEnabled((JPanel) chkEntregaCliente.getParent(), true); // Habilita panelD
        btnGenerarImprimir.setEnabled(false);
    }
    
    private void finalizarEtiquetado() {
        if (!chkEntregaCliente.isSelected()) {
            JOptionPane.showMessageDialog(this, "Debe confirmar la entrega de la tarjeta al cliente.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Proceso de etiquetado finalizado con éxito.", "Proceso Completado", JOptionPane.INFORMATION_MESSAGE);
        
       
        if (onCompleteCallback != null) {
            onCompleteCallback.run(); // Avisa al menú principal
        }
        dispose(); // Cierra esta ventana
    }

    // Método utilitario para habilitar/deshabilitar todos los componentes de un panel
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
        SwingUtilities.invokeLater(() -> {
            new FormularioActividad4(() -> {
                System.out.println("Prueba de Main: Actividad 4 completada.");
            }).setVisible(true);
        });
    }
}