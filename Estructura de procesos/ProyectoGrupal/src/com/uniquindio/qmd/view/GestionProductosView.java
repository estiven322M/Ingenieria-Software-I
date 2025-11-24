package com.uniquindio.qmd.view;

import com.uniquindio.qmd.controller.ModelController;
import com.uniquindio.qmd.model.Producto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionProductosView extends JFrame {

    private ModelController controller;
    private JTextField txtCodigo, txtNombre, txtDescripcion, txtCategoria;
    private JComboBox<String> cbEstado;
    private JTable tabla;
    private DefaultTableModel modelo;
    
    // Colores Admin (Gris Oscuro / Azul Acero)
    private Color colorHeader = new Color(44, 62, 80); 
    private Color colorSidebar = new Color(236, 240, 241);
    private Color colorBtnAdd = new Color(39, 174, 96);   // Verde
    private Color colorBtnEdit = new Color(41, 128, 185); // Azul
    private Color colorBtnDel = new Color(192, 57, 43);   // Rojo

    public GestionProductosView(ModelController controller) {
        this.controller = controller;
        setTitle("QMD - Gestión de Inventario (Administrador)");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- HEADER ---
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(colorHeader);
        pnlHeader.setBorder(new EmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitle = new JLabel("Gestión de Inventario");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setBackground(new Color(149, 165, 166));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        pnlHeader.add(lblTitle, BorderLayout.WEST);
        pnlHeader.add(btnLogout, BorderLayout.EAST);
        add(pnlHeader, BorderLayout.NORTH);

        // --- PANEL CENTRAL (Split: Formulario Izq | Tabla Der) ---
        JPanel pnlCentral = new JPanel(new BorderLayout());
        
        // 1. Formulario (Izquierda)
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setPreferredSize(new Dimension(320, 0));
        pnlForm.setBackground(colorSidebar);
        pnlForm.setBorder(new EmptyBorder(10, 15, 10, 15));
        
        // Título del formulario
        JLabel lblFormTitle = new JLabel("Detalle del Producto");
        lblFormTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblFormTitle.setForeground(colorHeader);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0; gbc.gridy = 0;
        pnlForm.add(lblFormTitle, gbc);
        
        gbc.gridy++; pnlForm.add(new JSeparator(), gbc);

        // Campos
        txtCodigo = crearInput();
        txtNombre = crearInput();
        txtDescripcion = crearInput();
        txtCategoria = crearInput();
        cbEstado = new JComboBox<>(new String[]{"Disponible", "Prestado", "Mantenimiento", "Reservado"});
        cbEstado.setBackground(Color.WHITE);

        agregarCampo(pnlForm, gbc, 2, "Código:", txtCodigo);
        agregarCampo(pnlForm, gbc, 4, "Nombre:", txtNombre);
        agregarCampo(pnlForm, gbc, 6, "Descripción:", txtDescripcion);
        agregarCampo(pnlForm, gbc, 8, "Categoría:", txtCategoria);
        agregarCampo(pnlForm, gbc, 10, "Estado Actual:", cbEstado);

        // Botones de Acción
        JPanel pnlBtns = new JPanel(new GridLayout(1, 3, 5, 0));
        pnlBtns.setOpaque(false);
        
        JButton btnAdd = crearBoton("Crear", colorBtnAdd);
        JButton btnEdit = crearBoton("Editar", colorBtnEdit);
        JButton btnDel = crearBoton("Borrar", colorBtnDel);
        
        pnlBtns.add(btnAdd);
        pnlBtns.add(btnEdit);
        pnlBtns.add(btnDel);
        
        gbc.gridy = 12; 
        gbc.insets = new Insets(20, 5, 5, 5);
        pnlForm.add(pnlBtns, gbc);
        
        JButton btnClear = new JButton("Limpiar Formulario");
        btnClear.setBackground(Color.WHITE);
        gbc.gridy = 13;
        gbc.insets = new Insets(5, 5, 5, 5);
        pnlForm.add(btnClear, gbc);

        pnlCentral.add(pnlForm, BorderLayout.WEST);

        // 2. Tabla (Centro/Derecha)
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlTable.setBackground(Color.WHITE);

        String[] cols = {"CÓDIGO", "NOMBRE", "DESCRIPCIÓN", "CATEGORÍA", "ESTADO"};
        modelo = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.getTableHeader().setBackground(new Color(236, 240, 241));
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabla.setSelectionBackground(new Color(220, 230, 240));
        
        // Renderizar colores de estado
        tabla.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String s = (String) value;
                if("Disponible".equalsIgnoreCase(s)) l.setForeground(new Color(39, 174, 96));
                else if("Prestado".equalsIgnoreCase(s)) l.setForeground(new Color(243, 156, 18));
                else if("Mantenimiento".equalsIgnoreCase(s)) l.setForeground(new Color(192, 57, 43));
                else l.setForeground(Color.BLACK);
                l.setFont(l.getFont().deriveFont(Font.BOLD));
                return l;
            }
        });

        pnlTable.add(new JScrollPane(tabla), BorderLayout.CENTER);
        pnlCentral.add(pnlTable, BorderLayout.CENTER);

        add(pnlCentral, BorderLayout.CENTER);

        // --- LÓGICA ---
        cargarTabla();

        // Listener de Selección
        tabla.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tabla.getSelectedRow();
                if (fila >= 0) {
                    txtCodigo.setText(modelo.getValueAt(fila, 0).toString());
                    txtNombre.setText(modelo.getValueAt(fila, 1).toString());
                    txtDescripcion.setText(modelo.getValueAt(fila, 2).toString());
                    txtCategoria.setText(modelo.getValueAt(fila, 3).toString());
                    cbEstado.setSelectedItem(modelo.getValueAt(fila, 4).toString());
                    txtCodigo.setEditable(false); // No editar ID existente
                }
            }
        });

        btnClear.addActionListener(e -> limpiar());

        // Acción CREAR
        btnAdd.addActionListener(e -> {
            if(validarCampos()) {
                Producto p = new Producto(txtCodigo.getText(), txtNombre.getText(), txtDescripcion.getText(), txtCategoria.getText(), cbEstado.getSelectedItem().toString());
                if(controller.crearProducto(p)) {
                    JOptionPane.showMessageDialog(this, "Producto creado correctamente.");
                    limpiar();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error: El código ya existe.");
                }
            }
        });

        // Acción EDITAR
        btnEdit.addActionListener(e -> {
            if(txtCodigo.getText().isEmpty()) return;
            if(validarCampos()) {
                Producto p = new Producto(txtCodigo.getText(), txtNombre.getText(), txtDescripcion.getText(), txtCategoria.getText(), cbEstado.getSelectedItem().toString());
                if(controller.actualizarProducto(p)) {
                    JOptionPane.showMessageDialog(this, "Producto actualizado.");
                    limpiar();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar.");
                }
            }
        });

        // Acción BORRAR
        btnDel.addActionListener(e -> {
            String codigo = txtCodigo.getText();
            if(codigo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto primero.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro de eliminar el producto " + codigo + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_OPTION) {
                if(controller.eliminarProducto(codigo)) {
                    JOptionPane.showMessageDialog(this, "Producto eliminado.");
                    limpiar();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(this, "No se puede eliminar (Posiblemente tiene reservas asociadas).");
                }
            }
        });
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        List<Producto> lista = controller.obtenerProductos("");
        for(Producto p : lista) {
            modelo.addRow(new Object[]{p.getCodigo(), p.getNombre(), p.getDescripcion(), p.getCategoria(), p.getEstado()});
        }
    }

    private void limpiar() {
        txtCodigo.setText(""); txtCodigo.setEditable(true);
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtCategoria.setText("");
        cbEstado.setSelectedIndex(0);
        tabla.clearSelection();
    }

    private boolean validarCampos() {
        if(txtCodigo.getText().isEmpty() || txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Código y Nombre son obligatorios.");
            return false;
        }
        return true;
    }

    private void agregarCampo(JPanel p, GridBagConstraints gbc, int y, String label, JComponent c) {
        gbc.gridy = y;
        p.add(new JLabel(label), gbc);
        gbc.gridy = y + 1;
        p.add(c, gbc);
    }

    private JTextField crearInput() {
        JTextField t = new JTextField();
        t.setPreferredSize(new Dimension(200, 30));
        return t;
    }

    private JButton crearBoton(String texto, Color bg) {
        JButton b = new JButton(texto);
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }
}
