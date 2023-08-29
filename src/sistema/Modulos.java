/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Areas.java
 *
 * Created on 01/02/2012, 09:52:18 PM
 */

package sistema;

import Clases.Forms;
import Clases.ConexionMySQL;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Administrador
 */
public class Modulos extends javax.swing.JInternalFrame {
    //tabla modelo
    //************
    DefaultTableModel MODELO;
    //variable para guardar
    //*********************
    String ACCION = "";
    //variable para actualizar
    //************************
    String ID_ACT;

    /** Creates new form Areas */
    public Modulos() {
        initComponents();
        //habilitar botones
        //*****************
        this.Habilitar();
        //Cargar tabla
        //************
        this.CargarTabla("");
        //Tamaño de la tabla
        //******************
        this.TamañoTabla();
    }

    //metodo para el tamaño de la tabla
    //*********************************
    private void TamañoTabla(){
        TableColumn column = null;
        column = tblModulos.getColumnModel().getColumn(0);
        column.setPreferredWidth(78);
        column = tblModulos.getColumnModel().getColumn(1);
        column.setPreferredWidth(350);
    }

    //metodo para habilitar
    //*********************
    private void Habilitar(){
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnCerrar.setEnabled(true);
        txtCodigo.setEnabled(false);
        txtModulo.setEnabled(false);
        txtClave.setEnabled(false);
        txtBuscar.setEnabled(true);
        tblModulos.setEnabled(true);
    }

    //metodo para desabilitar
    //***********************
    private void Deshabilitar(){
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnCerrar.setEnabled(false);
        txtCodigo.setEditable(false);
        txtModulo.setEnabled(true);
        txtClave.setEnabled(true);
        txtBuscar.setEnabled(false);
        tblModulos.setEnabled(false);
        txtModulo.requestFocus();
    }

    //metodo para limpiar campos
    //**************************
    private void Limpiar(){
        txtCodigo.setText("");
        txtModulo.setText("");
        txtClave.setText("");
    }

    //metodo para cargar tabla
    //************************
    private void CargarTabla(String valor){
        String [] titulos = {"Codigo", "Modulos", "Clave"};
        String [] registros = new String [3];

        MODELO = new DefaultTableModel(null, titulos);

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT * FROM modulos WHERE denominacion LIKE '%"+valor+"%'";

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next()){
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                registros[2] = rs.getString(3);

                MODELO.addRow(registros);
            }

            tblModulos.setModel(MODELO);
            Etiqueta();
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //metodo para cargar datos
    //************************
    private void CargarDatos(String id){
        String sSQL = "";
        String modulo = "";
        String clave = "";

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        sSQL = "SELECT * FROM modulos WHERE id= " + id;

        try
        {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next())
            {
                modulo  = rs.getString(2);
                clave = rs.getString(3);
            }

            txtModulo.setText(modulo);
            txtClave.setText(clave);
            ID_ACT = id;
            txtCodigo.setText(ID_ACT);

        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //metodo para llevar registro
    //***************************
    private void Etiqueta(){
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String total = "";
        String sSQL = "";
        sSQL = "select count(*) from modulos";
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
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblModulos = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtModulo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtClave = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Registro de Modulos");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_update.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setName("btnModificar"); // NOI18N
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel3.setText("Buscar:");
        jLabel3.setName("jLabel3"); // NOI18N

        txtBuscar.setName("txtBuscar"); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        tblModulos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblModulos.setName("tblModulos"); // NOI18N
        tblModulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblModulosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblModulos);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel6.setText("0");
        jLabel6.setName("jLabel6"); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel5.setText("Total ->");
        jLabel5.setName("jLabel5"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/application-exit-3.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.setName("btnCerrar"); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Modulos"));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel1.setText("Codigo:");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("Modulo:");
        jLabel2.setName("jLabel2"); // NOI18N

        txtCodigo.setFont(new java.awt.Font("Tahoma", 1, 12));
        txtCodigo.setName("txtCodigo"); // NOI18N

        txtModulo.setName("txtModulo"); // NOI18N

        jLabel4.setText("Clave:");
        jLabel4.setName("jLabel4"); // NOI18N

        txtClave.setName("txtClave"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtModulo, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                    .addComponent(txtClave))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtModulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/guardar-documento-icono-7840-48.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setName("btnGuardar"); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_add.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setName("btnNuevo"); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_remove.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setName("btnEliminar"); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setName("btnCancelar"); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(btnGuardar, 0, 0, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, 0, 257, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        ACCION = "Modificar";
        if (txtCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Modificar...!");
        } else{
            Deshabilitar();
        }
}//GEN-LAST:event_btnModificarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        String valor = txtBuscar.getText();
        CargarTabla(valor);
        TamañoTabla();
}//GEN-LAST:event_txtBuscarKeyPressed

    private void tblModulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblModulosMouseClicked
        int filasel;
        String id;

        try {
            filasel = tblModulos.getSelectedRow();
            if(filasel == -1) {
               // JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila...!");
            } else {
                MODELO = (DefaultTableModel) tblModulos.getModel();
                id = (String) MODELO.getValueAt(filasel, 0);
                txtBuscar.setText("");
                CargarDatos(id);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
}//GEN-LAST:event_tblModulosMouseClicked

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCerrarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtModulo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese Modulo...!");
            txtModulo.requestFocus();
        } else if (txtClave.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese Clave...!");
            txtClave.requestFocus();
        } else{

            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();

            String modulo = txtModulo.getText();
            String clave = txtClave.getText();
            String sSQL = "";
            String  mensaje = "";

            if(ACCION.equals("Insertar")){
                sSQL = "INSERT INTO modulos (denominacion, clave) VALUES(?, ?)";
                mensaje = "Registro Guardado...!";
            } else if(ACCION.equals("Modificar")){
                sSQL = "UPDATE modulos SET denominacion = ?, clave = ? WHERE id = " + ID_ACT;
                mensaje = "Registro Actualizado...!";
            }

            try{
                PreparedStatement pst = cn.prepareStatement(sSQL);
                pst.setString(1, modulo);
                pst.setString(2, clave);

                int n = pst.executeUpdate();
                if(n == 1){
                   // JOptionPane.showMessageDialog(null, mensaje);

                }
            } catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
            CargarTabla("");
            Habilitar();
            Limpiar();
            TamañoTabla();
        }
}//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        ACCION = "Insertar";
        Deshabilitar();
        Limpiar();
}//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (txtCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Eliminar...!");
        } else {

            int opcion = JOptionPane.showConfirmDialog(null,"¿Desea eliminar este registro...?", "Confirmación...!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcion == 0){
                ConexionMySQL mysql = new ConexionMySQL();
                Connection cn = mysql.Conectar();

                try{
                    PreparedStatement pst = cn.prepareStatement("DELETE FROM modulos WHERE id = ?");
                    pst.setInt(1, new Integer(txtCodigo.getText()));
                    pst.execute();
                    //JOptionPane.showMessageDialog(null, "Registro Eliminado...!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    Limpiar();
                    CargarTabla("");
                    TamañoTabla();
                } catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "No se Puede Eliminar \n Tiene Registros Asosiados...!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else{
                Limpiar();
                CargarTabla("");
                TamañoTabla();
            }
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Habilitar();
        Limpiar();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        //Centramos Formulario
        //********************
        Forms f = new Forms(this);
        f.setCenterFrame();
    }//GEN-LAST:event_formComponentShown


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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblModulos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtClave;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtModulo;
    // End of variables declaration//GEN-END:variables

}
