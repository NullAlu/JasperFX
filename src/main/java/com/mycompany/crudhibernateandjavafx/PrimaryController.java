/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crudhibernateandjavafx;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.persistence.Query;
import javax.swing.JFrame;
import models.*;
import net.sf.jasperreports.engine.JRException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;

/**
 * FXML Controller class
 *
 * @author marco
 */
public class PrimaryController implements Initializable {

       @FXML
    private TableColumn<Pedido, Integer> cId;
    @FXML
    private TableColumn<Pedido, String> cAlumno;
    @FXML
    private TableColumn<Pedido, String> cFecha;
    @FXML
    private TableColumn<Pedido, Integer> cProductoId;
    @FXML
    private TableColumn<Pedido, String> cEstado;
    @FXML
    private TableView<Pedido> tabla;
    
        private static SessionFactory sf = new Configuration().configure().buildSessionFactory();
        private static Session s = sf.openSession();
  
    @FXML
    private Label pendientes;
    @FXML
    private Button btnCarta;
    @FXML
    private Button btnPedidos;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ObservableList<Pedido> contenido = FXCollections.observableArrayList();
        tabla.setItems(contenido);
        
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        contenido.clear();
                        contenido.addAll(listarComandas());
                        count();
                        
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);



        cId.setCellValueFactory(new PropertyValueFactory<>("id"));
        cAlumno.setCellValueFactory(new PropertyValueFactory<>("alumno"));
        cFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        cProductoId.setCellValueFactory(new PropertyValueFactory<>("prodId"));
        cEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        contenido.addAll(listarComandas());

    }
        public void count(){
         pendientes.setText("Pedidos pendientes: " + String.valueOf(listarComandasPendientes().size()));
        }
        public static ArrayList<Pedido> listarComandas(){
        
        Query q = s.createQuery("FROM Pedido", Pedido.class);
        
        ArrayList<Pedido> qres = (ArrayList<Pedido>) q.getResultList();
       // System.out.println(qres.toString());
       return qres;
    }
        public static ArrayList<Pedido> listarComandasPendientes(){
        
        Query q = s.createQuery("FROM Pedido WHERE estado = 'pendiente'", Pedido.class);
        
        ArrayList<Pedido> qres = (ArrayList<Pedido>) q.getResultList();
      
       return qres;
    }
        
    @FXML
    private void click(MouseEvent event) {

       Pedido p = tabla.getSelectionModel().getSelectedItem();
         Transaction t = s.beginTransaction();
            p.setEstado("Recogido");
            s.update(p);
            t.commit();
            listarComandas();
    }


    @FXML
    private void mostarCarta(ActionEvent event) {
             String archivo = "Pedidos.jrxml";
         
        try {
            var parameters = new HashMap();
            parameters.put("Carta", "Listado de productos");
            
            JasperReport informe = JasperCompileManager.compileReport(archivo);
            JasperPrint impresion = JasperFillManager.fillReport(informe, parameters, Conexion.getConexion());
            
            JRViewer visor = new JRViewer(impresion);
            
            JFrame ventanaInforme = new JFrame("Informe");
            ventanaInforme.getContentPane().add(visor);
            ventanaInforme.pack();
            ventanaInforme.setVisible(true);
            
            JRPdfExporter exportador = new JRPdfExporter();
            exportador.setExporterInput( new SimpleExporterInput(impresion) );
            exportador.setExporterOutput( new SimpleOutputStreamExporterOutput("Carta.pdf") );
            
            var configuracion = new SimplePdfExporterConfiguration();
            exportador.setConfiguration(configuracion);
            
            exportador.exportReport();
            
                    
        } catch (JRException ex) {
            System.out.println(ex);
        }
    }
    

    @FXML
    private void mostarPedidos(ActionEvent event) {
         String archivo = "Productos.jrxml";
         
        try {
            var parameters = new HashMap();
            parameters.put("Pedidos", "Listado de pedidos");
            
            JasperReport informe = JasperCompileManager.compileReport(archivo);
            JasperPrint impresion = JasperFillManager.fillReport(informe, parameters, Conexion.getConexion());
            
            JRViewer visor = new JRViewer(impresion);
            
            JFrame ventanaInforme = new JFrame("Informe");
            ventanaInforme.getContentPane().add(visor);
            ventanaInforme.pack();
            ventanaInforme.setVisible(true);
            
            JRPdfExporter exportador = new JRPdfExporter();
            exportador.setExporterInput( new SimpleExporterInput(impresion) );
            exportador.setExporterOutput( new SimpleOutputStreamExporterOutput("Pedidos.pdf") );
            
            var configuracion = new SimplePdfExporterConfiguration();
            exportador.setConfiguration(configuracion);
            
            exportador.exportReport();
            
                    
        } catch (JRException ex) {
            System.out.println(ex);
        }
    }

}
