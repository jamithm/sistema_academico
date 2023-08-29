/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Planteles.java
 *
 * Created on 31/12/2011, 02:45:11 PM
 */

package sistema;

import java.sql.*;
import Clases.ConexionMySQL;
import Clases.Forms;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Administrador
 */
public class Planteles extends javax.swing.JInternalFrame {
    DefaultTableModel modelo;
    PreparedStatement insertar;
    PreparedStatement modificar;
    String accion = "";

    /** Creates new form Planteles */
    public Planteles() {
        initComponents();
        Habilitar();
        CargarTabla("");
        Limpiar();
        TamañoTabla();
    }

    void TamañoTabla(){
        TableColumn column = null;
        column = tblPlanteles.getColumnModel().getColumn(0);
        column.setPreferredWidth(80);
        column = tblPlanteles.getColumnModel().getColumn(1);
        column.setPreferredWidth(530);
        column = tblPlanteles.getColumnModel().getColumn(2);
        column.setPreferredWidth(300);
        column = tblPlanteles.getColumnModel().getColumn(3);
        column.setPreferredWidth(150);
    }

     void Habilitar(){
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnCerrar.setEnabled(true);
        txtCodigo.setEnabled(false);
        txtPlantel.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtTelefono.setEnabled(false);
        txtBuscar.setEnabled(true);
        tblPlanteles.setEnabled(true);
    }

    void Deshabilitar(){
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnCerrar.setEnabled(false);
        txtCodigo.setEditable(false);
        txtPlantel.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtTelefono.setEnabled(true);
        txtBuscar.setEnabled(false);
        tblPlanteles.setEnabled(false);
        txtPlantel.requestFocus();
    }

    void Limpiar(){
        txtCodigo.setText("");
        txtTelefono.setText("");
        txtPlantel.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
    }

    void CargarTabla(String valor){
        String [] titulos = {"Codigo", "Plantel", "Direccion", "Telefono"};
        String [] registros = new String [4];

        modelo = new DefaultTableModel(null, titulos);

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT * from planteles WHERE plantel LIKE '%"+valor+"%'";

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next()){
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                registros[2] = rs.getString(3);
                registros[3] = rs.getString(4);

                modelo.addRow(registros);
            }

            tblPlanteles.setModel(modelo);
            Etiqueta();
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    String id_act;
    void CargarDatos(String id){
        String sSQL = "";
        String escuela = "", direccion = "", telefono = "", sector = "";

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        sSQL = "SELECT * FROM planteles WHERE id = " + id;

        try
        {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next())
            {
                escuela  = rs.getString(2);
                direccion  = rs.getString(3);
                telefono = rs.getString(4);
            }

            txtPlantel.setText(escuela);
            txtDireccion.setText(direccion);
            txtTelefono.setText(telefono);
            id_act = id;
            txtCodigo.setText(id_act);

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
        sSQL = "select count(*) from planteles";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            if (rs.last()){
                total = rs.getString(1);
            }
            jLabel8.setText(total);
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

        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPlanteles = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtPlantel = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setTitle("Registros de Planteles");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setText("Buscar:");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        tblPlanteles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblPlanteles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPlantelesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPlanteles);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel7.setText("Total ->");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel8.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)))
        );

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_add.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_remove.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_update.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/guardar-documento-icono-7840-48.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/application-exit-3.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Plantel"));

        jLabel1.setText("Codigo:");

        jLabel2.setText("Plantel:");

        jLabel4.setText("Telefono:");

        jLabel5.setText("Direccion:");

        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPlantel, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlantel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblPlantelesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPlantelesMouseClicked
        //cboSectores.removeAllItems();
        int filasel;
        String id;

        try {
            filasel = tblPlanteles.getSelectedRow();
            if(filasel == -1) {
                //JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila");
            } else {
                modelo = (DefaultTableModel) tblPlanteles.getModel();
                id = (String) modelo.getValueAt(filasel, 0);
                txtBuscar.setText("");
                CargarDatos(id);
                //CargarTabla("");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

}//GEN-LAST:event_tblPlantelesMouseClicked

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Habilitar();
        Limpiar();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        accion = "Insertar";
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
                    PreparedStatement pst = cn.prepareStatement("DELETE FROM planteles WHERE id = ?");
                    pst.setInt(1, new Integer(txtCodigo.getText()));
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

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        accion = "Modificar";
        if (txtCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Modificar...!");
        } else{
            Deshabilitar();
        }
}//GEN-LAST:event_btnModificarActionPerformed


    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtPlantel.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese Plantel...!");
            txtPlantel.requestFocus();
        } else if (txtDireccion.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese Direccion...!");
            txtDireccion.requestFocus();
        } else if (txtTelefono.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese Telefono...!");
            txtTelefono.requestFocus();
        } else {

            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();

            String escuela, direccion, telefono ;
            escuela = txtPlantel.getText();
            direccion = txtDireccion.getText();
            telefono = txtTelefono.getText();
            String sSQL = "";
            String  mensaje = "";

            if(accion.equals("Insertar")){
                sSQL = "INSERT INTO  planteles (plantel, direccion, telefono) VALUES(?, ?, ?)";
                mensaje = "Registro Guardado...!";

                try{
                    insertar = cn.prepareStatement(sSQL);
                    insertar.setString(1, escuela);
                    insertar.setString(2, direccion);
                    insertar.setString(3, telefono);

                    int n = insertar.executeUpdate();
                    if (n == 1){
                        //JOptionPane.showMessageDialog(null, mensaje);
                    }
                } catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, ex);
                }
            } else if(accion.equals("Modificar")){
                sSQL = "UPDATE planteles SET plantel = ?, direccion = ?, telefono = ? WHERE id = " + id_act;
                mensaje = "Registro Actualizado...!";

                try{
                    modificar = cn.prepareStatement(sSQL);
                    modificar.setString(1, escuela);
                    modificar.setString(2, direccion);
                    modificar.setString(3, telefono);

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

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCerrarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        String valor = txtBuscar.getText();
        CargarTabla(valor);
        TamañoTabla();
    }//GEN-LAST:event_txtBuscarKeyPressed

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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblPlanteles;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtPlantel;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

}
