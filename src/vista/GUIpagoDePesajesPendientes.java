package vista;

import controlador.ControladorProduccion;
import utilidades.Rut;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class GUIpagoDePesajesPendientes extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField IDpago;
    private JTextField RUT;
    private JTable tabla;
    private ControladorProduccion control = ControladorProduccion.getInstance();
    private String[] lPes = control.listPesajes();
    private String[] listaRuts = new String[lPes.length];
    private String[] listaPagos = new String[lPes.length];
    private String[][] datos = new String[listaPagos.length][7];

    public void UpdateList() {
            control.readDataFromTextFile("InputDataGestionHuertos.txt");


        for (int i = 0; i < listaPagos.length; i++) {
        listaPagos[i] = lPes[i];
    }

    String[] nombreColumnas = {"ID", "Fecha", "Calidad", "Kilos", "Precio Kg", "Monto", "Pagado"};


        for (int i = 0; i < listaRuts.length; i++) {
        String[] linea = listaPagos[i].trim().replaceAll("\\s+", " ").split(" ");
        datos[i][0] = linea[0];
        datos[i][1] = linea[1];
        listaRuts[i] = linea[2];
        datos[i][2] = linea[3];
        datos[i][3] = linea[4];
        datos[i][4] = linea[5];
        datos[i][5] = linea[6];
        datos[i][6] = linea[7];

    };
        tabla.setModel(new DefaultTableModel(datos, nombreColumnas));
    ///tabla.enable();
    }

    public GUIpagoDePesajesPendientes() {
        control.readDataFromTextFile("InputDataGestionHuertos.txt");
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


        for (int i = 0; i < listaPagos.length; i++) {
            listaPagos[i] = lPes[i];
        }

        String[] nombreColumnas = {"ID", "Fecha", "Calidad", "Kilos", "Precio Kg", "Monto", "Pagado"};


        for (int i = 0; i < listaRuts.length; i++) {
            String[] linea = listaPagos[i].trim().replaceAll("\\s+", " ").split(" ");
            datos[i][0] = linea[0];
            datos[i][1] = linea[1];
            listaRuts[i] = linea[2];
            datos[i][2] = linea[3];
            datos[i][3] = linea[4];
            datos[i][4] = linea[5];
            datos[i][5] = linea[6];
            datos[i][6] = linea[7];

         };
        tabla.setModel(new DefaultTableModel(datos, nombreColumnas));
        ///tabla.enable();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        String idpago = IDpago.getText().trim();
        String rut = RUT.getText().trim();

        boolean rutExiste = false;
        boolean idExiste = false;

        for (int i = 0; i < listaRuts.length; i++) {
            if (listaRuts[i].equals(rut)) {
                rutExiste = true;
                break;
            }
        }

        if (!rutExiste) {
            JOptionPane.showMessageDialog(this, "El RUT no existe", "AVISO", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (int i = 0; i < datos.length; i++) {
            if (idpago.equals(datos[i][0])) {
                idExiste = true;
                break;
            }
        }

        if (!idExiste) {
            JOptionPane.showMessageDialog(this, "El ID de pesaje no existe", "AVISO", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            control.addPagoPesaje(Integer.parseInt(idpago), Rut.of(rut));
            JOptionPane.showMessageDialog(
                    this,
                    "El pago fue efectuado correctamente",
                    "AVISO",
                    JOptionPane.INFORMATION_MESSAGE
            );
            UpdateList();
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
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
        System.exit(0);
    }
}
