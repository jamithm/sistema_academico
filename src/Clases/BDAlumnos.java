/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Admnistrador
 */
public abstract class BDAlumnos {

    public static Alumnos buscarCodigo(long codigo) throws SQLException {
        return buscarCodigo(codigo, null);
    }

    public static Alumnos buscarCodigo(long codigo, Alumnos c) throws SQLException {
        Connection cnn = BD.getConnection();
        PreparedStatement ps = null;
        ps = cnn.prepareStatement("select cedula_escolar, nombres, apellidos from alumnos where cedula_escolar=?");
        ps.setLong(1, codigo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            if (c == null) {
                c = new Alumnos() {
                };
            }
            c.setcedula( codigo);
            c.setnombres(rs.getString("nombres") + " " + rs.getString("apellidos"));
        }
        cnn.close();
        ps.close();
        return c;
    }

    
}