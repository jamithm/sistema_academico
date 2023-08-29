/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Accsos.java
 *
 * Created on 02/08/2011, 07:11:16 AM
 */
package sistema;

import Clases.ConexionMySQL;
import Clases.Forms;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrador
 */
public final class Accesos extends javax.swing.JInternalFrame{
    //Modelo de la Tabla
    //******************
    DefaultTableModel MODELO;
    //Variables
    //*********
    String ID1, ID2;
    String ACCION = "";
    String MENSAJE = "";
    int R;
    int M;
    //Acciones
    //********
    PreparedStatement INSERTAR;
    PreparedStatement MODIFIACR;
    PreparedStatement ELIMINAR;

    /** Creates new form Accesos */
    public Accesos(){        
        initComponents();
        //Habilitar los Objetos
        //*********************
        this.Habilitar();
        //Cargamos registros en la tabla
        //*****************************
        this.CargarTabla("");
        //Colocamos el Tamaño a la tabla
        //******************************
        this.TamañoTabla();
        //Limpiamos Combos
        //****************
        this.Limpiar();
    }

    //Metodo para el Tamaño Tabla
    //***************************
    private void TamañoTabla(){
        this.tblAccesos.getColumnModel().getColumn(0).setMaxWidth(0);
        this.tblAccesos.getColumnModel().getColumn(0).setMinWidth(0);
        this.tblAccesos.getColumnModel().getColumn(0).setPreferredWidth(0);
        this.tblAccesos.getColumnModel().getColumn(2).setMaxWidth(0);
        this.tblAccesos.getColumnModel().getColumn(2).setMinWidth(0);
        this.tblAccesos.getColumnModel().getColumn(2).setPreferredWidth(0);
    }

    //Metodo para Habilitar objetos
    //*****************************
    private void Habilitar(){
        this.cboRoles.setEnabled(false);
        this.cboModulos.setEnabled(false);
        this.txtBuscar.setEnabled(false);
        this.btnNuevo.setEnabled(true);
        this.btnGuardar.setEnabled(false);
        this.btnModificar.setEnabled(true);
        this.btnEliminar.setEnabled(true);
        this.btnCancelar.setEnabled(false);
        this.btnCerrar.setEnabled(true);
        this.tblAccesos.setEnabled(true);
    }

    //Metodo para Deshabilitar objetos
    //********************************
    private void Deshabilitar(){
        this.cboRoles.setEnabled(true);
        this.cboModulos.setEnabled(true);
        this.txtBuscar.setEnabled(false);
        this.btnNuevo.setEnabled(false);
        this.btnGuardar.setEnabled(true);
        this.btnModificar.setEnabled(false);
        this.btnEliminar.setEnabled(false);
        this.btnCancelar.setEnabled(true);
        this.btnCerrar.setEnabled(false);
        this.tblAccesos.setEnabled(false);
        this.cboRoles.requestFocus();
    }

    //Metodo para Limpiar Campos
    //**************************
    private void Limpiar(){
        this.cboRoles.removeAllItems();
        this.cboModulos.removeAllItems();
        this.cboRoles.addItem("Seleccione");
        this.cboModulos.addItem("Seleccione");
    }

    //Metodo pata Llenar Combo Roles
    //******************************
    private void ComboRoles(){
        //Agregar item en blanco
        //this.cboRoles.addItem("");
        //Creamos Conexion
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        //Creamos Sentencia SQL
        //*********************
        String SQL = "";
        SQL += " SELECT denominacion ";
        SQL += " FROM roles ";
        //Ejecutamos Sentencia SQL
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()){
                this.cboRoles.addItem(rs.getObject(1));
            }
        } catch (SQLException  ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //Metodo pata Llenar Combo Modulos
    //********************************
    private void ComboModulos(){
        //Agregar item en blanco
        //this.cboModulos.addItem("");
        //Creamos Conexion
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        //Creamos Sentencia SQL
        //*********************
        String SQL = "";
        SQL += " SELECT denominacion ";
        SQL += " FROM modulos ";
        //Ejecutamos Sentencia SQL
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while (rs.next()){
                this.cboModulos.addItem(rs.getObject(1));
            }
        } catch (SQLException  ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //Metodo para Cargar tabla
    //************************
    private void CargarTabla(String Valor){
        //Arreglo para los Titulos
        //***********************
        String [] titulos = {"Codigo", "Rol", "Codigo","Modulo", "Clave"};
        //Arreglo para los Registro
        //************************
        String [] registros = new String [5];
        //Creamos el Modelo de la Tabla
        //*****************************
        MODELO = new DefaultTableModel(null, titulos);
        //Creamos Conexion
        //****************
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        //Creamos Sentencia SQL
        //*********************
        String SQL = "";
        SQL += " SELECT r.id, r.denominacion, m.id, m.denominacion, m.clave ";
        SQL += " FROM accesos AS a ";
        SQL += "INNER JOIN roles AS r ON a.id_rol = r.id ";
        SQL += "INNER JOIN modulos AS m ON a.id_modulo = m.id ";
        SQL += "WHERE m.denominacion LIKE '%" + Valor + "%'";
        //Ejecutamos Accion
        //*****************
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while(rs.next()){
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                registros[2] = rs.getString(3);
                registros[3] = rs.getString(4);
                registros[4] = rs.getString(5);
                MODELO.addRow(registros);
            }
            tblAccesos.setModel(MODELO);
            Etiqueta();
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //Metodo para Cargar Datos
    //************************
    private void CargarDatos(String Id1, String Id2){
        //Variables
        //*********
        String SQL = "";
        String RL = "", MD = "";
        //Creamos la Conexion
        //*******************
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        //Creamos Sentencia SQL
        //*********************
        SQL += " SELECT r.id, r.denominacion, m.id, m.denominacion, m.clave ";
        SQL += " FROM accesos AS a ";
        SQL += "INNER JOIN roles AS r ON a.id_rol = r.id ";
        SQL += "INNER JOIN modulos AS m ON a.id_modulo = m.id ";
        SQL += " WHERE a.id_rol = " + Id1 + " AND a.id_modulo = " + Id2 + " ";
        //Ejecutamos Accion
        //*****************
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);
            while(rs.next())
            {
                RL = rs.getString(2);
                MD = rs.getString(4);
            }
            cboRoles.setSelectedItem((Object)RL);
            cboModulos.setSelectedItem((Object)MD);

            ID1 = Id1;
            ID2 = Id2;
        }
        catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //metodo para llevar registro
    //***************************
    private void Etiqueta(){
        //Creamos conexion
        //****************
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        //Variables
        //*********
        String total = "";
        String sSQL = "";
        //Creamos Sentencia SQL
        //*********************
        sSQL = "select count(*) from accesos";
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

        pnl_marco = new javax.swing.JPanel();
        lbl_rol = new javax.swing.JLabel();
        lbl_modulo = new javax.swing.JLabel();
        cboRoles = new javax.swing.JComboBox();
        cboModulos = new javax.swing.JComboBox();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAccesos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Accesos");
        setToolTipText("");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        pnl_marco.setBorder(javax.swing.BorderFactory.createTitledBorder("Acceso"));
        pnl_marco.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lbl_rol.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_rol.setText("Rol:");
        lbl_rol.setName("lbl_rol"); // NOI18N

        lbl_modulo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_modulo.setText("Modulo:");
        lbl_modulo.setName("lbl_modulo"); // NOI18N

        cboRoles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboRolesMouseClicked(evt);
            }
        });
        cboRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboRolesActionPerformed(evt);
            }
        });

        cboModulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboModulosMouseClicked(evt);
            }
        });
        cboModulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboModulosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_marcoLayout = new javax.swing.GroupLayout(pnl_marco);
        pnl_marco.setLayout(pnl_marcoLayout);
        pnl_marcoLayout.setHorizontalGroup(
            pnl_marcoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_marcoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_marcoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbl_modulo)
                    .addComponent(lbl_rol, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_marcoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboRoles, 0, 377, Short.MAX_VALUE)
                    .addComponent(cboModulos, 0, 377, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_marcoLayout.setVerticalGroup(
            pnl_marcoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_marcoLayout.createSequentialGroup()
                .addGroup(pnl_marcoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboRoles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_rol, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_marcoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboModulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_modulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_add.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setToolTipText("Nuevo");
        btnNuevo.setFocusable(false);
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNuevo.setName("btnNuevo"); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/guardar-documento-icono-7840-48.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setFocusable(false);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnGuardar.setName("btnGuardar"); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setFocusable(false);
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCancelar.setName("btnCancelar"); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/application-exit-3.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.setFocusable(false);
        btnCerrar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCerrar.setName("btnCerrar"); // NOI18N
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        tblAccesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblAccesos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAccesosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAccesos);

        jLabel1.setText("Buscar:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel5.setText("Total ->");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel6.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
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
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)))
        );

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_update.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_remove.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_marco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnModificar, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.LEADING)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnl_marco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
    //Centramos Formulario
    //********************
    Forms f = new Forms(this);
    f.setCenterFrame();
}//GEN-LAST:event_formComponentShown
private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
    //Limpiamos los Campos
    //********************
    this.Limpiar();
    //Cerramos formulario
    //*******************
    this.dispose();
}//GEN-LAST:event_btnCerrarActionPerformed
private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
    //Accion Insertar
    //***************
    this.ACCION = "Insertar";
    //Deshabilitamos Objetos
    //**********************
    this.Deshabilitar();
    //Limpiar Campos
    //**************
    this.Limpiar();
    //Combos Roles y Modulos
    //**********************
    this.ComboRoles();
    this.ComboModulos();
}//GEN-LAST:event_btnNuevoActionPerformed
private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    //Validamos Campos
    //****************
    if (this.cboRoles.getSelectedItem().equals("")){
        MENSAJE = "Seleccione Rol...!";
        JOptionPane.showMessageDialog(null, MENSAJE);
        this.cboRoles.requestFocus();
    } else if (this.cboModulos.getSelectedItem().equals("")){
        MENSAJE = "Seleccione Modulo...!";
        JOptionPane.showMessageDialog(null, MENSAJE);
        this.cboModulos.requestFocus();
    } else {
        //Creamos Conexion
        //****************
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        //Varialbles
        String SQL = "";
        //Si Accion es Insertar
        //*********************
        if(ACCION.equals("Insertar")){
            //Creamos Sentencia SQL
            //*********************
            SQL += " INSERT INTO accesos ";
            SQL += " (id_rol, id_modulo) ";
            SQL += " VALUES(?, ?) ";
            //Ejecutamos Mensaje
            //******************
            MENSAJE = "Registro Guardado...!";
            try{
                //Ejecutamos Accion Insertar
                //**************************
                INSERTAR  = cn.prepareStatement(SQL);
                INSERTAR.setInt(1, R);
                INSERTAR.setInt(2, M);
                int n = INSERTAR.executeUpdate();
                if(n == 1){
                    //JOptionPane.showMessageDialog(null, MENSAJE);
                }
            } catch(SQLException ex){
                String mensaje = "";
                switch (ex.getErrorCode()){
                    case 1062:
                        mensaje += "Error\n";
                        mensaje += "Acceso ya Registrado...!";
                        break;
                    default:
                        mensaje += "Error\n";
                        mensaje += "Problemas al Guardar Registro...!";
                        break;
                    }
            JOptionPane.showMessageDialog(null, mensaje, "Error...!", JOptionPane.ERROR_MESSAGE);
            }
        //Si Accion es Modificar
        //**********************
        } else if(ACCION.equals("Modificar")){
            //Creamos Sentencia SQL
            //*********************
            SQL += " UPDATE accesos ";
            SQL += " SET id_rol = ?, ";
            SQL += " id_modulo = ? ";
            SQL += " WHERE id_rol = " + ID1 + " ";
            SQL += " AND  id_modulo = " + ID2;
            //Ejecutamos Mensaje
            //******************
            MENSAJE = "Registro Actualizado...!";
            try{
                //Ejecutamos Accion Modificar
                //***************************
                MODIFIACR = cn.prepareStatement(SQL);
                MODIFIACR.setInt(1, R);
                MODIFIACR.setInt(2, M);
                int n = MODIFIACR.executeUpdate();
                if(n == 1){
                    //JOptionPane.showMessageDialog(null, MENSAJE);
                }
            } catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex);
            }
        }
        //Cargamos tabla
        //**************
        this.CargarTabla("");
        //Habilitamos Objetos
        //*******************
        this.Habilitar();
        //Limpiamos Campos
        //****************
        this.Limpiar();
        //Colocamos Tamaño al Tabla
        //*************************
        this.TamañoTabla();
        }
}//GEN-LAST:event_btnGuardarActionPerformed
private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
    //Habilitamos Objetos
    //*******************
    this.Habilitar();
    //Limpiamos Campos
    //****************
    this.Limpiar();
}//GEN-LAST:event_btnCancelarActionPerformed

private void tblAccesosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAccesosMouseClicked
    //Variables
    //*********
    int filasel;
    String id1, id2;

    try {
        filasel = tblAccesos.getSelectedRow();
        if(filasel != -1) {
            MODELO = (DefaultTableModel) tblAccesos.getModel();
            id1 = (String) MODELO.getValueAt(filasel, 0);
            id2 = (String) MODELO.getValueAt(filasel, 2);
            this.txtBuscar.setText("");
            Limpiar();
            ComboRoles();
            ComboModulos();
            CargarDatos(id1, id2);
        }
    } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
    }
}//GEN-LAST:event_tblAccesosMouseClicked

private void cboRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboRolesActionPerformed
    //Creamos Conexion
    //****************
    ConexionMySQL mysql = new ConexionMySQL();
    Connection cn = mysql.Conectar();
    //Variable
    //********
    String SQL = "";
    //Creamos Sentencia SQL
    //*********************
    SQL += " SELECT id ";
    SQL += " FROM roles ";
    SQL += " WHERE denominacion = '" + cboRoles.getSelectedItem() + "' " ;
    //Ejecutamos La Sentencia SQL
    //***************************
    try{
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(SQL);
            if(rs.next()){
                R = rs.getInt(1);
            }
    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
    }
}//GEN-LAST:event_cboRolesActionPerformed

private void cboModulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboModulosActionPerformed
    //Creamos Conexion
    //****************
    ConexionMySQL mysql = new ConexionMySQL();
    Connection cn = mysql.Conectar();
    //Variable
    //********
    String SQL = "";
    //Creamos Sentencia SQL
    //*********************
    SQL += " SELECT id ";
    SQL += " FROM modulos ";
    SQL += " WHERE denominacion = '" + cboModulos.getSelectedItem() + "' " ;
    //Ejecutamos La Sentencia SQL
    //***************************
    try{
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(SQL);
            if(rs.next()){
                M = rs.getInt(1);
            }
    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
    }
}//GEN-LAST:event_cboModulosActionPerformed

private void cboRolesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboRolesMouseClicked
    //Limpiamos Combos
    //****************
    this.Limpiar();
    //Cargamos Combos
    //***************
    this.ComboRoles();
    this.ComboModulos();
}//GEN-LAST:event_cboRolesMouseClicked

private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
    //capturamos el valor del Campo
    //*****************************
    String valor = this.txtBuscar.getText();
    //Cargamos la Tabla segun el Valor
    //********************************
    this.CargarTabla(valor);
    //Colocamos el tamaño a la Tabla
    //******************************
    this.TamañoTabla();
}//GEN-LAST:event_txtBuscarKeyPressed

private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
    //Accion Modificar
    //****************
    this.ACCION = "Modificar";
    //Validamos si hay Registro Seleccionado
    //**************************************
    if (cboRoles.getSelectedItem().equals("Seleccione")){
        JOptionPane.showMessageDialog(null, "Seleccione un Registro...!");
    } else{
        //Deshabilatamos Objeto
        //**********************
        this.Deshabilitar();
    }
}//GEN-LAST:event_btnModificarActionPerformed

private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
    //Validamos si hay Registro
    //*************************
    if (this.cboRoles.getSelectedItem().equals("Seleccione")){
        MENSAJE = "Seleccione un Registro...!";
        JOptionPane.showMessageDialog(null, MENSAJE);
    } else{
        //Preguntamos si Deseamos eliminar el registro
        //********************************************
        MENSAJE = "¿Desea Eliminar Este Registro...?";
        int opc = JOptionPane.showConfirmDialog(null,MENSAJE, "Confirmacion...!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        //Si opc es igual a 0
        //*******************
        if (opc == 0){
            //Creamos Conexion
            //****************
            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();
            //Creamos Sentencia SQL
            //*********************
            String SQL = "";
            SQL = "DELETE FROM accesos WHERE id_rol = " + R +" AND id_modulo = " + M + "";
            try{
                //Ejecutamos Accion Eliminar
                //**************************
                ELIMINAR = cn.prepareStatement(SQL);
                ELIMINAR.execute();
                MENSAJE = "Registro Eliminado...!";
                //JOptionPane.showMessageDialog(null, MENSAJE, "AVISO", JOptionPane.INFORMATION_MESSAGE);
                //Limpiamos Combos
                //****************
                this.Limpiar();
                //Cargamos Tabla
                //**************
                this.CargarTabla("");
                //Colocamos Tamaño a la Tabla
                //***************************
                this.TamañoTabla();
            } catch(SQLException ex){
                String mensaje = "";
                switch (ex.getErrorCode()){
                    case 1451:
                        mensaje += "Error\n";
                        mensaje += "No Se Puede Eliminar\nTiene Registros Asociados...!";
                        break;
                    default:
                        mensaje += "Error\n";
                        mensaje += "Problemas al Eliminar Registro Registro...!";
                        break;
                    }
                JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
                //Limpiamos Campos
                //****************
                this.Limpiar();
                //Cargamos Tabla
                //**************
                this.CargarTabla("");
                //Colocamos Tamaño a la Tabla
                //***************************
                this.TamañoTabla();
            }
        } else{
            //Limpiamos Combos
            //****************
            this.Limpiar();
            //Cargamos Tabla
            //**************
            this.CargarTabla("");
            //Colocamos el Tamaño a la Tabla
            //******************************
            this.TamañoTabla();
        }
    }
}//GEN-LAST:event_btnEliminarActionPerformed

private void cboModulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboModulosMouseClicked
        cboModulos.removeAllItems();
        ComboModulos();
}//GEN-LAST:event_cboModulosMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox cboModulos;
    private javax.swing.JComboBox cboRoles;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_modulo;
    private javax.swing.JLabel lbl_rol;
    private javax.swing.JPanel pnl_marco;
    private javax.swing.JTable tblAccesos;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}
