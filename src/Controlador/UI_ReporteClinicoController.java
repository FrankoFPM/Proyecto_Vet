package Controlador;

import static Controlador.DashboardController.vista;
import Modelo.Paciente;
import Modelo.ReporteClinico;
import Procesos.ProcesoInsert;
import Procesos.ProcesoListado;
import Procesos.ProcesoRD;
import Procesos.ProcesoUpdate;
import Vista.Dashboard_UI;
import Vista.RPClinico_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class UI_ReporteClinicoController extends PanelController implements ActionListener, ListSelectionListener, FocusListener {

    RPClinico_UI reporteUI;

    boolean buscar = false;
    JTextField textFieldComboPaciente;
    public String[] titulosRP = {"COD", "Cod Paciente", "Paciente", "Causa", "Pronostico", "Sintomas", "Tratamiento", "Fecha", "Hora"};

    public UI_ReporteClinicoController(RPClinico_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.reporteUI = panel;

        ProcesoListado.tituloTabla(reporteUI.tbRepotes, titulosRP);
        ProcesoListado.llenarTabla(reporteUI.tbRepotes, ProcesoListado.listarDatos("ReporteClinico"));

        llenarCboPacientes();
        textFieldComboPaciente = (JTextField) reporteUI.cbPaciente.getEditor().getEditorComponent();

        String cod = ProcesoListado.generarCodigo("ReporteClinico", "id_rpclinico", "RPC-", 4);
        reporteUI.lblCodigo.setText(cod);

        reporteUI.btnModificar.setEnabled(false);
        reporteUI.btnEliminar.setEnabled(false);

        keyListener();
        addListeners();
    }

    private void keyListener() {
        textFieldComboPaciente.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(() -> {
                    ProcesoListado.filterComboBoxAllPacientes(textFieldComboPaciente.getText(), reporteUI.cbPaciente);
                });
            }
        });
    }

    private void llenarCboPacientes() {
        reporteUI.cbPaciente.removeAllItems();

        ArrayList<Paciente> listaPacientes = ProcesoListado.obtenerTodoPacientes();
        for (int i = 0; i < listaPacientes.size(); i++) {
            reporteUI.cbPaciente.addItem(new Paciente(listaPacientes.get(i).getCodigo(),
                    listaPacientes.get(i).getNombre()));
        }
    }

    @Override
    protected void addListeners() {
        reporteUI.btnRegistrar.addActionListener(this);
        reporteUI.btnBuscar.addActionListener(this);
        reporteUI.btnModificar.addActionListener(this);
        reporteUI.btnEliminar.addActionListener(this);

        textFieldComboPaciente.addFocusListener(this);

        reporteUI.tbRepotes.getSelectionModel().addListSelectionListener(this);
    }

    @Override
    protected void reloadWindow() {
        RPClinico_UI newReporte = new RPClinico_UI();
        UI_ReporteClinicoController ControllerRPClinico = new UI_ReporteClinicoController(newReporte, vista);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == reporteUI.btnRegistrar) {
            if (reporteUI.timePicker.getTime() != null) {
                ReporteClinico rpClinico = new ReporteClinico();
                rpClinico.setCodigo(reporteUI.lblCodigo.getText());
                rpClinico.setCodigo_entidad(reporteUI.cbPaciente.getItemAt(reporteUI.cbPaciente.getSelectedIndex()).getCodigo());
                rpClinico.setEntidad(reporteUI.cbPaciente.getItemAt(reporteUI.cbPaciente.getSelectedIndex()).getNombre());
                rpClinico.setCausa(reporteUI.txtCausa.getText());
                rpClinico.setPronostico(reporteUI.cbPronostico.getSelectedItem().toString());
                rpClinico.setSintomas(reporteUI.txtSintomas.getText());
                rpClinico.setTratamiento(reporteUI.txtTratamiento.getText());
                LocalDate localDate = reporteUI.datePicker.getDate();
                Date date = Date.valueOf(localDate);
                rpClinico.setFecha(date);
                LocalTime localTime = reporteUI.timePicker.getTime();
                Time time = Time.valueOf(localTime);
                rpClinico.setHora(time);

                ProcesoInsert.insertarReporteClinico(rpClinico);

                reloadWindow();
            }
        } else if (e.getSource() == reporteUI.btnBuscar) {
            if (reporteUI.btnBuscar.getText().equals("Buscar")) {
                String dato = JOptionPane.showInputDialog(null, "Ingrese el Codigo del reporte");
                if (dato != null) {
                    if (!dato.isEmpty()) {
                        List<String[]> datos = ProcesoRD.buscarRegistros("ReporteClinico", "id_rpclinico", dato);
                        if (!datos.isEmpty()) {
                            ProcesoListado.llenarTabla(reporteUI.tbRepotes, datos);
                            reporteUI.btnBuscar.setText("Cancelar");
                            buscar = true;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No ingresó ningún dato");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Operacion cancelada");
                }
            } else {
                reporteUI.btnBuscar.setText("Buscar");
                reporteUI.btnEliminar.setEnabled(false);
                reporteUI.btnModificar.setEnabled(false);
                reloadWindow();
                buscar = false;
            }
        } else if (e.getSource() == reporteUI.btnModificar) {
            if (reporteUI.timePicker.getTime() != null) {
                ReporteClinico rpClinico = new ReporteClinico();
                rpClinico.setCodigo(reporteUI.lblCodigo.getText());
                rpClinico.setCodigo_entidad(reporteUI.cbPaciente.getItemAt(reporteUI.cbPaciente.getSelectedIndex()).getCodigo());
                rpClinico.setEntidad(reporteUI.cbPaciente.getItemAt(reporteUI.cbPaciente.getSelectedIndex()).getNombre());
                rpClinico.setCausa(reporteUI.txtCausa.getText());
                rpClinico.setPronostico(reporteUI.cbPronostico.getSelectedItem().toString());
                rpClinico.setSintomas(reporteUI.txtSintomas.getText());
                rpClinico.setTratamiento(reporteUI.txtTratamiento.getText());
                LocalDate localDate = reporteUI.datePicker.getDate();
                Date date = Date.valueOf(localDate);
                rpClinico.setFecha(date);
                LocalTime localTime = reporteUI.timePicker.getTime();
                Time time = Time.valueOf(localTime);
                rpClinico.setHora(time);

                ProcesoUpdate.actualizarReporteClinico(rpClinico);

                reloadWindow();
            }
        } else if (e.getSource() == reporteUI.btnEliminar) {
            ProcesoRD.eliminarRegistros("ReporteClinico", "id_rpclinico", reporteUI.lblCodigo.getText());
            reporteUI.btnBuscar.setText("Buscar");
            reporteUI.btnEliminar.setEnabled(false);
            reporteUI.btnModificar.setEnabled(false);
            reloadWindow();
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == textFieldComboPaciente) {
            String enteredText = textFieldComboPaciente.getText();
            if (enteredText.equals("[Selecciona una opción...]")) {
                textFieldComboPaciente.setText("");
                reporteUI.cbPaciente.showPopup();
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == textFieldComboPaciente) {
            String enteredText = textFieldComboPaciente.getText();
            boolean isPresent = false;
            for (Paciente paciente : ProcesoListado.obtenerTodoPacientes()) {
                if (paciente.toString().equals(enteredText)) {
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                llenarCboPacientes();
                reporteUI.cbPaciente.setSelectedIndex(0);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            return;
        }

        // Obtiene el modelo de selección de la lista del evento
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();

        //if (e.getSource()==reporteUI.tbPacientes.getSelectionModel()) 
        // Verifica si hay alguna fila seleccionada
        if (!lsm.isSelectionEmpty() && buscar) {
            // Obtiene el índice de la fila seleccionada
            int filaSeleccionada = lsm.getMinSelectionIndex();

            String[] datos = new String[reporteUI.tbRepotes.getColumnCount()];
            for (int i = 0; i < reporteUI.tbRepotes.getColumnCount(); i++) {
                datos[i] = (String) reporteUI.tbRepotes.getValueAt(filaSeleccionada, i);
            }

            reporteUI.btnEliminar.setEnabled(true);
            reporteUI.btnModificar.setEnabled(true);
            reporteUI.lblCodigo.setText(datos[0]);
            String paciente = datos[1];
            System.out.println(paciente);

            for (int i = 1; i < reporteUI.cbPaciente.getItemCount(); i++) {
                String codigoItem = reporteUI.cbPaciente.getItemAt(i).getCodigo();
                if (codigoItem.equals(paciente)) {
                    reporteUI.cbPaciente.setSelectedIndex(i);
                    break;
                }
            }
            reporteUI.txtCausa.setText(datos[3]);
            reporteUI.cbPronostico.setSelectedItem(datos[4]);
            reporteUI.txtSintomas.setText(datos[5]);
            reporteUI.txtTratamiento.setText(datos[6]);

            String strFecha = datos[7];
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Ajusta este formato a cómo se ve tu fecha en la cadena de texto
            LocalDate fecha = LocalDate.parse(strFecha, formato);
            reporteUI.datePicker.setDate(fecha);

            String strHora = datos[8];
            LocalTime hora = LocalTime.parse(strHora);
            reporteUI.timePicker.setTime(hora);

        }
    }

}
