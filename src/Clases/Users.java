/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class Users{
    private static String USERNAME = "";
    private static String PASSWORD = "";
    private static String ROL = "";

    /**
     * Constructor Standard
     */
    public Users(){

    }
    /**
     * Constructor por defecto
     * @param username
     * @param password
     */
    public Users(String username,String password){
        Users.USERNAME = username;
        Users.PASSWORD = password;
        Users.ROL = "";
    }
    /**
     * Valida Usuario y Contraseña
     * @return Verdadero si la Comprobacion fue valida
     */
    public boolean isValidUser(){
        boolean Ok = false;
        //Super usuario
        //*************
        if (Users.USERNAME.equals("super") && Users.PASSWORD.equals("super")){
            Users.ROL="super";
            return true;
        }

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

            String SQL = "";
            SQL += "SELECT ";
            SQL += "usuario,";
            SQL += "clave,";
            SQL += "id_rol ";
            SQL += "FROM ";
            SQL += "usuarios ";
            SQL += "WHERE ";
            SQL += "usuario='" + Users.USERNAME + "' AND ";
            SQL += "clave='" + Users.PASSWORD + "'";

            try{
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(SQL);

                //Usario y password valido
                //************************
                if (rs.last()){
                    try{
                        Ok = true;
                        Users.USERNAME = rs.getString(1);
                        Users.PASSWORD = rs.getString(2);
                        Users.ROL = rs.getString(3);
                    }
                    catch (SQLException ex){
                        Ok = false;
                    }
                }
            }
            catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex);
            }

        return Ok;
    }
    /**
     * Valida si un modulo esta asignado a un rol
     * @param module
     * @return
     */
    public boolean isValidModule(int module){
        boolean bOk = false;
        //Super usuario
        //*************
        if (Users.USERNAME.equals("super") && Users.PASSWORD.equals("super")){
            return true;
        }
            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();

            String miSQL = "";

            miSQL += "SELECT ";
            miSQL += "a.id_rol, ";
            miSQL += "m.clave ";
            miSQL += "FROM accesos AS a ";
            miSQL += "LEFT JOIN modulos AS m ON a.id_modulo = m.id ";
            miSQL += "WHERE ";
            miSQL += "a.id_rol=" + Users.ROL + " AND ";
            miSQL += "m.clave=" + String.valueOf(module);

                try {
                    //Rol y modulo valido
                    //*******************
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery(miSQL);

                    if (rs.last()){
                        return true;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
               
        return bOk;
    }
    //Resetea cualquier valor de login
    //********************************
    public void Limpiar(){
        Users.USERNAME = "";
        Users.PASSWORD = "";
        Users.ROL = "";
    }
    /**
     * Cheque si un rol tiene asignado un modulo
     * @param module
     * @return Verdadero si el modulo esta asignado al rol
     */
    public boolean checkModule(int module)
    {
        boolean Ok = false;
        if (Users.ROL.equals(""))
        {
            JOptionPane.showMessageDialog(null, "ERROR:\nDebe hacer Login antes de ejecutar este modulo.\n\nConsulte al Administrador del Sistema.","Sistema" ,JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!(Ok = isValidModule(module)))
        {
            JOptionPane.showMessageDialog(null, "ERROR:\nPermisos insufiecientes para ejecutar este modulo.\n\nConsulte al Administrador del Sistema.", "Sistema", JOptionPane.ERROR_MESSAGE);
        }
        return Ok;
    }
}
