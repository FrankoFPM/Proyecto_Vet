package Controlador;

import Vista.Dashboard_UI;
import Vista.Paciente_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_PacienteController extends PanelController implements ActionListener{

    Paciente_UI PacienteUI;
    
    public UI_PacienteController(Paciente_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.PacienteUI = panel;
        super.showWindow(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
    
}
