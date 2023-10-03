package Controlador;

import Vista.Cliente_UI;
import Vista.Dashboard_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_ClienteController extends PanelController implements ActionListener{
   
    Cliente_UI ClienteUI;

    public UI_ClienteController(Cliente_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.ClienteUI = panel;
        super.showWindow(panel);
    }
    
    @Override
    protected void addListeners(){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    
}
