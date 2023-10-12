package Controlador;

import Vista.Dashboard_UI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;

public abstract class PanelController {

    JPanel panel;
    Dashboard_UI app;

    public PanelController(JPanel panel, Dashboard_UI app) {
        this.panel = panel;
        this.app = app;
        showWindow(panel);
    }

    void showWindow(JPanel panel) {
        panel.setPreferredSize(new Dimension(1000, 500)); // Tamaño inicial
        app.content.removeAll();
        app.content.setLayout(new BorderLayout());
        app.content.add(panel, BorderLayout.CENTER);
        app.content.revalidate();
        app.content.repaint();
    }

    protected abstract void addListeners();
    protected abstract void reloadWindow();
}
