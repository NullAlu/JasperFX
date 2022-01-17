package com.mycompany.jasperfx;

import java.io.IOException;
import javafx.fxml.FXML;

import java.sql.Connection;
 
import java.sql.DriverManager;
 
import net.sf.jasperreports.engine.JasperFillManager;
 
import net.sf.jasperreports.engine.JasperPrint;
 
import net.sf.jasperreports.engine.JasperReport;
 
import net.sf.jasperreports.engine.JasperCompileManager;
 
import net.sf.jasperreports.view.JasperViewer;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        try{
 
      Class.forName("com.mycompany.jasperfx");
       Connection con =     DriverManager.getConnection("jdbc:mysql://localhost:3307/acceso?zeroDateTimeBehavior=CONVERT_TO_NULL");
       String reportPath = "C:\\Users\\marco\\Desktop\\ejemplo.jrxml";
       JasperReport jr = JasperCompileManager.compileReport(reportPath);
       JasperPrint jp = JasperFillManager.fillReport(jr,null, con);
       JasperViewer.viewReport(jp);
       con.close();
}
 
catch(Exception ex)
{
    ex.printStackTrace();
}
    }
}
