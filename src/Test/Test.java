package Test;

import Vista.Login;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Test {

    public static void main(String[] args) {
        //FlatLightLaf.setup();
        /*
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Aquí estableces el look and feel
                try {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    UIManager.put("Button.arc", 15);
                    UIManager.put("TextComponent.arc", 15);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Aquí creas tus componentes
                //JFrame.setDefaultLookAndFeelDecorated(true);
                Login fr = new Login();
                fr.setVisible(true);
            }
        });*/
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("Button.arc", 15);
            UIManager.put("TextComponent.arc", 15);
            Login fr = new Login();
            fr.setVisible(true);
        } catch (Exception e) {
        }
    }

}
