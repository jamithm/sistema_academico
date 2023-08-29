/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Usuarios.java
 *
 * Created on 08/01/2012, 12:37:33 PM
 */

package sistema;

import Clases.ConexionMySQL;
import Clases.Forms;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Adminitrador
 */
public class Docentes extends javax.swing.JInternalFrame {
    DefaultTableModel modelo;
    PreparedStatement insertar;
    PreparedStatement modificar;
    String accion = "";
    int rol;
    Acceso ac;

    /** Creates new form Usuarios */
    public Docentes() {
        initComponents();
        Habilitar();
        Limpiar();
        CargarTabla("");
        TamañoTabla();
    }

    void TamañoTabla(){
        TableColumn column = null;
        column = tblDocentes.getColumnModel().getColumn(0);
        column.setPreferredWidth(120);
        column = tblDocentes.getColumnModel().getColumn(1);
        column.setPreferredWidth(180);
        column = tblDocentes.getColumnModel().getColumn(2);
        column.setPreferredWidth(180);
        column = tblDocentes.getColumnModel().getColumn(3);
        column.setPreferredWidth(150);
        column = tblDocentes.getColumnModel().getColumn(4);
        column.setPreferredWidth(120);
    }

    void Habilitar(){
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnCerrar.setEnabled(true);
        txtCedula.setEnabled(false);
        txtNombres.setEnabled(false);
        txtApellidos.setEnabled(false);
        txtDireccon.setEnabled(false);
        txtTelefono.setEnabled(false);
        txtBuscar.setEnabled(true);
        tblDocentes.setEnabled(true);
    }

    void Deshabilitar(){
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnCerrar.setEnabled(false);
        txtCedula.setEnabled(true);
        txtNombres.setEnabled(true);
        txtApellidos.setEnabled(true);
        txtDireccon.setEnabled(true);
        txtTelefono.setEnabled(true);
        txtBuscar.setEnabled(false);
        tblDocentes.setEnabled(false);
        txtCedula.requestFocus();
    }

    void Limpiar(){
        txtCedula.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtDireccon.setText("");
    }

    void CargarTabla(String valor){
        String [] titulos = {"Cedula", "Nombres", "Apellidos", "Telefono", "Direccion"};
        String [] registros = new String [6];

        modelo = new DefaultTableModel(null, titulos);

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT * FROM docentes "
        + "WHERE nombres LIKE '%"+ valor +"%'";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next()){
                String var = rs.getString(1);
                if (var.length() == 7){
                    registros[0] = "0" + rs.getString(1);
                    registros[1] = rs.getString(2);
                    registros[2] = rs.getString(3);
                    registros[3] = rs.getString(4);
                    registros[4] = rs.getString(5);
                }else{
                    registros[0] = rs.getString(1);
                    registros[1] = rs.getString(2);
                    registros[2] = rs.getString(3);
                    registros[3] = rs.getString(4);
                    registros[4] = rs.getString(5);
                }
                modelo.addRow(registros);
            }

            tblDocentes.setModel(modelo);
            Etiqueta();
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    String id_act;
    void CargarDatos(String id){
        String sSQL="";
        String nombre = "",  apellido = "", telefono = "", direccion = "";

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        sSQL = "SELECT * FROM docentes "
        + "WHERE cedula = " + id;

        try
        {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next())
            {
                nombre  = rs.getString(2);
                apellido = rs.getString(3);
                telefono = rs.getString(4);
                direccion = rs.getString(5);
            }

            txtNombres.setText(nombre);
            txtApellidos.setText(apellido);
            txtTelefono.setText(telefono);
            txtDireccon.setText(direccion);
            id_act = id;
            txtCedula.setText(id_act);

        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void Etiqueta(){
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String total = "";
        String sSQL = "";
        sSQL = "select count(*) from docentes";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            if (rs.last()){
                total = rs.getString(1);
            }


            jLabel6.setText(total);
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnModificar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtApellidos = new javax.swing.JTextField();
        txtDireccon = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDocentes = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Registro de Docentes");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_update.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/application-exit-3.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_remove.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Docente"));

        jLabel1.setText("Cedula:");

        jLabel2.setText("Nombres:");

        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });

        jLabel4.setText("Apellidos:");

        jLabel7.setText("Telefono:");

        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        jLabel8.setText("Direccion:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel7)
                        .addComponent(jLabel4)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtDireccon)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono)
                    .addComponent(txtApellidos)
                    .addComponent(txtNombres, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDireccon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_add.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/guardar-documento-icono-7840-48.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tblDocentes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblDocentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDocentesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDocentes);

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        jLabel3.setText("Buscar:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel5.setText("Total ->");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel6.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 723, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6))
                            .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 683, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        accion = "Modificar";
        if (txtCedula.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Modificar...!");
        } else{
            Deshabilitar();
            txtCedula.setEditable(false);
            txtNombres.requestFocus();
        }
}//GEN-LAST:event_btnModificarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Habilitar();
        Limpiar();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCerrarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (txtCedula.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Eliminar...!");
        } else {

            int opcion = JOptionPane.showConfirmDialog(null,"¿Desea eliminar este registro...?", "Confirmación...!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcion == 0){
                ConexionMySQL mysql = new ConexionMySQL();
                Connection cn = mysql.Conectar();

                try{
                    PreparedStatement pst = cn.prepareStatement("DELETE FROM docentes WHERE cedula = ?");
                    pst.setInt(1, new Integer(txtCedula.getText()));
                    pst.execute();
                    //JOptionPane.showMessageDialog(null, "Registro Eliminado...!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    Limpiar();
                    CargarTabla("");
                    TamañoTabla();
                } catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error de borrado: "+ ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else{
                Limpiar();
                CargarTabla("");
                TamañoTabla();
            }
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void tblDocentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDocentesMouseClicked
        int filasel;
        String id;

        try {
            filasel = tblDocentes.getSelectedRow();
            if(filasel == -1) {
                //JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila...!");
            } else {
                modelo = (DefaultTableModel) tblDocentes.getModel();
                id = (String) modelo.getValueAt(filasel, 0);
                txtBuscar.setText("");
                CargarDatos(id);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
}//GEN-LAST:event_tblDocentesMouseClicked

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        String valor = txtBuscar.getText();
        CargarTabla(valor);
        TamañoTabla();
}//GEN-LAST:event_txtBuscarKeyPressed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        accion = "Insertar";
        Deshabilitar();
        txtCedula.setEditable(true);
        Limpiar();
}//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtCedula.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese Cedula...!");
            txtCedula.requestFocus();
        } else if (txtNombres.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese Nombres...!");
            txtNombres.requestFocus();
        } else if (txtApellidos.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese Apellidos...!");
            txtApellidos.requestFocus();
        } else if (txtTelefono.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese Usuario...!");
            txtTelefono.requestFocus();
        } else if (txtDireccon.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese Clave...!");
            txtDireccon.requestFocus();
        } else {

            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();

            String cedula, nombre, apellido, telefono, direccion;

            cedula = txtCedula.getText();
            nombre = txtNombres.getText();
            apellido = txtApellidos.getText();
            telefono = txtTelefono.getText();
            direccion = txtDireccon.getText();

            String sSQL = "";
            String  mensaje = "";

            if(accion.equals("Insertar")){
                sSQL = "INSERT INTO docentes VALUES(?, ?, ?, ?, ?)";
                mensaje = "Registro Guardado...!";

                try{
                    insertar = cn.prepareStatement(sSQL);
                    insertar.setString(1, cedula);
                    insertar.setString(2, nombre);
                    insertar.setString(3, apellido);
                    insertar.setString(4, telefono);
                    insertar.setString(5, direccion);

                    int n = insertar.executeUpdate();
                    if(n == 1){
                        //JOptionPane.showMessageDialog(null, mensaje);

                    }
                } catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Cedula Ya Esta Registrada...!");
                }


            } else if(accion.equals("Modificar")){
                sSQL = "UPDATE docentes SET nombres = ?,"
                        + "apellidos = ?,"
                        + "telefono = ?,"
                        + "direccion = ? "
                        + "WHERE cedula = " + id_act;
                mensaje = "Registro Actualizado...!";

                try{
                    modificar = cn.prepareStatement(sSQL);
                    modificar.setString(1, nombre);
                    modificar.setString(2, apellido);
                    modificar.setString(3, telefono);
                    modificar.setString(4, direccion);

                    int n = modificar.executeUpdate();
                    if(n == 1){
                        //JOptionPane.showMessageDialog(null, mensaje);

                    }
                } catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex);
                }
            }

            CargarTabla("");
            Habilitar();
            Limpiar();
            TamañoTabla();
        }
}//GEN-LAST:event_btnGuardarActionPerformed

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))){
            getToolkit().beep();
            evt.consume();
        }
        String Caracteres = txtCedula.getText();
        if(Caracteres.length()>=8){
            evt.consume();
        }
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        //Centramos Formulario
        //********************
        Forms f = new Forms(this);
        f.setCenterFrame();
    }//GEN-LAST:event_formComponentShown

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))){
            getToolkit().beep();
            evt.consume();
        }
        String Caracteres = txtTelefono.getText();
        if(Caracteres.length()>=11){
            evt.consume();
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblDocentes;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtDireccon;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

}
