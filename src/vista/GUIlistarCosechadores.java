package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class GUIlistarCosechadores extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTable tablaCos;

    public GUIlistarCosechadores() {
        setContentPane(contentPane);
        setModal(true);

        String[] nombreColumnas = {"Rut", "Nombre", "Direccion", "email", "Fecha Nac.", "Nro. Cuadrillas", "Monto Impago$, Monto Pagado$"};
        String[][] datos = {
                {"33.333.333-3", "NEGRO ESCLAVO 1", "CAMPO DE ALGODON", "no tiene", "hace 2 dias", "2 MILLONES", "0", "Trabaja gratis"},
                {"22.222.222-2", "JUDIO PRISIONERO", "INCENIRADOR", "jewmaster@gmail.com", "0/0/0", "12000", "0", "morira mañana"}
        };

        tablaCos.setModel(new DefaultTableModel(datos, nombreColumnas));
        ///tabla.enable();

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
        GUIlistarCosechadores dialog = new GUIlistarCosechadores();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}