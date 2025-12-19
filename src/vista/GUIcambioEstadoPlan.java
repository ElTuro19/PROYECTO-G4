package vista;

import controlador.ControladorProduccion;
import utilidades.EstadoPlan;

import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

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

    private String[] planesOriginales;

    public GUIcambioEstadoPlan() {

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(500, 400);
        setLocationRelativeTo(null);

        limpiarCampos();

        nuevoEstado.setModel(new DefaultComboBoxModel<>(EstadoPlan.values()));
        nuevoEstado.setSelectedIndex(-1);
        nuevoEstado.setEnabled(false);

        control.readDataFromTextFile("InputDataGestionHuertos.txt");
        planesOriginales = control.listPlanesCosecha();

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

    private void cargarPlan() {

        int idBuscado;

        try {
            idBuscado = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "ID inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            limpiarCampos();
            return;
        }

        if (planesOriginales.length == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "No existen planes de cosecha",
                    "Información",
                    JOptionPane.INFORMATION_MESSAGE
            );
            limpiarCampos();
            return;
        }

        String[] planEncontrado = Arrays.stream(planesOriginales)
                .map(l -> l.trim().replaceAll("\\s+", " ").split(" "))
                .filter(p -> Integer.parseInt(p[0]) == idBuscado)
                .findFirst()
                .orElse(null);

        if (planEncontrado == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "No se encontró el plan",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            limpiarCampos();
            return;
        }

        // Mapeo directo por columnas
        txtNombre.setText(planEncontrado[1]);
        txtMeta.setText(planEncontrado[3]);
        txtEstadoActual.setText(planEncontrado[5]);

        nuevoEstado.setEnabled(true);
        buttonOK.setEnabled(true);
    }

    private void onOK() {

        EstadoPlan estadoNuevo = (EstadoPlan) nuevoEstado.getSelectedItem();

        if (estadoNuevo == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un nuevo estado",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int idPlan;
        try {
            idPlan = Integer.parseInt(txtId.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "ID inválido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            control.changeEstadoPlan(idPlan, estadoNuevo);

            JOptionPane.showMessageDialog(
                    this,
                    "Estado del plan actualizado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

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
