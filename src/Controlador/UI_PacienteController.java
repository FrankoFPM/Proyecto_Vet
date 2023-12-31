package Controlador;

import static Controlador.DashboardController.vista;
import Modelo.Paciente;
import Modelo.PersonaCliente;
import DAO.MetodosList;
import DataUtils.ProcesoValidacion;
import Vista.Dashboard_UI;
import Vista.Paciente_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import DAO.CargarCombos;
import DAO.DAOPaciente;

public class UI_PacienteController extends PanelController
        implements ActionListener, ListSelectionListener, FocusListener {

    boolean buscar = false;
    Paciente_UI PacienteUI;
    DAOPaciente daoPaciente;
    CargarCombos cargarCombos;

    String titutos[] = { "COD", "Nombre", "Especie", "Raza", "sexo",
            "Color", "Dueño" };

    String[] msgPaciente = { "Nombre", "Especie", "Raza",
            "Color" };
    JTextField[] txtPaciente;
    JTextField textFieldCombo;

    public UI_PacienteController(Paciente_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.PacienteUI = panel;
        txtPaciente = new JTextField[] { PacienteUI.txtNombre, PacienteUI.txtEspecie, PacienteUI.txtRaza,
                PacienteUI.txtColor };
        ProcesoValidacion.placeholderJtxt(txtPaciente, msgPaciente);
        MetodosList.tituloTabla(PacienteUI.tbPacientes, titutos);
        daoPaciente = new DAOPaciente();
        MetodosList.llenarTabla(PacienteUI.tbPacientes, daoPaciente.listarPacientes());

        // super.showWindow(panel);
        String cod = MetodosList.generarCodigo("paciente", "id_paciente", "PAC-", 4);
        PacienteUI.lblCodigo.setText(cod);

        PacienteUI.btnActualizar.setEnabled(false);
        PacienteUI.btnEliminar.setEnabled(false);
        llenarCboClientes();
        textFieldCombo = (JTextField) PacienteUI.cbCliente.getEditor().getEditorComponent();

        addListeners();
        keyListener();
    }

    private void keyListener() {
        textFieldCombo.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(() -> {
                    cargarCombos = new CargarCombos();
                    cargarCombos.filterComboBox(textFieldCombo.getText(), PacienteUI.cbCliente);
                    // MetodosList.filterComboBox(textFieldCombo.getText(),
                    // PacienteUI.cbCliente);
                });
            }
        });
    }

    private void llenarCboClientes() {
        PacienteUI.cbCliente.removeAllItems();
        // ArrayList<PersonaCliente> listaClientes = MetodosList.obtenerClientes();
        cargarCombos = new CargarCombos();
        ArrayList<PersonaCliente> listaClientes = cargarCombos.obtenerClientes();

        for (int i = 0; i < listaClientes.size(); i++) {
            PacienteUI.cbCliente.addItem(new PersonaCliente(listaClientes.get(i).getCodigo(),
                    listaClientes.get(i).getNombre()));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == PacienteUI.btnRegistrar) {
            if (ProcesoValidacion.validarInputs(txtPaciente, msgPaciente)) {
                Paciente mascota = new Paciente();
                mascota.setCodigo(PacienteUI.lblCodigo.getText());
                mascota.setNombre(txtPaciente[0].getText());
                mascota.setEspecie(txtPaciente[1].getText());
                mascota.setRaza(txtPaciente[2].getText());
                mascota.setSexo(PacienteUI.cboSexo.getSelectedItem().toString());
                mascota.setColor(txtPaciente[3].getText());
                mascota.setDueño(PacienteUI.cbCliente.getItemAt(PacienteUI.cbCliente.getSelectedIndex()).getCodigo());

                if (!ProcesoValidacion.validarCombobox(PacienteUI.cbCliente, "Dueño")
                        || !ProcesoValidacion.validarCombobox(PacienteUI.cboSexo, "Sexo")) {
                    return;
                }

                daoPaciente = new DAOPaciente();
                daoPaciente.insertarPaciente(mascota);

                reloadWindow();
            }
        } else if (e.getSource() == PacienteUI.btnBuscar) {
            if (PacienteUI.btnBuscar.getText().equals("Buscar")) {
                String dato = JOptionPane.showInputDialog(null, "Ingrese el DNI del cliente");
                if (dato != null) {
                    if (!dato.isEmpty()) {
                        daoPaciente = new DAOPaciente();
                        // List<String[]> datos = ProcesoRD.buscarRegistros("paciente", "id_cliente",
                        // dato);
                        List<String[]> datos = daoPaciente.buscarPaciente(dato);
                        if (!datos.isEmpty()) {
                            MetodosList.llenarTabla(PacienteUI.tbPacientes, datos);
                            // PacienteUI.btnEliminar.setEnabled(true);
                            PacienteUI.btnActualizar.setEnabled(true);
                            PacienteUI.btnBuscar.setText("Cancelar");
                            buscar = true;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No ingresó ningún dato");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operacion cancelada");
                }
            } else {
                PacienteUI.btnBuscar.setText("Buscar");
                PacienteUI.btnEliminar.setEnabled(false);
                PacienteUI.btnActualizar.setEnabled(false);
                reloadWindow();
                buscar = false;
            }
        } else if (e.getSource() == PacienteUI.btnActualizar) {
            if (ProcesoValidacion.validarInputs(txtPaciente, msgPaciente)) {
                Paciente mascota = new Paciente();
                mascota.setCodigo(PacienteUI.lblCodigo.getText());
                mascota.setNombre(txtPaciente[0].getText());
                mascota.setEspecie(txtPaciente[1].getText());
                mascota.setRaza(txtPaciente[2].getText());
                mascota.setSexo(PacienteUI.cboSexo.getSelectedItem().toString());
                mascota.setColor(txtPaciente[3].getText());
                mascota.setDueño(PacienteUI.cbCliente.getItemAt(PacienteUI.cbCliente.getSelectedIndex()).getCodigo());
                if (!ProcesoValidacion.validarCombobox(PacienteUI.cbCliente, "Dueño")
                        || !ProcesoValidacion.validarCombobox(PacienteUI.cboSexo, "Sexo")) {
                    return;
                }

                daoPaciente = new DAOPaciente();
                daoPaciente.actualizarPaciente(mascota);

                reloadWindow();
            }
        } else if (e.getSource() == PacienteUI.btnEliminar) {
            // ProcesoRD.eliminarRegistros("paciente", "id_paciente",
            // PacienteUI.lblCodigo.getText());
            daoPaciente = new DAOPaciente();
            daoPaciente.eliminarPaciente(PacienteUI.lblCodigo.getText());
            PacienteUI.btnBuscar.setText("Buscar");
            PacienteUI.btnEliminar.setEnabled(false);
            PacienteUI.btnActualizar.setEnabled(false);
            reloadWindow();
        }
    }

    @Override
    protected void addListeners() {
        PacienteUI.btnRegistrar.addActionListener(this);
        PacienteUI.btnBuscar.addActionListener(this);
        PacienteUI.btnActualizar.addActionListener(this);
        PacienteUI.btnEliminar.addActionListener(this);

        PacienteUI.tbPacientes.getSelectionModel().addListSelectionListener(this);

        textFieldCombo.addFocusListener(this);

    }

    /**
     * Este método se llama cuando el valor de la selección en la lista cambia.
     *
     * @param e El evento de cambio de selección.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        // Si el valor aún se está ajustando, salimos del método.
        if (e.getValueIsAdjusting()) {
            return;
        }

        // Obtenemos el modelo de selección de la lista del evento.
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        // Verificamos si hay alguna fila seleccionada y si la búsqueda está activa.
        if (!lsm.isSelectionEmpty() && buscar) {
            // Obtenemos el índice de la fila seleccionada.
            int filaSeleccionada = lsm.getMinSelectionIndex();

            // Creamos un array para almacenar los datos de la fila seleccionada.
            String[] datos = new String[PacienteUI.tbPacientes.getColumnCount()];
            for (int i = 0; i < PacienteUI.tbPacientes.getColumnCount(); i++) {
                datos[i] = (String) PacienteUI.tbPacientes.getValueAt(filaSeleccionada, i);
            }

            // Habilitamos el botón de eliminar.
            PacienteUI.btnEliminar.setEnabled(true);

            // Actualizamos los campos de la interfaz de usuario con los datos de la fila
            // seleccionada.
            PacienteUI.lblCodigo.setText(datos[0]);
            PacienteUI.txtNombre.setText(datos[1]);
            PacienteUI.txtEspecie.setText(datos[2]);
            PacienteUI.txtRaza.setText(datos[3]);
            PacienteUI.cboSexo.setSelectedItem(datos[4]);
            PacienteUI.txtColor.setText(datos[5]);

            // Buscamos el código del cliente en el combo box y lo seleccionamos.
            String codigoBD = datos[6];
            for (int i = 1; i < PacienteUI.cbCliente.getItemCount(); i++) {
                String codigoItem = PacienteUI.cbCliente.getItemAt(i).getCodigo();
                if (codigoItem.equals(codigoBD)) {
                    PacienteUI.cbCliente.setSelectedIndex(i);
                    break;
                }
            }

        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        String enteredText = textFieldCombo.getText();
        if (enteredText.equals("[Selecciona una opción...]")) {
            textFieldCombo.setText("");
            PacienteUI.cbCliente.showPopup();
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == textFieldCombo) {
            String enteredText = textFieldCombo.getText();
            boolean isPresent = false;
            cargarCombos = new CargarCombos();

            for (PersonaCliente cliente : cargarCombos.obtenerClientes()) {
                if (cliente.toString().equals(enteredText)) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                llenarCboClientes();
                PacienteUI.cbCliente.setSelectedIndex(0);
            }
        }
    }

    @Override
    protected void reloadWindow() {
        Paciente_UI newpaciente = new Paciente_UI();
        UI_PacienteController controllerPaciente = new UI_PacienteController(newpaciente, vista);
    }

}
