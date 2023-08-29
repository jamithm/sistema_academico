/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author BADY RICHARD
 */
public abstract class BD {
     public static Connection getConnection() {
        Connection cn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/sistema_academico";
            String user = "root";
            //String password = "";
            String password = "";
            cn= DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            cn=null;
            System.out.println("Error no se puede cargar el driver:" + e.getMessage());
        } catch (SQLException e) {
            cn=null;
            System.out.println("Error no se establecer la conexion:" + e.getMessage());
        }
        return cn;
    }
}
