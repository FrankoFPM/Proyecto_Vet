package Controlador;

import static Controlador.DashboardController.vista;
import Modelo.PersonaEmpleado;
import DAO.MetodosList;
import DataUtils.ProcesoValidacion;
import Vista.Dashboard_UI;
import Vista.Personal_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import DAO.DAOPersonal;

public class UI_PersonalController extends PanelController implements ActionListener, FocusListener {

    Personal_UI PersonalUI;
    DAOPersonal daoPersonal;

    String titutos[] = { "COD", "Nombre", "Apellidos", "Correo", "DNI",
            "Cargo", "Usuario" };

    String[] msgPersonal = { "Nombre", "Apellidos", "Contraseña", "Correo", "DNI" };
    JTextField[] txtPersonal;

    public UI_PersonalController(Personal_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.PersonalUI = panel;
        txtPersonal = new JTextField[] { PersonalUI.txtNombres, PersonalUI.txtApellidos, PersonalUI.txtPassword,
                PersonalUI.txtCorreo, PersonalUI.txtDni };

        ProcesoValidacion.placeholderJtxt(txtPersonal, msgPersonal);
        MetodosList.tituloTabla(PersonalUI.tbPersonal, titutos);
        daoPersonal = new DAOPersonal();
        // MetodosList.llenarTabla(PersonalUI.tbPersonal,
        // MetodosList.listarDatos("v_personal"));
        MetodosList.llenarTabla(PersonalUI.tbPersonal, daoPersonal.listarPersonal());

        String cod = MetodosList.generarCodigoUnico("personal", "id_registro", "EMP-", 4);
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
                String dniText = txtPersonal[4].getText();
                if (!dniText.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "El DNI solo debe contener números");
                    return;
                }
                empleado.setDni(Integer.parseInt(dniText));
                empleado.setCargo(PersonalUI.cboCargo.getSelectedItem().toString());
                empleado.setNickname(PersonalUI.txtNick.getText());

                if (!ProcesoValidacion.validarEmail(empleado.getCorreo()) ||
                        !ProcesoValidacion.validarDni(empleado.getDni())) {
                    return;
                }

                // ProcesoInsert.insertarPersonal(empleado);
                daoPersonal = new DAOPersonal();
                daoPersonal.insertarPersonal(empleado);

                reloadWindow();
            }
        } else if (e.getSource() == PersonalUI.btnBuscar) {
            if (PersonalUI.btnBuscar.getText().equals("Buscar")) {
                String dato = JOptionPane.showInputDialog(null, "Ingrese el DNI del empleado");
                if (dato != null) {
                    if (!dato.isEmpty()) {
                        // List<String[]> datos = ProcesoRD.buscarRegistros("personal", "dni", dato);
                        List<String[]> datos = daoPersonal.buscarPersonal(dato);
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
                reloadWindow();
            }
        } else if (e.getSource() == PersonalUI.btnModificar) {
            if (ProcesoValidacion.validarInputs(txtPersonal, msgPersonal)) {
                PersonaEmpleado empleado = new PersonaEmpleado();
                empleado.setCodigo(PersonalUI.lblCodigo.getText());
                empleado.setNombre(txtPersonal[0].getText());
                empleado.setApellido(txtPersonal[1].getText());
                empleado.setContraseña(txtPersonal[2].getText());
                empleado.setCorreo(txtPersonal[3].getText());
                String dniText = txtPersonal[4].getText();
                if (!dniText.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null, "El DNI solo debe contener números");
                    return;
                }
                empleado.setDni(Integer.parseInt(dniText));
                empleado.setCargo(PersonalUI.cboCargo.getSelectedItem().toString());
                empleado.setNickname(PersonalUI.txtNick.getText());

                if (!ProcesoValidacion.validarEmail(empleado.getCorreo()) ||
                        !ProcesoValidacion.validarDni(empleado.getDni())) {
                    return;
                }
                daoPersonal = new DAOPersonal();
                // ProcesoUpdate.actualizarPersonal(empleado);
                daoPersonal.actualizarPersonal(empleado);
                reloadWindow();
            }
        } else if (e.getSource() == PersonalUI.btnEliminar) {
            // ProcesoRD.eliminarRegistros("personal", "id_personal",
            // PersonalUI.lblCodigo.getText());
            daoPersonal = new DAOPersonal();
            daoPersonal.eliminarPersonal(PersonalUI.lblCodigo.getText());
            PersonalUI.btnBuscar.setText("Buscar");
            PersonalUI.btnEliminar.setEnabled(false);
            PersonalUI.btnModificar.setEnabled(false);
            reloadWindow();
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

    @Override
    protected void reloadWindow() {
        Personal_UI newpersonal = new Personal_UI();
        UI_PersonalController controllerPersonal = new UI_PersonalController(newpersonal, vista);
    }

}
