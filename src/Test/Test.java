package Test;

import Vista.Login;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.UIManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
/**
 *
 * @author franc
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //FlatLightLaf.setup();
        /*SwingUtilities.invokeLater(new Runnable() {
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
            UIManager.put("Button.borderColor", "#B5E0FF");
            UIManager.put("TextComponent.arc", 15);
            UIManager.put("Separator.stripeWidth", 3);
            UIManager.put("PasswordField.showRevealButton", true);
            //System.setProperty("Window.style", "small");

            //Insets marginInsets = new Insets(0, 10, 0, 10);
            //UIManager.put("TextField.margin", marginInsets);
            Login fr = new Login();
            
            //fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //fr.getRootPane().putClientProperty("Window.style", "small");
            fr.getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.WHITE);
            fr.getRootPane().putClientProperty("JRootPane.titleBarBackground", new Color(23,180,252));
            fr.setVisible(true);
        } catch (Exception e) {
        }
    }

}
