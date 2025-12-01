package vista;

import javax.swing.*;
import controlador.ControladorProduccion;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GUIcreaPersona extends JDialog {
    ControladorProduccion control = ControladorProduccion.getInstance();
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField cajonRut;
    private JTextField cajonNombre;
    private JTextField cajonEmail;
    private JTextField cajonDireccion;
    private JRadioButton propietarioRadioButton;
    private JRadioButton supervisorRadioButton;
    private JRadioButton cosechadorRadioButton;
    private JTextField cajonDComercial;
    private JLabel cajonDF;
    ButtonGroup grupo = new ButtonGroup();

    public GUIcreaPersona() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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
        grupo.add(propietarioRadioButton);
        grupo.add(supervisorRadioButton);
        grupo.add(cosechadorRadioButton);
        propietarioRadioButton.addActionListener(e -> cajonDF.setText("Dirección Comercial:"));
        propietarioRadioButton.addActionListener(e -> cajonDComercial.setEnabled(true));
        supervisorRadioButton.addActionListener(e -> cajonDF.setText("Profesión:"));
        supervisorRadioButton.addActionListener(e -> cajonDComercial.setEnabled(true));
        cosechadorRadioButton.addActionListener(e -> cajonDF.setText("Fecha Nacimiento:"));
        cosechadorRadioButton.addActionListener(e -> cajonDComercial.setEnabled(true));


    }

    private void onOK() {
        String rut = cajonRut.getText();
        String nombre = cajonNombre.getText();
        String email = cajonEmail.getText();
        String direccion = cajonDireccion.getText();

        if(propietarioRadioButton.isSelected()){
            String dirComercial = cajonDComercial.getText();
            if (rut.isEmpty() || nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || dirComercial.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe completar todos los campos del Propietario",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean okPropietario = control.createPropietario(rut, nombre, email, direccion, dirComercial);

            if (okPropietario) {
                JOptionPane.showMessageDialog(this,
                        "Propietario creado exitosamente",
                        "Proceso finalizado",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al crear Propietario (puede que el RUT ya exista)",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }else if(supervisorRadioButton.isSelected()){

        }else if(cosechadorRadioButton.isSelected()){

        }else{
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún rol", "ERROR EN LA SELECCIÓN", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // add your code here
        dispose();
    }

    private void onCancel() {
        this.setVisible(false);
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        GUIcreaPersona dialog = new GUIcreaPersona();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);

    }



}
