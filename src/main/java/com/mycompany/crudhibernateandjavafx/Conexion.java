/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.crudhibernateandjavafx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author marco
 */
public class Conexion {
    
    private static Connection conexion;
    
    static{
        String url="jdbc:mysql://localhost:3307/acceso";
        String user="root";
        String pass="";
        
        try{
            conexion = DriverManager.getConnection(url,user,pass);
            System.out.println("Conexión realizada con éxito, mostrando tabla");
        }catch(SQLException ex){
            System.out.println("Error con la conexión" + ex);
        }
    }
    
    public static Connection getConexion(){
        return conexion;
    }
    
}
