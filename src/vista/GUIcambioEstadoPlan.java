package vista;

import controlador.ControladorProduccion;
import utilidades.EstadoPlan;

import javax.swing.*;
import java.awt.event.*;

public class GUIcambioEstadoPlan extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField txtId;
    private JLabel txtNombre;
    private JLabel txtMeta;
    private JLabel txtEstadoActual;
    private JComboBox<EstadoPlan> nuevoEstado;

    private final ControladorProduccion control = ControladorProduccion.getInstance();

    public GUIcambioEstadoPlan() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Estado inicial
        limpiarCampos();

        // Cargar enum en ComboBox
        nuevoEstado.setModel(
                new DefaultComboBoxModel<>(EstadoPlan.values())
        );
        nuevoEstado.setSelectedIndex(-1);
        nuevoEstado.setEnabled(false);

        txtId.addActionListener(e -> cargarPlan());

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(
                e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        );
    }

    // ===============================
    // CARGA DEL PLAN DESDE LISTADO
    // ===============================
    private void cargarPlan() {

        int idBuscado;

        try {
            idBuscado = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "ID inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            limpiarCampos();
            return;
        }

        String[] planes = control.listPlanesCosecha();

        if (planes.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "No existen planes de cosecha",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            return;
        }

        String linea = null;

        for (String p : planes) {
            int id = Integer.parseInt(p.substring(0, 15).trim());
            if (id == idBuscado) {
                linea = p;
                break;
            }
        }

        if (linea == null) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró el plan",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            limpiarCampos();
            return;
        }

        // Extracción por columnas (según String.format)
        txtNombre.setText(linea.substring(15, 30).trim());
        txtMeta.setText(linea.substring(70, 85).trim());
        txtEstadoActual.setText(linea.substring(100, 120).trim());

        nuevoEstado.setEnabled(true);
        buttonOK.setEnabled(true);
    }

    // ===============================
    // CAMBIO DE ESTADO
    // ===============================
    private void onOK() {

        EstadoPlan estadoNuevo = (EstadoPlan) nuevoEstado.getSelectedItem();

        if (estadoNuevo == null) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un nuevo estado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idPlan = Integer.parseInt(txtId.getText().trim());
        String nombrePlan = txtNombre.getText();
        int idCuartel = obtenerIdCuartelDesdeListado(idPlan);

        control.changeEstadoPlan(idCuartel, estadoNuevo);

        JOptionPane.showMessageDialog(this,
                "Estado del plan actualizado correctamente",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        dispose();
    }

    private int obtenerIdCuartelDesdeListado(int idPlan) {

        for (String p : control.listPlanesCosecha()) {
            int id = Integer.parseInt(p.substring(0, 15).trim());
            if (id == idPlan) {
                return Integer.parseInt(p.substring(120, 140).trim());
            }
        }
        throw new IllegalStateException("Plan inconsistente");
    }

    // ===============================
    // LIMPIEZA DE CAMPOS
    // ===============================
    private void limpiarCampos() {
        txtNombre.setText("");
        txtMeta.setText("");
        txtEstadoActual.setText("");

        nuevoEstado.setSelectedIndex(-1);
        nuevoEstado.setEnabled(false);
        buttonOK.setEnabled(false);
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        GUIcambioEstadoPlan dialog = new GUIcambioEstadoPlan();
        dialog.setVisible(true);
        System.exit(0);
    }
}
