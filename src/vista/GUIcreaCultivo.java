package vista;

import controlador.ControladorProduccion;

import javax.swing.*;
import java.awt.event.*;

public class GUIcreaCultivo extends JDialog {
    ControladorProduccion control = ControladorProduccion.getInstance();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField CajonRe;
    private JTextField CajonVa;
    private JTextField CajonEsp;
    private JTextField CajonID;

    public GUIcreaCultivo() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setVisible(false);

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
        int id;
        double rendimiento;

        try {
            id = Integer.parseInt(CajonID.getText().trim());
            rendimiento = Double.parseDouble(CajonRe.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "ID y Rendimiento deben ser valores numéricos válidos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        String especie = CajonEsp.getText().trim();
        String variedad = CajonVa.getText().trim();

        if (id <= 0 || especie.isEmpty() || variedad.isEmpty() || rendimiento <= 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe completar todos los campos correctamente",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        boolean ok = control.createCultivo(id, especie, variedad, rendimiento);

        if (ok) {
            JOptionPane.showMessageDialog(
                    this,
                    "Cultivo creado exitosamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );
            dispose();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "El cultivo ya existe o no pudo crearse",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        GUIcreaCultivo dialog = new GUIcreaCultivo();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
