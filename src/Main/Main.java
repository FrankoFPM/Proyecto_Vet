package Main;

import Controlador.LoginController;
import Vista.Login;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

public class Main {
    
    public static Login login;
    //public static Dashboard_UI dashboard;

    public static void main(String[] args) {
        try {
            //Styles
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("Button.arc", 15);
            UIManager.put("Button.borderColor", "#B5E0FF");
            UIManager.put("TextComponent.arc", 15);
            UIManager.put("Component.arc", 15);
            UIManager.put("Separator.stripeWidth", 3);
            UIManager.put("PasswordField.showRevealButton", true);
            //Vista
            login = new Login();
            //Controller
            LoginController controller = new LoginController(login);
            
        } catch (Exception e) {
        }
    }
    
}
