package vista;

import controlador.ControladorProduccion;
import utilidades.Calidad;
import utilidades.Rut;

import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GUIagregacionDePesajeCosechador extends JDialog {

    private JPanel contentPane;
    private JPanel Plan;
    private JButton buttonOK;
    private JButton buttonCancel;

    private JTextField txtIdPesaje;
    private JTextField txtKilos;

    private JComboBox<String> cbCosechador;
    private JComboBox<String> cbPlan;
    private JComboBox<String> cbCuadrilla;
    private JComboBox<Calidad> cbCalidad;

    private final ControladorProduccion control =
            ControladorProduccion.getInstance();

    public GUIagregacionDePesajeCosechador() {

        setContentPane(contentPane);
        setModal(true);
        setSize(520, 420);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(buttonOK);

        cargarDatosIniciales();

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

    // =========================================
    // CARGA DE COMBOS
    // =========================================
    private void cargarDatosIniciales() {

        // Datos que el controlador SÍ expone
        control.readDataFromTextFile("InputDataGestionHuertos.txt");

        cbCosechador.setModel(
                new DefaultComboBoxModel<>(control.listCosechadores())
        );

        cbPlan.setModel(
                new DefaultComboBoxModel<>(control.listPlanesCosecha())
        );

        // Cuadrillas desde TXT (no existe listCuadrillas)
        cbCuadrilla.setModel(
                new DefaultComboBoxModel<>(cargarCuadrillasDesdeTxt())
        );

        cbCalidad.setModel(
                new DefaultComboBoxModel<>(Calidad.values())
        );

        cbCosechador.setSelectedIndex(-1);
        cbPlan.setSelectedIndex(-1);
        cbCuadrilla.setSelectedIndex(-1);
        cbCalidad.setSelectedIndex(-1);
    }

    // =========================================
    // LECTURA DE CUADRILLAS DESDE TXT
    // =========================================
    private String[] cargarCuadrillasDesdeTxt() {

        List<String> cuadrillas = new ArrayList<>();

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader("InputDataGestionHuertos.txt"))) {

            String linea;
            boolean enCuadrillas = false;

            while ((linea = br.readLine()) != null) {

                linea = linea.trim();

                if (linea.isEmpty()) continue;

                if (linea.startsWith("#")) {
                    enCuadrillas = linea.equalsIgnoreCase("#CUADRILLAS");
                    continue;
                }

                if (enCuadrillas) {
                    cuadrillas.add(linea);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error al leer las cuadrillas desde el archivo",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return cuadrillas.toArray(new String[0]);
    }

    // =========================================
    // OK
    // =========================================
    private void onOK() {

        if (txtIdPesaje.getText().trim().isEmpty()) {
            error("Debe ingresar el ID del pesaje");
            return;
        }

        if (txtKilos.getText().trim().isEmpty()) {
            error("Debe ingresar la cantidad de kilos");
            return;
        }

        if (cbCosechador.getSelectedItem() == null) {
            error("Debe seleccionar un cosechador");
            return;
        }

        if (cbPlan.getSelectedItem() == null) {
            error("Debe seleccionar un plan");
            return;
        }

        if (cbCuadrilla.getSelectedItem() == null) {
            error("Debe seleccionar una cuadrilla");
            return;
        }

        if (cbCalidad.getSelectedItem() == null) {
            error("Debe seleccionar la calidad");
            return;
        }

        try {
            int idPesaje = Integer.parseInt(txtIdPesaje.getText().trim());
            float kilos = Float.parseFloat(txtKilos.getText().trim());

            // ===== PARSEO DESDE TXT =====
            String cosechadorLinea = cbCosechador.getSelectedItem().toString();
            String planLinea = cbPlan.getSelectedItem().toString();
            String cuadrillaLinea = cbCuadrilla.getSelectedItem().toString();

            String[] datosCosechador = cosechadorLinea.split("\\s+");
            String[] datosPlan = planLinea.split("\\s+");
            String[] datosCuadrilla = cuadrillaLinea.split("\\s+");

            Rut rut = Rut.of(datosCosechador[1]);   // RUT
            int idPlan = Integer.parseInt(datosPlan[0]);
            int idCuadrilla = Integer.parseInt(datosCuadrilla[0]);
            Calidad calidad = (Calidad) cbCalidad.getSelectedItem();

            control.addPesaje(
                    idPesaje,
                    rut,
                    idPlan,
                    idCuadrilla,
                    kilos,
                    calidad
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Pesaje agregado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

        } catch (NumberFormatException e) {
            error("Formato numérico inválido");
        } catch (Exception e) {
            error(e.getMessage());
        }
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(
                this,
                msg,
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        GUIagregacionDePesajeCosechador dialog =
                new GUIagregacionDePesajeCosechador();
        dialog.setVisible(true);
        System.exit(0);
    }
}
