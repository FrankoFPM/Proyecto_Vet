package Controlador;

import static Controlador.DashboardController.vista;
import Modelo.PersonaEmpleado;
import Procesos.ProcesoInsert;
import Procesos.ProcesoListado;
import Procesos.ProcesoRD;
import Procesos.ProcesoUpdate;
import Procesos.ProcesoValidacion;
import Vista.Dashboard_UI;
import Vista.Personal_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UI_PersonalController extends PanelController implements ActionListener, FocusListener {

    Personal_UI PersonalUI;

    String titutos[] = {"COD", "Nombre", "Apellidos", "Correo", "DNI",
        "Cargo", "Usuario"};

    String[] msgPersonal = {"Nombre", "Apellidos", "Contraseña", "Correo", "DNI"};
    JTextField[] txtPersonal;

    public UI_PersonalController(Personal_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.PersonalUI = panel;
        txtPersonal = new JTextField[]{PersonalUI.txtNombres, PersonalUI.txtApellidos, PersonalUI.txtPassword, PersonalUI.txtCorreo, PersonalUI.txtDni};

        ProcesoValidacion.placeholderJtxt(txtPersonal, msgPersonal);
        ProcesoListado.tituloTabla(PersonalUI.tbPersonal, titutos);
        ProcesoListado.llenarTabla(PersonalUI.tbPersonal, ProcesoListado.listarDatos("v_personal"));

        String cod = ProcesoListado.generarCodigoUnico("personal", "id_registro", "EMP-", 4);
        PersonalUI.lblCodigo.setText(cod);
        addListeners();

        PersonalUI.btnModificar.setEnabled(false);
        PersonalUI.btnEliminar.setEnabled(false);
    }

    private void generarNick() {
        if (!txtPersonal[0].getText().isEmpty() && !txtPersonal[4].getText().isEmpty()) {
            String nombre = txtPersonal[0].getText();
            String dni = txtPersonal[4].getText();
            char firstLetter = nombre.charAt(0);
            String nick = firstLetter + dni;
            PersonalUI.txtNick.setText(nick.toUpperCase());
        }
    }

    @Override
    protected void addListeners() {
        txtPersonal[0].addFocusListener(this);
        txtPersonal[4].addFocusListener(this);

        PersonalUI.btnBuscar.addActionListener(this);
        PersonalUI.btnEliminar.addActionListener(this);
        PersonalUI.btnModificar.addActionListener(this);
        PersonalUI.btnRegistrar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == PersonalUI.btnRegistrar) {
            if (ProcesoValidacion.validarInputs(txtPersonal, msgPersonal)) {
                PersonaEmpleado empleado = new PersonaEmpleado();
                empleado.setCodigo(PersonalUI.lblCodigo.getText());
                empleado.setNombre(txtPersonal[0].getText());
                empleado.setApellido(txtPersonal[1].getText());
                empleado.setContraseña(txtPersonal[2].getText());
                empleado.setCorreo(txtPersonal[3].getText());
                empleado.setDni(Integer.parseInt(txtPersonal[4].getText()));
                empleado.setCargo(PersonalUI.cboCargo.getSelectedItem().toString());
                empleado.setNickname(PersonalUI.txtNick.getText());

                ProcesoInsert.insertarPersonal(empleado);

                Personal_UI newPersonal = new Personal_UI();
                UI_PersonalController controllerPersonal = new UI_PersonalController(newPersonal, vista);
            }
        } else if (e.getSource() == PersonalUI.btnBuscar) {
            if (PersonalUI.btnBuscar.getText().equals("Buscar")) {
                String dato = JOptionPane.showInputDialog(null, "Ingrese el DNI del empleado");
                if (dato != null) {
                    if (!dato.isEmpty()) {
                        List<String[]> datos = ProcesoRD.buscarRegistros("personal", "dni", dato);
                        if (!datos.isEmpty()) {
                            String[] primerRegistro = datos.get(0);
                            PersonalUI.lblCodigo.setText(primerRegistro[1]);
                            for (int i = 0; i < txtPersonal.length; i++) {
                                txtPersonal[i].setText(primerRegistro[i + 2]);
                            }
                            PersonalUI.cboCargo.setSelectedItem(primerRegistro[7]);
                            PersonalUI.txtNick.setText(primerRegistro[8]);

                            PersonalUI.btnEliminar.setEnabled(true);
                            PersonalUI.btnModificar.setEnabled(true);
                            PersonalUI.btnBuscar.setText("Cancelar");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No ingresó ningún dato");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operacion cancelada");
                }
            } else {
                PersonalUI.btnBuscar.setText("Buscar");
                PersonalUI.btnEliminar.setEnabled(false);
                PersonalUI.btnModificar.setEnabled(false);
                Personal_UI newpersonal = new Personal_UI();
                UI_PersonalController controllerPersonal = new UI_PersonalController(newpersonal, vista);
            }
        } else if (e.getSource() == PersonalUI.btnModificar) {
            if (ProcesoValidacion.validarInputs(txtPersonal, msgPersonal)) {
                PersonaEmpleado empleado = new PersonaEmpleado();
                empleado.setCodigo(PersonalUI.lblCodigo.getText());
                empleado.setNombre(txtPersonal[0].getText());
                empleado.setApellido(txtPersonal[1].getText());
                empleado.setContraseña(txtPersonal[2].getText());
                empleado.setCorreo(txtPersonal[3].getText());
                empleado.setDni(Integer.parseInt(txtPersonal[4].getText()));
                empleado.setCargo(PersonalUI.cboCargo.getSelectedItem().toString());
                empleado.setNickname(PersonalUI.txtNick.getText());
                ProcesoUpdate.actualizarPersonal(empleado);
                Personal_UI newpersonal = new Personal_UI();
                UI_PersonalController controllerPersonal = new UI_PersonalController(newpersonal, vista);
            }
        } else if (e.getSource() == PersonalUI.btnEliminar) {
            ProcesoRD.eliminarRegistros("personal", "id_personal", PersonalUI.lblCodigo.getText());
            PersonalUI.btnBuscar.setText("Buscar");
            PersonalUI.btnEliminar.setEnabled(false);
            PersonalUI.btnModificar.setEnabled(false);
            Personal_UI newpersonal = new Personal_UI();
            UI_PersonalController controllerPersonal = new UI_PersonalController(newpersonal, vista);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {

    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == txtPersonal[0]) {
            generarNick();
        } else if (e.getSource() == txtPersonal[4]) {
            generarNick();
        }
    }

}
