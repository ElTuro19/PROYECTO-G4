package vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class GUIpagoDePesajesPendientes extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JTable tabla;

    public GUIpagoDePesajesPendientes() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        String[] nombreColumnas = {"ID", "Fecha", "Calidad", "Kilos", "Precio Kg", "Monto", "Pagado"};
        String[][] datos = {
                {"222", "2000 A.c", "de la polla", "-3", "Precio K", "Monto", "Pagado"},
                {"223", "2001 A.c", "otra cosa", "10", "1200", "12000", "No"}
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
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    public static void main(String[] args) {
        GUIpagoDePesajesPendientes dialog = new GUIpagoDePesajesPendientes();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
