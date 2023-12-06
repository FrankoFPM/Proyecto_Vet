/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reportes;

import DB.Conexion;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Franko
 */
public class Reportes {
    private Conexion conreporte = new Conexion();
    Connection cnreporte;
    private JasperReport jasperreport;
    private JasperPrint jasperprint;
    private JasperViewer jasperviewer;
    String origen = "C:\\Users\\USUARIO\\Documents\\NetBeansProjects\\Proyecto_Vet\\src\\Reportes\\Clinico.jrxml";
    String destino = "C:\\Users\\USUARIO\\Documents\\NetBeansProjects\\Proyecto_Vet\\src\\Reportes\\Clinico.pdf";
    String origenVenta = "C:\\Users\\USUARIO\\Documents\\NetBeansProjects\\Proyecto_Vet\\src\\Reportes\\BoletaVenta.jrxml";
    String destinoVenta = "C:\\Users\\USUARIO\\Documents\\NetBeansProjects\\Proyecto_Vet\\src\\Reportes\\BoletaVenta.pdf";

    public void reporteClinico(String cod) {

        try {
            cnreporte = conreporte.ObtenerConexion();
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("id", cod);
            jasperreport = JasperCompileManager.compileReport(origen);
            jasperprint = JasperFillManager.fillReport(jasperreport, parametros, cnreporte);
            JasperExportManager.exportReportToPdfFile(jasperprint, destino);
            jasperviewer = new JasperViewer(jasperprint, false);
            jasperviewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jasperviewer.setVisible(true);
        } catch (JRException r1) {
            r1.getMessage();
        }
    }
    public void reporteVenta(String cod) {

        try {
            cnreporte = conreporte.ObtenerConexion();
            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("id", cod);
            jasperreport = JasperCompileManager.compileReport(origenVenta);
            jasperprint = JasperFillManager.fillReport(jasperreport, parametros, cnreporte);
            JasperExportManager.exportReportToPdfFile(jasperprint, destinoVenta);
            jasperviewer = new JasperViewer(jasperprint, false);
            jasperviewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jasperviewer.setVisible(true);
        } catch (JRException r1) {
            r1.getMessage();
        }
    }
}
