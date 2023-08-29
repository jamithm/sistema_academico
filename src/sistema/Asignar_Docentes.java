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
public class Asignar_Docentes extends javax.swing.JInternalFrame {
    DefaultTableModel modelo;
    PreparedStatement insertar;
    PreparedStatement modificar;
    String accion = "";
    int a_e;
    int doc;
    int gra;
    int sec;
    int mat;
    Acceso ac;

    /** Creates new form Usuarios */
    public Asignar_Docentes() {
        initComponents();
        Habilitar();
        Limpiar();
        CargarTabla("");
        TamañoTabla();
    }

    void TamañoTabla(){
        TableColumn column = null;
        column = tblAsignacion.getColumnModel().getColumn(0);
        column.setPreferredWidth(120);
        column = tblAsignacion.getColumnModel().getColumn(1);
        column.setPreferredWidth(180);
        column = tblAsignacion.getColumnModel().getColumn(2);
        column.setPreferredWidth(180);
        column = tblAsignacion.getColumnModel().getColumn(3);
        column.setPreferredWidth(150);
        column = tblAsignacion.getColumnModel().getColumn(4);
        column.setPreferredWidth(120);
        column = tblAsignacion.getColumnModel().getColumn(5);
        column.setPreferredWidth(120);
    }

    void Habilitar(){
        btnNuevo.setEnabled(true);
        btnGuardar.setEnabled(false);
        btnModificar.setEnabled(true);
        btnEliminar.setEnabled(true);
        btnCancelar.setEnabled(false);
        btnCerrar.setEnabled(true);
        txtCodigo.setEnabled(false);
        cboPeriodo.setEnabled(false);
        cboDocentes.setEnabled(false);
        cboGrados.setEnabled(false);
        cboSecciones.setEnabled(false);
        cboMaterias.setEnabled(false);
        txtBuscar.setEnabled(true);
        tblAsignacion.setEnabled(true);
    }

    void Deshabilitar(){
        btnNuevo.setEnabled(false);
        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnCancelar.setEnabled(true);
        btnCerrar.setEnabled(false);
        txtCodigo.setEnabled(false);
        cboPeriodo.setEnabled(true);
        cboDocentes.setEnabled(true);
        cboGrados.setEnabled(true);
        cboSecciones.setEnabled(true);
        cboMaterias.setEnabled(true);
        txtBuscar.setEnabled(false);
        tblAsignacion.setEnabled(false);
        txtCodigo.requestFocus();
    }

    void Limpiar(){
        txtCodigo.setText("");
        cboPeriodo.removeAllItems();
        cboDocentes.removeAllItems();
        cboGrados.removeAllItems();
        cboSecciones.removeAllItems();
        cboMaterias.removeAllItems();
    }

    void ComboPeriodos(){
        cboPeriodo.addItem("");
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT * FROM a_escolar";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()){
                cboPeriodo.addItem(rs.getObject(2));
            }
        } catch (SQLException  ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void ComboDocentes(){
        cboDocentes.addItem("");
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT * FROM docentes";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()){
                cboDocentes.addItem(rs.getObject(2) + " " + rs.getObject(3));
            }
        } catch (SQLException  ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void ComboGrados(){
        cboGrados.addItem("");
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT * FROM grados";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()){
                cboGrados.addItem(rs.getObject(2));
            }
        } catch (SQLException  ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void ComboSecciones(){
        cboSecciones.addItem("");
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT * FROM secciones";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()){
                cboSecciones.addItem(rs.getObject(2));
            }
        } catch (SQLException  ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void ComboMaterias(){
        cboMaterias.addItem("");
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT * FROM materias WHERE id_grado = " + gra;

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while (rs.next()){
                cboMaterias.addItem(rs.getObject(2));
            }
        } catch (SQLException  ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }


    void CargarTabla(String valor){
        String [] titulos = {"Codigo", "Periodo", "Docente", "Grado", "Seccion", "Materia"};
        String [] registros = new String [6];

        modelo = new DefaultTableModel(null, titulos);

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";
        sSQL = "SELECT ad.id, ae.denominacion, d.nombres, d.apellidos, g.denominacion, s.denominacion, m.denominacion "
        + "FROM docente_grado as ad, a_escolar as ae, docentes as d, grados as g, secciones as s, materias as m "
        + "WHERE ad.a_esc = ae.id and ad.cedula_profesor = d.cedula and ad.grado = g.id and ad.materia = m.id "
        + "and ad.seccion = s.id and d.nombres LIKE '%"+ valor +"%'";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next()){
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                registros[2] = rs.getString(3) + " " + rs.getString(4);
                registros[3] = rs.getString(5);
                registros[4] = rs.getString(6);
                registros[5] = rs.getString(7);
                modelo.addRow(registros);
            }

            tblAsignacion.setModel(modelo);
            Etiqueta();
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    String id_act;
    void CargarDatos(String id){
        String sSQL="";
        String a_escolar = "", docente  = "", grado = "", seccion = "", materia = "";

        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        sSQL = "SELECT ad.id, ae.denominacion, d.nombres, d.apellidos, g.denominacion, s.denominacion, m.denominacion "
        + "FROM docente_grado as ad, a_escolar as ae, docentes as d, grados as g, secciones as s, materias as m "
        + "WHERE ad.a_esc = ae.id and ad.cedula_profesor = d.cedula and ad.grado = g.id and ad.materia = m.id "
        + "and ad.seccion = s.id and ad.id = " + id;

        try
        {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            while(rs.next())
            {
                a_escolar  = rs.getString(2);
                docente = rs.getString(3)+ " " + rs.getString(4);
                grado = rs.getString(5);
                seccion = rs.getString(6);
                materia = rs.getString(7);
            }

            cboPeriodo.setSelectedItem((Object)a_escolar);
            cboDocentes.setSelectedItem((Object)docente);
            cboGrados.setSelectedItem((Object)grado);
            cboSecciones.setSelectedItem((Object)seccion);
            cboMaterias.setSelectedItem((Object)materia);
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
        sSQL = "select count(*) from docente_grado";
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
        txtCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cboPeriodo = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        cboDocentes = new javax.swing.JComboBox();
        cboGrados = new javax.swing.JComboBox();
        cboSecciones = new javax.swing.JComboBox();
        cboMaterias = new javax.swing.JComboBox();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAsignacion = new javax.swing.JTable();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Asignacion de Docentes");
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Asignacion"));

        jLabel1.setText("Codigo:");

        jLabel2.setText("Periodo:");

        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        jLabel4.setText("Docente:");

        jLabel7.setText("Grado:");

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Seccion:");

        cboPeriodo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboPeriodoMouseClicked(evt);
            }
        });
        cboPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPeriodoActionPerformed(evt);
            }
        });

        jLabel12.setText("Materia:");

        cboDocentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboDocentesMouseClicked(evt);
            }
        });
        cboDocentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDocentesActionPerformed(evt);
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

        cboMaterias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboMateriasMouseClicked(evt);
            }
        });
        cboMaterias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMateriasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel7)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboPeriodo, 0, 421, Short.MAX_VALUE)
                    .addComponent(cboDocentes, 0, 421, Short.MAX_VALUE)
                    .addComponent(cboGrados, 0, 421, Short.MAX_VALUE)
                    .addComponent(cboSecciones, 0, 421, Short.MAX_VALUE)
                    .addComponent(cboMaterias, 0, 421, Short.MAX_VALUE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cboDocentes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(cboGrados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboSecciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboMaterias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12)))
                            .addComponent(jLabel7)))
                    .addComponent(jLabel4))
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tblAsignacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblAsignacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAsignacionMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAsignacion);

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
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addComponent(jScrollPane1))
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
                    .addComponent(jLabel6)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGuardar)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnCerrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnModificar, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        accion = "Modificar";
        if (txtCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Modificar...!");
        } else{
            Deshabilitar();
            txtCodigo.setEditable(false);
            cboPeriodo.requestFocus();
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
        if (txtCodigo.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Eliminar...!");
        } else {

            int opcion = JOptionPane.showConfirmDialog(null,"¿Desea eliminar este registro...?", "Confirmación...!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (opcion == 0){
                ConexionMySQL mysql = new ConexionMySQL();
                Connection cn = mysql.Conectar();

                try{
                    PreparedStatement pst = cn.prepareStatement("DELETE FROM docente_grado WHERE id = ?");
                    pst.setInt(1, new Integer(txtCodigo.getText()));
                    pst.execute();
                    //JOptionPane.showMessageDialog(null, "Registro Eliminado...!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    Limpiar();
                    CargarTabla("");
                    TamañoTabla();
                } catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error de Borrado: "+ ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else{
                Limpiar();
                CargarTabla("");
                TamañoTabla();
            }
        }
}//GEN-LAST:event_btnEliminarActionPerformed

    private void cboPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPeriodoActionPerformed
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";

        sSQL = "SELECT * FROM a_escolar WHERE denominacion ='" + cboPeriodo.getSelectedItem() + "'" ;

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);


            if(rs.next()){
                a_e = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
}//GEN-LAST:event_cboPeriodoActionPerformed

    private void cboDocentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDocentesActionPerformed
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";

        sSQL = "SELECT * FROM docentes WHERE CONCAT(nombres, ' ',apellidos)  ='" + cboDocentes.getSelectedItem() + "'" ;

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);


            if(rs.next()){
                doc = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
}//GEN-LAST:event_cboDocentesActionPerformed

    private void tblAsignacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAsignacionMouseClicked
        int filasel;
        String id;

        try {
            filasel = tblAsignacion.getSelectedRow();
            if(filasel == -1) {
                //JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila...!");
            } else {
                modelo = (DefaultTableModel) tblAsignacion.getModel();
                id = (String) modelo.getValueAt(filasel, 0);
                txtBuscar.setText("");
                cboPeriodo.removeAllItems();
                cboDocentes.removeAllItems();
                cboGrados.removeAllItems();
                cboSecciones.removeAllItems();
                cboMaterias.removeAllItems();
                ComboPeriodos();
                ComboDocentes();
                ComboGrados();
                ComboSecciones();
                ComboMaterias();
                CargarDatos(id);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
}//GEN-LAST:event_tblAsignacionMouseClicked

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        String valor = txtBuscar.getText();
        CargarTabla(valor);
        TamañoTabla();
}//GEN-LAST:event_txtBuscarKeyPressed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        accion = "Insertar";
        Deshabilitar();
        txtCodigo.setEditable(true);
        Limpiar();
        ComboPeriodos();
        ComboDocentes();
        ComboGrados();
        ComboSecciones();
}//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (cboPeriodo.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione Periodo...!");
            cboPeriodo.requestFocus();
        } else if (cboDocentes.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione Docente...!");
            cboDocentes.requestFocus();
        } else if (cboGrados.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione Grado...!");
            cboGrados.requestFocus();
        } else if (cboSecciones.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione Seccion...!");
            cboSecciones.requestFocus();
        } else if (cboMaterias.getSelectedItem().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione Materia...!");
            cboMaterias.requestFocus();
        } else {

            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();

            String sSQL = "";
            String  mensaje = "";

            if(accion.equals("Insertar")){
                sSQL = "INSERT INTO docente_grado VALUES(null, ?, ?, ?, ?, ?)";
                mensaje = "Registro Guardado...!";

                try{
                    insertar = cn.prepareStatement(sSQL);
                    insertar.setInt(1, a_e);
                    insertar.setInt(2, doc);
                    insertar.setInt(3, gra);
                    insertar.setInt(4, sec);
                    insertar.setInt(5, mat);
  
                    int n = insertar.executeUpdate();
                    if(n == 1){
                        //JOptionPane.showMessageDialog(null, mensaje);

                    }
                } catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, ex);
                }


            } else if(accion.equals("Modificar")){
                sSQL = "UPDATE docente_grado SET a_esc = ?,"
                        + "cedula_profesor = ?,"
                        + "grado = ?,"
                        + "seccion = ?,"
                        + "materia = ? "
                        + "WHERE id = " + id_act;
                mensaje = "Registro Actualizado...!";

                try{
                    modificar = cn.prepareStatement(sSQL);
                    modificar.setInt(1, a_e);
                    modificar.setInt(2, doc);
                    modificar.setInt(3, gra);
                    modificar.setInt(4, sec);
                    modificar.setInt(5, mat);


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

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))){
            getToolkit().beep();
            evt.consume();
        }
        String Caracteres = txtCodigo.getText();
        if(Caracteres.length()>=8){
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        //Centramos Formulario
        //********************
        Forms f = new Forms(this);
        f.setCenterFrame();
    }//GEN-LAST:event_formComponentShown

    private void cboDocentesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboDocentesMouseClicked
        cboDocentes.removeAllItems();
        ComboDocentes();
    }//GEN-LAST:event_cboDocentesMouseClicked

    private void cboGradosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboGradosMouseClicked
        cboGrados.removeAllItems();
        cboMaterias.removeAllItems();
        ComboGrados();
    }//GEN-LAST:event_cboGradosMouseClicked

    private void cboGradosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGradosActionPerformed
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";

        sSQL = "SELECT * FROM grados WHERE denominacion ='" + cboGrados.getSelectedItem() + "'" ;

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);


            if(rs.next()){
                gra = rs.getInt(1);
                cboMaterias.removeAllItems();
                ComboMaterias();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_cboGradosActionPerformed

    private void cboSeccionesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboSeccionesMouseClicked
        cboSecciones.removeAllItems();
        ComboSecciones();
    }//GEN-LAST:event_cboSeccionesMouseClicked

    private void cboSeccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSeccionesActionPerformed
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";

        sSQL = "SELECT * FROM secciones WHERE denominacion ='" + cboSecciones.getSelectedItem() + "'" ;

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);


            if(rs.next()){
                sec = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_cboSeccionesActionPerformed

    private void cboMateriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboMateriasMouseClicked
        cboMaterias.removeAllItems();
        ComboMaterias();
    }//GEN-LAST:event_cboMateriasMouseClicked

    private void cboMateriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMateriasActionPerformed
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

        String sSQL = "";

        sSQL = "SELECT * FROM materias WHERE denominacion ='" + cboMaterias.getSelectedItem() + "'" ;

        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);


            if(rs.next()){
                mat = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_cboMateriasActionPerformed

    private void cboPeriodoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboPeriodoMouseClicked
        cboPeriodo.removeAllItems();
        ComboPeriodos();
    }//GEN-LAST:event_cboPeriodoMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox cboDocentes;
    private javax.swing.JComboBox cboGrados;
    private javax.swing.JComboBox cboMaterias;
    private javax.swing.JComboBox cboPeriodo;
    private javax.swing.JComboBox cboSecciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblAsignacion;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables

}
