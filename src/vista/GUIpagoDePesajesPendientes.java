package vista;

import controlador.ControladorProduccion;
import utilidades.Rut;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.Arrays;

public class GUIpagoDePesajesPendientes extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField IDpago;
    private JTextField RUT;
    private JTable tabla;

    private ControladorProduccion control = ControladorProduccion.getInstance();

    private String[] pagosOriginales;

    public GUIpagoDePesajesPendientes() {

        control.readDataFromTextFile("InputDataGestionHuertos.txt");

        pagosOriginales = control.listPesajes();

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        cargarTablaCompleta();

        IDpago.addActionListener(e -> filtrar());
        RUT.addActionListener(e -> filtrar());

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


    private void cargarTablaCompleta() {
        filtrarPagos("", "");
    }


    private void filtrar() {
        String id = IDpago.getText().trim();
        String rut = RUT.getText().trim();
        filtrarPagos(id, rut);
    }

    private void filtrarPagos(String id, String rut) {

        String[][] datos = Arrays.stream(pagosOriginales)
                .map(l -> l.trim().replaceAll("\\s+", " ").split(" "))
                .filter(p -> {
                    boolean okId = id.isEmpty() || p[0].equals(id);
                    boolean okRut = rut.isEmpty() || p[2].equals(rut);
                    return okId && okRut;
                })
                .map(p -> new String[]{
                        p[0], // ID
                        p[1], // Fecha
                        p[3], // Calidad
                        p[4], // Kilos
                        p[5], // Precio Kg
                        p[6], // Monto
                        p[7]  // Pagado
                })
                .toArray(String[][]::new);

        String[] columnas = {
                "ID", "Fecha", "Calidad", "Kilos",
                "Precio Kg", "Monto", "Pagado"
        };

        tabla.setModel(new DefaultTableModel(datos, columnas));
    }


    private void onOK() {

        String idTexto = IDpago.getText().trim();
        String rutTexto = RUT.getText().trim();

        if (idTexto.isEmpty() || rutTexto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar ID y RUT",
                    "AVISO",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            int id = Integer.parseInt(idTexto);
            Rut rut = Rut.of(rutTexto);

            control.addPagoPesaje(id, rut);

            JOptionPane.showMessageDialog(
                    this,
                    "El pago fue efectuado correctamente",
                    "AVISO",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // recargar datos
            control.readDataFromTextFile("InputDataGestionHuertos.txt");
            pagosOriginales = control.listPesajes();
            filtrar();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        ControladorProduccion control = ControladorProduccion.getInstance();
        control.readDataFromTextFile("InputDataGestionHuertos.txt");

        GUIpagoDePesajesPendientes dialog = new GUIpagoDePesajesPendientes();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        System.exit(0);
    }
}
