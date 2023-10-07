package Controlador;

import Procesos.ProcesoListado;
import Procesos.ProcesoValidacion;
import Vista.Dashboard_UI;
import Vista.Paciente_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class UI_PacienteController extends PanelController implements ActionListener{

    Paciente_UI PacienteUI;
    
    String titutos[] = {"COD", "Nombre", "Especie", "Raza",
        "Color", "Dueño"};

    String msgCliente[] = {"Nombre", "Especie", "Raza",
        "Color"};
    JTextField txtCliente[];

    
    public UI_PacienteController(Paciente_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.PacienteUI = panel;
        txtCliente = new JTextField[]{PacienteUI.txtNombre,PacienteUI.txtEspecie,PacienteUI.txtRaza,PacienteUI.txtColor};
        ProcesoValidacion.placeholderJtxt(txtCliente, msgCliente);
        ProcesoListado.tituloTabla(PacienteUI.tbPacientes, titutos);
        ProcesoListado.llenarTabla(PacienteUI.tbPacientes, ProcesoListado.listarDatos("paciente"));
        
        super.showWindow(panel);
        String cod = ProcesoListado.generarCodigo("paciente", "id_paciente", "PAC-", 4);
        PacienteUI.lblCodigo.setText(cod);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
    
}
