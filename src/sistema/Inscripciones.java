/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Matriculas.java
 *
 * Created on 08/01/2012, 12:29:07 AM
 */

package sistema;

import Clases.ConexionMySQL;
import Clases.Forms;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Administrador
 */
public class Inscripciones extends javax.swing.JInternalFrame {
    DefaultTableModel modelo;
    PreparedStatement insertar;
    PreparedStatement modificar;
    //Reporte_Ficha  ficha;

    String accion = "";
    String cedula;
    String turno;
    String periodo;
    String grado;
    String seccion;
    public static String CEDULA1;

    /** Creates new form Matriculas */
    public Inscripciones() {
        initComponents();
        Habilitar();
        CargarTabla("");
        TamañoTabla();
        Limpiar();
        LimpiarCombo();
    }

     void TamañoTabla(){
        TableColumn column = null;
        column = tblMatriculas.getColumnModel().getColumn(0);
        column.setPreferredWidth(80);
        column = tblMatriculas.getColumnModel().getColumn(1);
        column.setPreferredWidth(130);
        column = tblMatriculas.getColumnModel().getColumn(2);
        column.setPreferredWidth(230);
        column = tblMatriculas.getColumnModel().getColumn(3);
        column.setPreferredWidth(100);
        column = tblMatriculas.getColumnModel().getColumn(4);
        column.setPreferredWidth(100);
        column = tblMatriculas.getColumnModel().getColumn(5);
        column.setPreferredWidth(100);
    }

    void Habilitar(){
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnImprimir.setEnabled(true);
        btnCerrar.setEnabled(true);
        btnBuscar.setEnabled(false);
        txtCodigo.setEnabled(false);
        txtCedula.setEnabled(false);
        txtAlumno.setEnabled(false);
        cboPeriodos.setEnabled(false);
        cboGrados.setEnabled(false);
        cboSecciones.setEnabled(false);
    }

    void Deshabilitar(){
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnImprimir.setEnabled(false);
        btnCerrar.setEnabled(false);
        btnBuscar.setEnabled(true);
        txtCodigo.setEditable(false);
        txtCedula.setEnabled(true);
        txtAlumno.setEnabled(true);
        cboPeriodos.setEnabled(true);
        cboGrados.setEnabled(true);
        cboSecciones.setEnabled(true);
        btnBuscar.requestFocus();
    }

    void Limpiar(){
        txtCodigo.setText("");
        txtCedula.setText("");
        txtAlumno.setText("");
        
    }

    void LimpiarCombo(){
        cboPeriodos.removeAllItems();
        cboGrados.removeAllItems();
        cboSecciones.removeAllItems();
    }

    void CargarTabla(String valor){
        String [] titulos = {"Codigo", "Cedula", "Alumno", "Periodo", "Grado", "Seccion"};
        String [] registros = new String [6];

        modelo = new DefaultTableModel(null, titulos);

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL += "SELECT i.id, a.cedula_escolar, a.nombres, a.apellidos, ";
        sSQL += "ae.denominacion, g.denominacion, s.denominacion ";
        sSQL += "FROM inscripciones as i, grados as g, secciones as s, a_escolar as ae, alumnos as a ";
        sSQL += "WHERE i.cedula_escolar = a.cedula_escolar and i.a_escolar = ae.id ";
        sSQL += "and i.grado = g.id and i.seccion = s.id ";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next()){
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                registros[2] = rs.getString(3) + " " + rs.getString(4) ;
                registros[3] = rs.getString(5);
                registros[4] = rs.getString(6);
                registros[5] = rs.getString(7);

                modelo.addRow(registros);
            }

            tblMatriculas.setModel(modelo);
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    String id_act;
    void CargarDatos(String id){
        String sSQL="";
        String ced="", alumno="", per="", gra="", sec="", lit="", fec="", rep="";

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        sSQL += "SELECT i.id, a.cedula_escolar, a.nombres, a.apellidos, ";
        sSQL += "ae.denominacion, g.denominacion, s.denominacion ";
        sSQL += "FROM inscripciones as i, grados as g, secciones as s, a_escolar as ae, alumnos as a ";
        sSQL += "WHERE i.cedula_escolar = a.cedula_escolar and i.a_escolar = ae.id ";
        sSQL += "and i.grado = g.id and i.seccion = s.id and i.id = " + id;

        try
        {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next())
            {
                ced  = rs.getString(2);
                alumno = rs.getString(3) + " " + rs.getString(4);
                per = rs.getString(5);
                gra = rs.getString(6);
                sec = rs.getString(7);
            }

            txtCedula.setText(ced);
            txtAlumno.setText(alumno);
            cboPeriodos.setSelectedItem(per);
            cboGrados.setSelectedItem(gra);
            cboSecciones.setSelectedItem(sec);
            id_act = id;
            txtCodigo.setText(id_act);

        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

   void ComboGrados(){
       cboGrados.addItem("");
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT * FROM grados";

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next()){
                cboGrados.addItem(rs.getObject(2));
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void ComboSecciones(){
        cboSecciones.addItem("");
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT * FROM secciones";

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next()){
                cboSecciones.addItem(rs.getObject(2));
            }
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void ComboPeriodos(){
        cboPeriodos.addItem("");
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT * FROM a_escolar";

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next()){
                cboPeriodos.addItem(rs.getObject(2));
            }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cboPeriodos = new javax.swing.JComboBox();
        cboGrados = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jlabel6 = new javax.swing.JLabel();
        cboSecciones = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMatriculas = new javax.swing.JTable();
        txtbuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel1.setText("jLabel1");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setTitle("Inscripciones");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Matricula del Alumno"));

        jLabel2.setText("Alumno:");

        jLabel8.setText("Cedula Escolar:");

        txtCedula.setEditable(false);

        txtAlumno.setEditable(false);

        jLabel9.setText("Codigo:");

        btnBuscar.setText("...");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtAlumno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Inscripcion"));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Periodo:");

        cboPeriodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboPeriodosMouseClicked(evt);
            }
        });
        cboPeriodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPeriodosActionPerformed(evt);
            }
        });

        cboGrados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboGradosMouseClicked(evt);
            }
        });
        cboGrados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGradosActionPerformed(evt);
            }
        });

        jLabel4.setText("Grado:");

        jlabel6.setText("Seccion:");

        cboSecciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboSeccionesMouseClicked(evt);
            }
        });
        cboSecciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSeccionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jlabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboSecciones, 0, 323, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboPeriodos, 0, 323, Short.MAX_VALUE)
                            .addComponent(cboGrados, 0, 323, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cboPeriodos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboGrados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlabel6)
                    .addComponent(cboSecciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tblMatriculas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblMatriculas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMatriculasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMatriculas);

        jLabel7.setText("Buscar Alumno:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_comit.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_remove.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/imprimir.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/application-exit-3.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addGap(5, 5, 5)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (txtCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Eliminar...!");
        } else {

            int opcion = JOptionPane.showConfirmDialog(null,"¿Desea eliminar este registro...?", "Confirmación...!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcion == 0){
                ConexionMySQL mysql = new ConexionMySQL();
                Connection cn = mysql.Conectar();

                try{
                    PreparedStatement pst = cn.prepareStatement("DELETE FROM inscripciones WHERE id = ?");
                    pst.setInt(1, new Integer(txtCodigo.getText()));
                    pst.execute();
                    //JOptionPane.showMessageDialog(null, "Registro Borrado...!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    Limpiar();
                    CargarTabla("");
                    TamañoTabla();
                    LimpiarCombo();
                } catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error de Borrado: "+ ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else{
                Limpiar();
                CargarTabla("");
                TamañoTabla();
                LimpiarCombo();
            }
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtCedula.getText().equals("") ){
            JOptionPane.showMessageDialog(null, "Buscar un Alumno...!");
            txtCedula.requestFocus();
        } else if (cboPeriodos.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Periodo...!");
            cboPeriodos.requestFocus();
        } else if (cboGrados.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Grado...!");
            cboGrados.requestFocus();
        } else if (cboSecciones.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Seleccione una Seccion...!");
            cboSecciones.requestFocus();
        } else {

            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();

            String sSQL = "";
            String  mensaje = "";
            String CED = txtCedula.getText();
           
                if(accion.equals("Insertar")){
                    try{
                        sSQL = "SELECT id FROM inscripciones WHERE a_escolar = " + periodo + " and cedula_escolar = " + CED + "";
                        Statement st = cn.createStatement();
                        ResultSet rs = st.executeQuery(sSQL);


                        if (rs.last()){
                            JOptionPane.showMessageDialog(null, "Alumno ya inscrito en el \n Periodo: " + " " + cboPeriodos.getSelectedItem());
                        }
                        else{
                            sSQL = "INSERT INTO inscripciones VALUES(null, ?, ?, ?, ?)";
                            mensaje = "Registro Grabado...!";

                            try{
                                insertar = cn.prepareStatement(sSQL);
                                insertar.setString(1, CED);
                                insertar.setString(2, periodo);
                                insertar.setString(3, grado);
                                insertar.setString(4, seccion);
                                int n = insertar.executeUpdate();
                                if(n == 1){
                                    //JOptionPane.showMessageDialog(null, mensaje);
                                }
                            }
                            catch(SQLException ex){
                                JOptionPane.showMessageDialog(null, ex);
                            }
                        }

                    }
                    catch(SQLException ex){
                        JOptionPane.showMessageDialog(null, ex);
                    }
                    
                } else if(accion.equals("Modificar")){
                    sSQL = "UPDATE inscripciones "
                            + "SET cedula_escolar = ?, "
                            + "a_escolar = ?, "
                            + "grado = ?, "
                            + "seccion = ? "
                            + " WHERE id = " + id_act;
                            mensaje = "Registro Actualizado...!";

                    try{
                        modificar = cn.prepareStatement(sSQL);
                        modificar.setString(1, CED);
                        modificar.setString(2, periodo);
                        modificar.setString(3, grado);
                        modificar.setString(4, seccion);
                        int n = modificar.executeUpdate();
                        if(n == 1){
                            //JOptionPane.showMessageDialog(null, mensaje);

                        }
                    }
                    catch(SQLException ex){
                        JOptionPane.showMessageDialog(null, ex);
                    }
                }              
            CargarTabla("");
            Habilitar();
            Limpiar();
            TamañoTabla();
            LimpiarCombo();
        }
}//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        accion = "Modificar";
        if (txtCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Modificar...!");
        } else{
            Deshabilitar();
        }
}//GEN-LAST:event_btnModificarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        accion = "Insertar";
        Deshabilitar();
        Limpiar();
        LimpiarCombo();
        ComboPeriodos();
        ComboGrados();
        ComboSecciones();
}//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        Habilitar();
        Limpiar();
        LimpiarCombo();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
}//GEN-LAST:event_btnCerrarActionPerformed

    private void cboSeccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSeccionesActionPerformed
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        String codseccion = "";

        sSQL = "SELECT * FROM secciones WHERE denominacion ='" + cboSecciones.getSelectedItem() + "'" ;

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);


            if(rs.next()){
                codseccion = rs.getString(1);
                seccion = codseccion;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_cboSeccionesActionPerformed

    private void cboPeriodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPeriodosActionPerformed
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        String codae = "";

        sSQL = "SELECT * FROM a_escolar WHERE denominacion ='" + cboPeriodos.getSelectedItem() + "'" ;

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);


            if(rs.next()){
                codae = rs.getString(1);
                periodo = codae;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_cboPeriodosActionPerformed

    private void tblMatriculasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMatriculasMouseClicked
        LimpiarCombo();
        int filasel;
        String id;

        try {
            filasel = tblMatriculas.getSelectedRow();
            if(filasel == -1) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila");
            } else {
                modelo = (DefaultTableModel) tblMatriculas.getModel();
                id = (String) modelo.getValueAt(filasel, 0);
                ComboPeriodos();
                ComboGrados();
                ComboSecciones();
                CargarDatos(id);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_tblMatriculasMouseClicked

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        //Centramos Formulario
        //********************
        Forms f = new Forms(this);
        f.setCenterFrame();
    }//GEN-LAST:event_formComponentShown

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        //Validar
        //*******
        if (txtCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Registro...!");
        }else{
            //Ejecutar Ficha de Inscripcion
            //*****************************
            //ficha.Ejecutar(txtCodigo.getText());
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void cboSeccionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboSeccionesMouseClicked
        cboSecciones.removeAllItems();
        ComboSecciones();
    }//GEN-LAST:event_cboSeccionesMouseClicked

    private void cboPeriodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboPeriodosMouseClicked
        cboPeriodos.removeAllItems();
        ComboPeriodos();
    }//GEN-LAST:event_cboPeriodosMouseClicked

    private void cboGradosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboGradosMouseClicked
        cboGrados.removeAllItems();
        cboSecciones.removeAllItems();
        ComboGrados();
    }//GEN-LAST:event_cboGradosMouseClicked

    private void cboGradosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGradosActionPerformed
        cboSecciones.removeAllItems();
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        String cod = "";

        sSQL = "SELECT * FROM grados WHERE denominacion ='" + cboGrados.getSelectedItem() + "'" ;

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);


            if(rs.next()){
                cod = rs.getString(1);
                grado = cod;
                ComboSecciones();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_cboGradosActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        Buscar_Alumno frm = new Buscar_Alumno();
        Principal.PANEL.add(frm);
        frm.toFront();
        frm.show();
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox cboGrados;
    private javax.swing.JComboBox cboPeriodos;
    private javax.swing.JComboBox cboSecciones;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel jlabel6;
    private javax.swing.JTable tblMatriculas;
    public static final javax.swing.JTextField txtAlumno = new javax.swing.JTextField();
    public static final javax.swing.JTextField txtCedula = new javax.swing.JTextField();
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtbuscar;
    // End of variables declaration//GEN-END:variables

}
