package MenuNavegacion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

// Importar cada uno de tus formularios de actividad
import Actividad1.FormularioActividad1;
import Actividad2.FormularioActividad2;
import Actividad3.FormularioActividad3;
import Actividad4.FormularioActividad4;
import Actividad5.FormularioActividad5;
import Actividad6.FormularioActividad6;
import Actividad7.FormularioActividad7;
import Actividad8.FormularioActividad8;

public class MenuPrincipal extends JFrame {

    // --- Variables de Estado para las Reglas de Negocio ---
    // (Controlan qué actividades se han completado)
    private boolean actividad1Completada = false;
    private boolean actividad2Completada = false;
    private boolean actividad3Completada = false;
    private boolean actividad4Completada = false;
    private boolean actividad5Completada = false;
    private boolean actividad6Completada = false;
    private boolean actividad7Completada = false;
    

    // --- Componentes de la UI (para poder habilitar/deshabilitar) ---
    private JButton btnActividad1;
    private JButton btnActividad2;
    private JButton btnActividad3;
    private JButton btnActividad4;
    private JButton btnActividad5;
    private JButton btnActividad6;
    private JButton btnActividad7;
    private JButton btnActividad8;


    public MenuPrincipal() {
        // --- Configuración de la ventana principal ---
        setTitle("Proceso MIS-03");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600); // Tamaño de la ventana del menú
        setLocationRelativeTo(null);
        
        // --- Panel Principal con padding ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        getContentPane().add(mainPanel);

        // --- Título ---
        JLabel lblTitulo = new JLabel("Prototipo navegable: MIS-03", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);
        
        // --- Subtítulo ---
        JLabel lblSubtitulo = new JLabel("Seleccione la actividad:", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSubtitulo.setBorder(new EmptyBorder(0, 0, 15, 0));
        mainPanel.add(lblSubtitulo, BorderLayout.CENTER);

        // --- Panel de Botones (Grid) ---
        // Un grid de 4 filas x 2 columnas para los 8 botones
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 15, 15)); // 4 filas, 2 cols, 15px gap
        buttonPanel.setBackground(Color.WHITE);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // --- Crear y agregar los 8 botones ---
        // Se pasa una "función de callback" (Runnable) a cada formulario.
        // El formulario la ejecutará cuando termine con éxito.

        btnActividad1 = crearBotonActividad("1. Registrar verificación paquete", e -> {
            FormularioActividad1 form1 = new FormularioActividad1("Jefe de Acopio (Usuario)", () -> {
                this.actividad1Completada = true;
                this.actualizarEstadoBotones();
            });
            form1.setVisible(true);
        });
        buttonPanel.add(btnActividad1);
        
        btnActividad2 = crearBotonActividad("2. Registrar admisión comprobante", e -> {
            FormularioActividad2 form2 = new FormularioActividad2(() -> {
                this.actividad2Completada = true;
                this.actualizarEstadoBotones();
            });
            form2.setVisible(true);
        });
        buttonPanel.add(btnActividad2);

        btnActividad3 = crearBotonActividad("3. Registrar firma acta", e -> {
            FormularioActividad3 form3 = new FormularioActividad3(() -> {
                this.actividad3Completada = true;
                this.actualizarEstadoBotones();
            });
            form3.setVisible(true);
        });
        buttonPanel.add(btnActividad3);
        
        btnActividad4 = crearBotonActividad("4. Generar tarjeta bulto", e -> {
            FormularioActividad4 form4 = new FormularioActividad4(() -> {
                this.actividad4Completada = true;
                this.actualizarEstadoBotones();
            });
            form4.setVisible(true);
        });
        buttonPanel.add(btnActividad4);
        
        btnActividad5 = crearBotonActividad("5. Registrar verificación carga", e -> {
            FormularioActividad5 form5 = new FormularioActividad5(() -> {
                this.actividad5Completada = true;
                this.actualizarEstadoBotones();
            });
            form5.setVisible(true);
        });
        buttonPanel.add(btnActividad5);
        
        btnActividad6 = crearBotonActividad("6. Confirmar orden despacho", e -> {
            FormularioActividad6 form6 = new FormularioActividad6(() -> {
                this.actividad6Completada = true;
                this.actualizarEstadoBotones();
            });
            form6.setVisible(true);
        });
        buttonPanel.add(btnActividad6);
        
        btnActividad7 = crearBotonActividad("7. Registrar salida vehículo", e -> {
            FormularioActividad7 form7 = new FormularioActividad7(() -> {
                this.actividad7Completada = true;
                this.actualizarEstadoBotones();
            });
            form7.setVisible(true);
        });
        buttonPanel.add(btnActividad7);
        
        btnActividad8 = crearBotonActividad("8. Enviar notificación salida", e -> {
            FormularioActividad8 form8 = new FormularioActividad8(() -> {
                
            });
            form8.setVisible(true);
        });
        buttonPanel.add(btnActividad8);

        // --- Estado inicial de Botones ---
        actualizarEstadoBotones();
    }

    /**
     * Método para habilitar/deshabilitar botones según las reglas de negocio.
     */
    private void actualizarEstadoBotones() {
        // El flujo es una secuencia estricta:
        btnActividad1.setEnabled(true); // Siempre habilitado
        btnActividad2.setEnabled(actividad1Completada);
        btnActividad3.setEnabled(actividad2Completada);
        btnActividad4.setEnabled(actividad3Completada);
        btnActividad5.setEnabled(actividad4Completada);
        btnActividad6.setEnabled(actividad5Completada);
        btnActividad7.setEnabled(actividad6Completada);
        btnActividad8.setEnabled(actividad7Completada);
    }

    /**
     * Método de utilidad para crear botones con un estilo unificado.
     */
    private JButton crearBotonActividad(String texto, java.awt.event.ActionListener accion) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 14));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(200, 60)); // Altura del botón
        boton.setBackground(new Color(240, 240, 240));
        boton.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(220, 235, 255)); // Azul claro
                boton.setBorder(BorderFactory.createLineBorder(new Color(0, 120, 215)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(240, 240, 240));
                boton.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
            }
        });

        // Asignar la acción (abrir el formulario)
        boton.addActionListener(accion);
        return boton;
    }

    // Método main para ejecutar el menú principal
    public static void main(String[] args) {
        // Asegura que la UI se cree en el hilo de despacho de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MenuPrincipal().setVisible(true);
        });
    }

}