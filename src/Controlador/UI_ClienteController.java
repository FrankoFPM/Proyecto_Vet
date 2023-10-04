package Controlador;

import Modelo.PersonaCliente;
import Vista.Cliente_UI;
import Vista.Dashboard_UI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI_ClienteController extends PanelController implements ActionListener{
   
    Cliente_UI ClienteUI;
    PersonaCliente cliente;
    
    public UI_ClienteController(Cliente_UI panel, Dashboard_UI app) {
        super(panel, app);
        this.ClienteUI = panel;
        super.showWindow(panel);
        cliente = new PersonaCliente("1", "2", "3", "pepe", "ganzales", 0);
        addListeners();
    }
    
    @Override
    protected void addListeners(){
        ClienteUI.btnEliminar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ClienteUI.btnEliminar) {
            System.out.println("eliminar");
            
            String cod =cliente.getCodigo();
            ClienteUI.lblNombre.setText(cod);
        }
    }
    
}
