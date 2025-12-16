package vista;

import controlador.ControladorProduccion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class GUIlistarCosechadores extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTable tablaCos;
    private ControladorProduccion control = ControladorProduccion.getInstance();

    public GUIlistarCosechadores() {
        setContentPane(contentPane);
        setModal(true);

        String[] lCos = control.listCosechadores();

        String[] nombreColumnas = {"Rut", "Nombre", "Direccion", "email", "Fecha Nac.", "Nro. Cuadrillas", "Monto Impago$", "Monto Pagado$"};
        String[][] datos = new String[lCos.length][8];

        for (int i = 0; i < lCos.length; i++) {
            String[] linea = lCos[i].split(";");
            datos[i][0] = linea[0];
            datos[i][1] = linea[1];
            datos[i][2] = linea[2];
            datos[i][3] = linea[3];
            datos[i][4] = linea[4];
            datos[i][5] = linea[5];
            datos[i][6] = linea[6];
            datos[i][7] = linea[7];

        }



        tablaCos.setModel(new DefaultTableModel(datos, nombreColumnas));
        tablaCos.setFillsViewportHeight(true);

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
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        ControladorProduccion control = ControladorProduccion.getInstance();
        control.readDataFromTextFile("InputDataGestionHuertos.txt");
        GUIlistarCosechadores dialog = new GUIlistarCosechadores();
        dialog.pack();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
        System.exit(0);
    }
}
