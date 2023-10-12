package Controlador;

import static Controlador.DashboardController.vista;
import Modelo.Cita;
import Modelo.Paciente;
import Modelo.PersonaCliente;
import Modelo.PersonaEmpleado;
import Procesos.ProcesoInsert;
import Procesos.ProcesoListado;
import Procesos.ProcesoRD;
import Procesos.ProcesoUpdate;
import Vista.Cita_UI;
import Vista.Dashboard_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ListSelectionModel;

public class UI_CitaController extends PanelController implements ActionListener, ListSelectionListener, FocusListener, ItemListener {

    Cita_UI CitaUI;

    boolean buscar = false;

    public static String[] titulosCitas = {"COD", "Cod Cliente", "Cliente", "Paciente", "Veterinario", "Fecha", "Hora", "Estado"};
    JTextField textFieldComboCliente;
    JTextField textFieldComboPaciente;

    public UI_CitaController(Cita_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.CitaUI = panel;

        ProcesoListado.tituloTabla(CitaUI.tbcitas, titulosCitas);
        ProcesoListado.llenarTabla(CitaUI.tbcitas, ProcesoListado.listarDatos("cita"));

        String cod = ProcesoListado.generarCodigo("cita", "id_cita", "CTA-", 4);
        CitaUI.lblCodigo.setText(cod);

        CitaUI.btnModificar.setEnabled(false);
        CitaUI.btnEliminar.setEnabled(false);

        llenarCboClientes();
        llenarCboVeterinarios();
        llenarCboPacientes(CodigoCliente());
        textFieldComboCliente = (JTextField) CitaUI.cbCliente.getEditor().getEditorComponent();
        textFieldComboPaciente = (JTextField) CitaUI.cbPaciente.getEditor().getEditorComponent();

        addListeners();
        keyListener();
    }

    private void keyListener() {
        textFieldComboCliente.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(() -> {
                    ProcesoListado.filterComboBox(textFieldComboCliente.getText(), CitaUI.cbCliente);
                });
            }
        });
        textFieldComboPaciente.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(() -> {
                    ProcesoListado.filterComboBoxPacientes(textFieldComboPaciente.getText(), CitaUI.cbPaciente, CodigoCliente());
                });
            }
        });
    }

    private void llenarCboClientes() {
        CitaUI.cbCliente.removeAllItems();

        ArrayList<PersonaCliente> listaClientes = ProcesoListado.obtenerClientes();
        for (int i = 0; i < listaClientes.size(); i++) {
            CitaUI.cbCliente.addItem(new PersonaCliente(listaClientes.get(i).getCodigo(),
                    listaClientes.get(i).getNombre()));
        }
    }

    private void llenarCboPacientes(String codigo) {
        CitaUI.cbPaciente.removeAllItems();

        ArrayList<Paciente> listaPacientes = ProcesoListado.obtenerPacientes(codigo);
        for (int i = 0; i < listaPacientes.size(); i++) {
            CitaUI.cbPaciente.addItem(new Paciente(listaPacientes.get(i).getCodigo(),
                    listaPacientes.get(i).getNombre()));
        }
    }

    private void llenarCboVeterinarios() {
        CitaUI.cbPaciente.removeAllItems();

        ArrayList<PersonaEmpleado> listaVeterinarios = ProcesoListado.obtenerVeterinarios();
        for (int i = 0; i < listaVeterinarios.size(); i++) {
            CitaUI.cbVeterinario.addItem(new PersonaEmpleado(listaVeterinarios.get(i).getCodigo(),
                    listaVeterinarios.get(i).getNombre()));
        }
    }

    private String CodigoCliente() {
        Object itemSeleccionado = CitaUI.cbCliente.getSelectedItem();
        if (itemSeleccionado instanceof PersonaCliente) {
            PersonaCliente clienteSeleccionado = (PersonaCliente) itemSeleccionado;
            String codigoCliente = clienteSeleccionado.getCodigo();
            return codigoCliente;
        } else {
            return "000";
        }
    }

    @Override
    protected void addListeners() {
        CitaUI.btnRegistrar.addActionListener(this);
        CitaUI.btnBuscar.addActionListener(this);
        CitaUI.btnModificar.addActionListener(this);
        CitaUI.btnEliminar.addActionListener(this);

        CitaUI.cbCliente.addItemListener(this);

        textFieldComboCliente.addFocusListener(this);
        textFieldComboPaciente.addFocusListener(this);

        CitaUI.tbcitas.getSelectionModel().addListSelectionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == CitaUI.btnRegistrar) {
            if (CitaUI.timePicker.getTime() != null) {
                Cita reserva = new Cita();
                reserva.setCodigo(CitaUI.lblCodigo.getText());
                reserva.setCodigoCliente(CitaUI.cbCliente.getItemAt(CitaUI.cbCliente.getSelectedIndex()).getCodigo());
                reserva.setCliente(CitaUI.cbCliente.getItemAt(CitaUI.cbCliente.getSelectedIndex()).getNombre());
                reserva.setPaciente(CitaUI.cbPaciente.getItemAt(CitaUI.cbPaciente.getSelectedIndex()).getNombre());
                reserva.setVeterinario(CitaUI.cbVeterinario.getItemAt(CitaUI.cbVeterinario.getSelectedIndex()).getNombre());
                LocalDate localDate = CitaUI.datePicker.getDate();
                Date date = Date.valueOf(localDate);
                reserva.setFecha(date);
                LocalTime localTime = CitaUI.timePicker.getTime();
                Time time = Time.valueOf(localTime);
                reserva.setHora(time);
                reserva.setEstado(CitaUI.cbEstado.getSelectedItem().toString());

                ProcesoInsert.insertarCita(reserva);

                reloadWindow();
            }
        } else if (e.getSource() == CitaUI.btnBuscar) {
            if (CitaUI.btnBuscar.getText().equals("Buscar")) {
                String dato = JOptionPane.showInputDialog(null, "Ingrese el DNI del cliente");
                if (dato != null) {
                    if (!dato.isEmpty()) {
                        List<String[]> datos = ProcesoRD.buscarCitas(dato);
                        if (!datos.isEmpty()) {
                            ProcesoListado.llenarTabla(CitaUI.tbcitas, datos);
                            CitaUI.btnBuscar.setText("Cancelar");
                            buscar = true;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No ingresó ningún dato");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operacion cancelada");
                }
            } else {
                CitaUI.btnBuscar.setText("Buscar");
                CitaUI.btnEliminar.setEnabled(false);
                CitaUI.btnModificar.setEnabled(false);
                reloadWindow();
                buscar = false;
            }
        } else if (e.getSource() == CitaUI.btnModificar) {
            if (CitaUI.timePicker.getTime() != null) {
                Cita reserva = new Cita();
                reserva.setCodigo(CitaUI.lblCodigo.getText());
                reserva.setCodigoCliente(CitaUI.cbCliente.getItemAt(CitaUI.cbCliente.getSelectedIndex()).getCodigo());
                reserva.setCliente(CitaUI.cbCliente.getItemAt(CitaUI.cbCliente.getSelectedIndex()).getNombre());
                reserva.setPaciente(CitaUI.cbPaciente.getItemAt(CitaUI.cbPaciente.getSelectedIndex()).getNombre());
                reserva.setVeterinario(CitaUI.cbVeterinario.getItemAt(CitaUI.cbVeterinario.getSelectedIndex()).getNombre());
                LocalDate localDate = CitaUI.datePicker.getDate();
                Date date = Date.valueOf(localDate);
                reserva.setFecha(date);
                LocalTime localTime = CitaUI.timePicker.getTime();
                Time time = Time.valueOf(localTime);
                reserva.setHora(time);
                reserva.setEstado(CitaUI.cbEstado.getSelectedItem().toString());

                ProcesoUpdate.actualizarCita(reserva);

                reloadWindow();
            }
        } else if (e.getSource() == CitaUI.btnEliminar) {
            ProcesoRD.eliminarRegistros("cita", "id_cita", CitaUI.lblCodigo.getText());
            CitaUI.btnBuscar.setText("Buscar");
            CitaUI.btnEliminar.setEnabled(false);
            CitaUI.btnModificar.setEnabled(false);
            reloadWindow();
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        // Obtiene el modelo de selección de la lista del evento
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        //if (e.getSource()==CitaUI.tbPacientes.getSelectionModel()) 
        // Verifica si hay alguna fila seleccionada
        if (!lsm.isSelectionEmpty() && buscar) {
            // Obtiene el índice de la fila seleccionada
            int filaSeleccionada = lsm.getMinSelectionIndex();

            String[] datos = new String[CitaUI.tbcitas.getColumnCount()];
            for (int i = 0; i < CitaUI.tbcitas.getColumnCount(); i++) {
                datos[i] = (String) CitaUI.tbcitas.getValueAt(filaSeleccionada, i);
            }

            CitaUI.btnEliminar.setEnabled(true);
            CitaUI.btnModificar.setEnabled(true);
            CitaUI.lblCodigo.setText(datos[0]);
            String cliente = datos[1];
            String paciente = datos[3];
            String veterinario = datos[4];
            System.out.println(cliente);
            System.out.println(paciente);
            System.out.println(veterinario);

            for (int i = 1; i < CitaUI.cbCliente.getItemCount(); i++) {
                String codigoItem = CitaUI.cbCliente.getItemAt(i).getCodigo();
                if (codigoItem.equals(cliente)) {
                    CitaUI.cbCliente.setSelectedIndex(i);
                    break;
                }
            }
            //llenarCboPacientes(CodigoCliente());
            for (int i = 1; i < CitaUI.cbPaciente.getItemCount(); i++) {
                String codigoItem = CitaUI.cbPaciente.getItemAt(i).getNombre();
                if (codigoItem.equals(paciente)) {
                    CitaUI.cbPaciente.setSelectedIndex(i);
                    break;
                }
            }
            for (int i = 1; i < CitaUI.cbVeterinario.getItemCount(); i++) {
                String codigoItem = CitaUI.cbVeterinario.getItemAt(i).getNombre();
                if (codigoItem.equals(veterinario)) {
                    CitaUI.cbVeterinario.setSelectedIndex(i);
                    break;
                }
            }
            String strFecha = datos[5];
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Ajusta este formato a cómo se ve tu fecha en la cadena de texto
            LocalDate fecha = LocalDate.parse(strFecha, formato);
            CitaUI.datePicker.setDate(fecha);

            String strHora = datos[6];
            LocalTime hora = LocalTime.parse(strHora);
            CitaUI.timePicker.setTime(hora);

            CitaUI.cbEstado.setSelectedItem(datos[7]);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == textFieldComboCliente) {
            String enteredText = textFieldComboCliente.getText();
            if (enteredText.equals("[Selecciona una opción...]")) {
                textFieldComboCliente.setText("");
                CitaUI.cbCliente.showPopup();
            }
        } else if (e.getSource() == textFieldComboPaciente) {
            String enteredText = textFieldComboPaciente.getText();
            if (enteredText.equals("[Selecciona una opción...]")) {
                textFieldComboPaciente.setText("");
                CitaUI.cbPaciente.showPopup();
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == textFieldComboCliente) {
            String enteredText = textFieldComboCliente.getText();
            boolean isPresent = false;
            for (PersonaCliente cliente : ProcesoListado.obtenerClientes()) {
                if (cliente.toString().equals(enteredText)) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                llenarCboClientes();
                CitaUI.cbCliente.setSelectedIndex(0);
            }
        } else if (e.getSource() == textFieldComboPaciente) {
            String enteredText = textFieldComboPaciente.getText();
            boolean isPresent = false;
            for (Paciente paciente : ProcesoListado.obtenerPacientes(CodigoCliente())) {
                if (paciente.toString().equals(enteredText)) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                llenarCboPacientes(CodigoCliente());
                CitaUI.cbPaciente.setSelectedIndex(0);
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getSource() == CitaUI.cbCliente) {
                llenarCboPacientes(CodigoCliente());
            }
        }
    }

    @Override
    protected void reloadWindow() {
        Cita_UI newcita = new Cita_UI();
        UI_CitaController controllerCita = new UI_CitaController(newcita, vista);
    }

}
