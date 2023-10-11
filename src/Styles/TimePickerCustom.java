package Styles;

import com.github.lgooddatepicker.components.TimePicker;
import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.UIManager;


public class TimePickerCustom extends TimePicker{
    public TimePickerCustom() {
        super();
        getTextField(this);
    }

    private JTextField getTextField(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                textField.setBorder(UIManager.getBorder("TextField.border"));
                textField.setMargin(new Insets(0, 6, 0, 6));
                return textField;
            } else if (component instanceof Container) {
                return getTextField((Container) component);
            }
        }
        return null;
    }
}
