package com.uniquindio.qmd.view;

import com.uniquindio.qmd.controller.ModelController;
import com.uniquindio.qmd.model.Ciudadano;
import com.uniquindio.qmd.model.Producto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class CatalogoView extends JFrame {

    private ModelController controller;
    private Ciudadano ciudadano;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtBuscar;

    // Colores Naranja
    private Color colorHeader = new Color(255, 152, 0); 
    private Color colorBtn = new Color(239, 108, 0); 

    public CatalogoView(Ciudadano ciudadano, ModelController controller) {
        this.ciudadano = ciudadano;
        this.controller = controller;

        setTitle("QMD - Catálogo de Productos - Usuario: " + ciudadano.getNombre());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- HEADER (Naranja) ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(colorHeader);
        pnlHeader.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        JLabel lblTitle = new JLabel("Catálogo de productos disponibles");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        
        JLabel lblSub = new JLabel("Consulta de inventario para préstamos y reservas");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblSub.setForeground(new Color(255, 240, 230));

        JPanel pnlTitulos = new JPanel();
        pnlTitulos.setLayout(new BoxLayout(pnlTitulos, BoxLayout.Y_AXIS));
        pnlTitulos.setOpaque(false);
        pnlTitulos.add(lblTitle);
        pnlTitulos.add(lblSub);
        pnlHeader.add(pnlTitulos, BorderLayout.CENTER);
        
        add(pnlHeader, BorderLayout.NORTH);

        // --- CONTENIDO ---
        JPanel pnlBody = new JPanel(new BorderLayout(20, 20));
        pnlBody.setBackground(Color.WHITE);
        pnlBody.setBorder(new EmptyBorder(20, 40, 20, 40));

        // Barra de Búsqueda
        JPanel pnlSearch = new JPanel(new BorderLayout(10, 0));
        pnlSearch.setBackground(Color.WHITE);
        
        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(300, 40));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(colorBtn);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnBuscar.setFocusPainted(false);
        
        pnlSearch.add(new JLabel("Buscar objeto: "), BorderLayout.WEST);
        pnlSearch.add(txtBuscar, BorderLayout.CENTER);
        pnlSearch.add(btnBuscar, BorderLayout.EAST);
        
        pnlBody.add(pnlSearch, BorderLayout.NORTH);

        // Tabla
        String[] columnas = {"CÓDIGO", "NOMBRE PRODUCTO", "DESCRIPCIÓN", "CATEGORÍA", "ESTADO", "ACCIÓN"};
        modelo = new DefaultTableModel(columnas, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        tabla = new JTable(modelo);
        tabla.setRowHeight(40);
        tabla.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(240, 240, 240));
        tabla.setSelectionBackground(new Color(255, 224, 178));
        
        // Renderizar columna de estado con colores (Simulación visual)
        tabla.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String estado = (String) value;
                if ("Disponible".equalsIgnoreCase(estado)) {
                    l.setForeground(new Color(0, 128, 0)); // Verde
                    l.setText("● Disponible");
                } else if ("Prestado".equalsIgnoreCase(estado)) {
                    l.setForeground(new Color(200, 150, 0)); // Naranja oscuro
                    l.setText("● Prestado");
                } else {
                    l.setForeground(Color.RED);
                    l.setText("● " + estado);
                }
                l.setHorizontalAlignment(SwingConstants.CENTER);
                return l;
            }
        });

        // Scroll
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        pnlBody.add(scroll, BorderLayout.CENTER);

        add(pnlBody, BorderLayout.CENTER);

        // --- EVENTOS ---
        cargarProductos("");

        btnBuscar.addActionListener(e -> cargarProductos(txtBuscar.getText()));
        
        // Clic en la tabla para "Reservar"
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                int col = tabla.getSelectedColumn();
                
                // Si hacen clic en cualquier parte de la fila (o especificamente en la ultima columna)
                if (fila >= 0) {
                    String codigo = (String) modelo.getValueAt(fila, 0);
                    String estado = (String) modelo.getValueAt(fila, 4); // Columna estado original
                    
                    if ("Disponible".equalsIgnoreCase(estado) || "● Disponible".equals(estado)) {
                        // Obtener objeto completo
                        Producto pSeleccionado = new Producto(
                            codigo, 
                            (String) modelo.getValueAt(fila, 1),
                            (String) modelo.getValueAt(fila, 2),
                            (String) modelo.getValueAt(fila, 3),
                            "Disponible"
                        );
                        
                        // Abrir Modal de Reserva (Imagen 4)
                        new FormularioReservaView(CatalogoView.this, ciudadano, pSeleccionado, controller).setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(CatalogoView.this, "Este ítem no está disponible para reserva.");
                    }
                }
            }
        });
    }

    public void cargarProductos(String filtro) {
        modelo.setRowCount(0);
        List<Producto> lista = controller.obtenerProductos(filtro);
        for (Producto p : lista) {
            modelo.addRow(new Object[]{
                p.getCodigo(), p.getNombre(), p.getDescripcion(), p.getCategoria(), p.getEstado(), "RESERVAR >"
            });
        }
    }
}
