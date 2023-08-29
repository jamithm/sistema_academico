/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Alumnos.java
 *
 * Created on 07/01/2012, 03:01:42 PM
 */

package sistema;

import Clases.Forms;
import Clases.ConexionMySQL;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrador
 */
public class Alumnos extends javax.swing.JInternalFrame {
    //Modelo de la Tabla
    //******************
    DefaultTableModel modelo;
    //Variables
    //*********
    String cedula;
    String accion  = "";
    String id_act;
    int v = 1;
    //Acciones
    //********
    PreparedStatement insertar;
    PreparedStatement modificar;
    public static String CEDULA_ESCOLAR;


    /** Creates new form Alumnos */
    public Alumnos() {
        initComponents();
        //Habilitar Objetos
        //*****************
        this.Habilitar();
        //Cargar Tabla
        //************
        this.CargarTabla("");
        //Deshabilitar Cajas
        //******************
        Cajas(false);
        //Limpiar Campos
        //**************
        Limpiar();
        //Limpiar Combos
        //**************
        LimpiarCombos();
    }

    private void CargarTabla(String valor){
        //Arreglo para el Titulo
        //**********************
        String [] titulos = {"Cedula Escolar", "Nombres", "Apellidos", "Sexo", "Lugar de Nac", "Fec Nac", "Edad", "Representante"};
        //Arreglo para los Registros
        String [] registros = new String [8];
        //Asignamos el Modelo de la Tabla
        //*******************************
        modelo = new DefaultTableModel(null, titulos);
        //Creamos Conexion
        //****************
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        //Creamos Sentencia SQL
        //*********************
        String sSQL = "";
        sSQL = "SELECT a.cedula_escolar, a.nombres, a.apellidos, a.sexo, a.lugar_nac, "
        + "date_format(a.fec_nac, '%d/%m/%Y') as fecha, a.edad, r.cedula, r.nombres, "
        + "r.apellidos "
        +"FROM alumnos as a, representantes as r "
        + "WHERE a.cedula_representante = r.cedula and CONCAT(a.nombres, ' ',a.apellidos) LIKE '%"+valor+"%'";
        //Ejecutamos la Senetncia
        //***********************
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while(rs.next()){
                String var = rs.getString(8);
                if (var.length() == 7) {
                    registros[0] = rs.getString(1);
                    registros[1] = rs.getString(2);
                    registros[2] = rs.getString(3);
                    registros[3] = rs.getString(4);
                    registros[4] = rs.getString(5);
                    registros[5] = rs.getString(6);
                    registros[6] = rs.getString(7);
                    registros[7] = rs.getString(9) + " " + rs.getString(10);
                
                }else {
                    registros[0] = rs.getString(1);
                    registros[1] = rs.getString(2);
                    registros[2] = rs.getString(3);
                    registros[3] = rs.getString(4);
                    registros[4] = rs.getString(5);
                    registros[5] = rs.getString(6);
                    registros[6] = rs.getString(7);
                    registros[7] = rs.getString(9) + " " + rs.getString(10);
                }
                modelo.addRow(registros);
            }
            //Lenamos la Tabla
            //****************
            tblAlumnos.setModel(modelo);
            //Registros
            //*********
            Etiqueta();
        }
        catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //Metodo para habilitar Objetos
    //*****************************
    private void Habilitar(){
       btnNuevo.setEnabled(true);
       btnGuardar.setEnabled(false);
       btnModificar.setEnabled(true);
       btnEliminar.setEnabled(true);
       btnCancelar.setEnabled(false);
       btnCerrar.setEnabled(true);
       btnBuscar.setEnabled(false);
       btnGenerar.setEnabled(false);
       txtCedRep.setEnabled(false);
       txtRepresentante.setEnabled(false);
       txtBuscar.setEnabled(true);
       tblAlumnos.setEnabled(true);
    }
    //Metodo para deshabilitar Objetos
    //********************************
    private void Deshabilitar(){
       btnNuevo.setEnabled(false);
       btnGuardar.setEnabled(true);
       btnModificar.setEnabled(false);
       btnEliminar.setEnabled(false);
       btnCancelar.setEnabled(true);
       btnCerrar.setEnabled(false);
       btnBuscar.setEnabled(true);
       txtCedRep.setEnabled(true);
       txtRepresentante.setEnabled(true);
       txtBuscar.setEnabled(false);
       tblAlumnos.setEnabled(false);
       txtCedRep.requestFocus();
    }

    //Metodo para los Campos
    //**********************
    private void Cajas(Boolean v){
        txtCedula.setEnabled(v);
        txtNombre1.setEnabled(v);
        txtApellido1.setEnabled(v);
        cboSexo.setEnabled(v);
        txtLugar.setEnabled(v);
        txtFecha.setEnabled(v);
        txtEdad.setEnabled(v);
        btnGenerar.setEnabled(v);
    }

    //Metodo para Limpiar Campos
    //**************************
    private void Limpiar(){
        txtCedRep.setText("");
        txtRepresentante.setText("");
        txtCedula.setText("");
        txtNombre1.setText("");
        txtApellido1.setText("");
        txtLugar.setText("");
        txtFecha.setDate(null);
        txtEdad.setText("");
    }

    //Metodos para Limpiar combos
    //***************************
    private void LimpiarCombos(){
        cboSexo.removeAllItems();
    }

    //Metodo para Llenar Combos Estaticos
    private void Combos(){
        cboSexo.addItem("");
        cboSexo.addItem("Masculino");
        cboSexo.addItem("Femenino");
    }

    //Metodo para Cargar Datos
    //************************
    private void CargarDatos(String id){
        //Variables
        //*********
        String sSQL="";
        String ced_rep="", nombres="", apellidos="", sexo="", lugar="", fecha="", edad="", rep="";
        //Creamos Conexion
        //****************
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        //Creamos Sentencia SQL
        //*********************
        sSQL = "SELECT a.cedula_escolar, a.nombres, a.apellidos, a.sexo, a.lugar_nac, "
        + "date_format(a.fec_nac, '%d/%m/%Y') as fecha, a.edad, r.cedula, r.nombres, "
        + "r.apellidos "
        +"FROM alumnos as a, representantes as r "
        + "WHERE a.cedula_representante = r.cedula and a.cedula_escolar = " + id; 
        //Ejecutamos Sentencia
        //********************
        try
        {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            while(rs.next())
            {
                String var = rs.getString(8);
                if (var.length() == 7){
                    nombres = rs.getString(2);
                    apellidos = rs.getString(3);
                    sexo = rs.getString(4);
                    lugar = rs.getString(5);
                    fecha = rs.getString(6);
                    edad = rs.getString(7);
                    ced_rep = "0" + rs.getString(8);
                    rep = rs.getString(9) + " " + rs.getString(10);
                }else {
                    nombres = rs.getString(2);
                    apellidos = rs.getString(3);
                    sexo = rs.getString(4);
                    lugar = rs.getString(5);
                    fecha = rs.getString(6);
                    edad = rs.getString(7);
                    ced_rep = rs.getString(8);
                    rep = rs.getString(9) + " " + rs.getString(10);
                }
            }      
            txtNombre1.setText(nombres);
            txtApellido1.setText(apellidos);
            cboSexo.setSelectedItem((Object)sexo);
            txtLugar.setText(lugar);
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");
            Date dato = null;
            try {
                dato = formatoDelTexto.parse(fecha);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
            txtFecha.setDate(dato);
            txtEdad.setText(edad);
            txtCedRep.setText(ced_rep);
            txtRepresentante.setText(rep);
            id_act = id;
            txtCedula.setText(id_act);
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //Metodo para Llevar Registros
    //****************************
    private void Etiqueta(){
        //Creamos Conexion
        //****************
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        //Variables
        //*********
        String total = "";
        String sSQL = "";
        //Creamos Sentencia SQL
        //*********************
        sSQL = "select count(*) from alumnos";
        try{
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            if (rs.last()){
                total = rs.getString(1);
            }
            jLabel19.setText(total);
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

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Panel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtRepresentante = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        txtCedRep = new javax.swing.JTextField();
        Panel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNombre1 = new javax.swing.JTextField();
        cboSexo = new javax.swing.JComboBox();
        txtLugar = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtApellido1 = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        btnGenerar = new javax.swing.JButton();
        txtFecha = new com.toedter.calendar.JDateChooser();
        txtEdad = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblAlumnos = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setTitle("Registro de Alumnos");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        Panel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel1.setText("Cedula del Representante:");

        jLabel2.setText("Representante:");

        txtRepresentante.setEditable(false);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        txtCedRep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedRepKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout Panel1Layout = new javax.swing.GroupLayout(Panel1);
        Panel1.setLayout(Panel1Layout);
        Panel1Layout.setHorizontalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCedRep, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        Panel1Layout.setVerticalGroup(
            Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(txtCedRep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnBuscar)
                .addComponent(jLabel2)
                .addComponent(txtRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Panel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel3.setText("Cedula Escolar:");

        jLabel4.setText("Nombres:");

        jLabel6.setText("Fec-Nac:");

        jLabel7.setText("Sexo:");

        jLabel8.setText("Lugar de Nac:");

        jLabel9.setText("Edad:");

        jLabel20.setText("Apellidos:");

        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCedulaKeyTyped(evt);
            }
        });

        btnGenerar.setText("Generar Cedula Escolar");
        btnGenerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarActionPerformed(evt);
            }
        });

        txtEdad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEdadKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout Panel2Layout = new javax.swing.GroupLayout(Panel2);
        Panel2.setLayout(Panel2Layout);
        Panel2Layout.setHorizontalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtNombre1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, Panel2Layout.createSequentialGroup()
                        .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGenerar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(cboSexo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addGroup(Panel2Layout.createSequentialGroup()
                        .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtApellido1)
                        .addComponent(txtLugar, javax.swing.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE))
                    .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Panel2Layout.setVerticalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtNombre1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(txtApellido1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_add.png"))); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnNuevo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/guardar-documento-icono-7840-48.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnGuardar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_update.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnModificar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db_remove.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEliminar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCancelar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/application-exit-3.png"))); // NOI18N
        btnCerrar.setText("Cerrar");
        btnCerrar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCerrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel21.setText("Buscar:");

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        tblAlumnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblAlumnos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblAlumnos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblAlumnos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAlumnosMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblAlumnos);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel18.setText("Total ->");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 12));
        jLabel19.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(647, Short.MAX_VALUE)
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCerrar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnCancelar)
                    .addComponent(btnCerrar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        //Limpiar Campos
        //**************
        this.Limpiar();
        //Limpiamos Combo
        //***************
        this.LimpiarCombos();
        //Accion Insertar
        //***************
        this.accion = "Insertar";
        //Deshabilitar Objetos
        //********************
        this.Deshabilitar();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //Habilitar Objetos
        //*****************
        this.Habilitar();
        //Limpiar Campos
        //**************
        this.Limpiar();
        //Limpiar Combos
        //**************
        this.LimpiarCombos();
        //cajas False
        //***********
        this.Cajas(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        //Creamos Conexion 
        //****************
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
        //Variables
        //*********
        String sSQL = "";
        String ced_rep = "", repres = "";
        //Creamos sentencia SQL
        //*********************
        sSQL = "SELECT * FROM representantes WHERE cedula = '" + txtCedRep.getText() + "'";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);
            if (rs.last()){
                String var = rs.getString(1);
                if (var.length() == 7){
                    ced_rep = "0" + rs.getString(1);
                    repres  = rs.getString(2) + " "  + rs.getString(3);
                }else {
                    ced_rep = rs.getString(1);
                    repres  = rs.getString(2) + " "  + rs.getString(3);
                }
                txtCedRep.setText(ced_rep);
                txtRepresentante.setText(repres);
                Cajas(true);
                Combos();
                txtCedula.requestFocus();

            }
            else{
                JOptionPane.showMessageDialog(null, "Representante no Existe...!");
                txtCedRep.setText("");
                txtCedRep.requestFocus();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        Limpiar();
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
                    PreparedStatement pst = cn.prepareStatement("DELETE FROM alumnos WHERE cedula_escolar = ?");
                    pst.setString(1, txtCedula.getText());
                    pst.execute();
                    //JOptionPane.showMessageDialog(null, "Registro Eliminado...!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                    Limpiar();
                    LimpiarCombos();
                    CargarTabla("");
                } catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "No se Puede Eliminar \n Tiene Registros Asosiados...!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } else{
                Limpiar();
                LimpiarCombos();
                CargarTabla("");
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        accion = "Modificar";
        if (txtCedula.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Seleccione un Registro para Modificar...!");
        } else{
            Deshabilitar();
            Cajas(true);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
            if (txtNombre1.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese Primer Nombre...!");
                txtNombre1.requestFocus();
            } else if (txtApellido1.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese Primer Apellido...!");
                txtApellido1.requestFocus();
            } else if (cboSexo.getSelectedItem().equals("")){
                JOptionPane.showMessageDialog(null, "Seleccione Sexo...!");
                txtLugar.requestFocus();
            } else if (txtLugar.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese Lugar de Nacimineto...!");
                txtLugar.requestFocus();
            } else if (txtEdad.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese la Edad del Alumno...!");
                txtEdad.requestFocus();
            } else {


            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();

            String ced_rep, nombre1, nombre2, apellido1, apellido2, fecha, fec, sexo, lugar, edad;
            ced_rep = txtCedRep.getText();
            nombre1 = txtNombre1.getText();
            apellido1 = txtApellido1.getText();
            sexo = cboSexo.getSelectedItem().toString();
            lugar = txtLugar.getText();
            edad = txtEdad.getText();
            SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyy-MM-dd"); // formato de fecha año mes dia
            fecha = formatoDeFecha.format(txtFecha.getDate());


            if (fecha.equals(null)){
                JOptionPane.showMessageDialog(null, "Ingrese Fecha de Nacimiento...!");
                txtFecha.requestFocus();
                
            } else if (txtCedula.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese Cedula del Alumno o \nGenerar Cedula Escolar.........!");
                txtCedula.requestFocus();
            }else {

                cedula = txtCedula.getText();

                    try {
                        String sSQL = "";
                        String mensaje = "";
                        if (accion.equals("Insertar")) {
                            sSQL = "INSERT INTO  alumnos VALUES(?,?,?,?,?,?,?,?)";
                            mensaje = "Registro Guardado...!";

                            try {
                                insertar = cn.prepareStatement(sSQL);
                                insertar.setString(1, cedula);
                                insertar.setString(2, nombre1);
                                insertar.setString(3, apellido1);
                                insertar.setString(4, sexo);
                                insertar.setString(5, lugar);
                                insertar.setString(6, fecha);
                                insertar.setString(7, edad);
                                insertar.setString(8, ced_rep);
                                int n = insertar.executeUpdate();
                                if (n == 1) {
                                    //JOptionPane.showMessageDialog(null, mensaje);
                                }
                                CargarTabla("");
                                Habilitar();
                                Limpiar();
                                LimpiarCombos();
                                Cajas(false);
                            } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, "e1: " + ex);
                            }

                        } else if (accion.equals("Modificar")) {
                            sSQL = "UPDATE alumnos SET "
                            + "cedula_escolar = ?,"
                            + "nombres = ?,"
                            + "apellidos = ?,"
                            + "sexo = ?,"
                            + "lugar_nac = ?,"
                            + "fec_nac = ?,"
                            + "edad = ?,"
                            + "cedula_representante = ?"
                            + "WHERE cedula_escolar = " + id_act;
                             mensaje = "Registro Actualizado...!";
                            try {
                                modificar = cn.prepareStatement(sSQL);
                                modificar.setString(1, cedula);
                                modificar.setString(2, nombre1);
                                modificar.setString(3, apellido1);
                                modificar.setString(4, sexo);
                                modificar.setString(5, lugar);
                                modificar.setString(6, fecha);
                                modificar.setString(7, edad);
                                modificar.setString(8, ced_rep);
                                int n = modificar.executeUpdate();
                                if (n == 1) {
                                    //JOptionPane.showMessageDialog(null, mensaje);
                                }
                                CargarTabla("");
                                Habilitar();
                                Limpiar();
                                LimpiarCombos();
                                Cajas(false);
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "e2: " + ex);
                            }
                        }

                    } catch (Exception ex) {

                    }
               }
            }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tblAlumnosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAlumnosMouseClicked
        int filasel;
        String id;

        try {
            filasel = tblAlumnos.getSelectedRow();
            if(filasel == -1) {
                //JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila");
            } else {
                modelo = (DefaultTableModel) tblAlumnos.getModel();
                id = (String) modelo.getValueAt(filasel, 0);
                txtBuscar.setText("");
                LimpiarCombos();
                Combos();
                CargarDatos(id);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_tblAlumnosMouseClicked

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        String valor = txtBuscar.getText();
        CargarTabla(valor);
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void btnGenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarActionPerformed
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();

       
        
        String fecha;
        SimpleDateFormat  fechaPersonalizada = new SimpleDateFormat("dd/MM/yyyy");
        fecha = fechaPersonalizada.format(txtFecha.getDate());
        if (fecha.equals("  /  /    ")){
            JOptionPane.showMessageDialog(null, "Debe Llenar los Campos Para \nGenerar la Cedula Escolar.......!");
            txtNombre1.requestFocus();
        }
        else if (txtCedula.getText().equals("")){
            String sCaden = fecha;
            String sSubCaden = sCaden.substring(8,10);
            cedula = v+sSubCaden+txtCedRep.getText();

            String sSQL = "";
            sSQL = "SELECT * FROM alumnos WHERE cedula_escolar = " + cedula;
            try{
                Statement st = cn.createStatement();
                ResultSet rs = st.executeQuery(sSQL);

                if (rs.last()){
                    String ce = rs.getString(1);
                    String nce = ce.substring(0,1);

                    int n = Integer.parseInt(nce);
                    int j = n + 1;

                    String fec = fecha;
                    String nfec = fec.substring(8,10);
                    cedula = j+nfec+txtCedRep.getText();

                    String sSQL1 = "";
                    sSQL1 = "SELECT * FROM alumnos WHERE cedula_escolar = " + cedula;
                    try{
                        Statement st1 = cn.createStatement();
                        ResultSet rs1 = st1.executeQuery(sSQL1);

                        if (rs1.last()){
                            String ce1 = rs1.getString(1);
                            String nce1 = ce1.substring(0, 1);

                            int n1 = Integer.parseInt(nce1);
                            int j1 = n1+1;

                            String fec1 = fecha;
                            String nfec1 = fec1.substring(8, 10);
                            cedula = j1+nfec1+txtCedRep.getText();

                            String sSQL2 = "";
                            sSQL2 = "SELECT * FROM alumnos WHERE cedula_escolar = " + cedula;
                            try {
                                Statement st2 = cn.createStatement();
                                ResultSet rs2 = st2.executeQuery(sSQL2);

                                if (rs2.last()){
                                    String ce2 = rs2.getString(1);
                                    String nce2 = ce2.substring(0,1);

                                    int n2 = Integer.parseInt(nce2);
                                    int j2 = n2+1;

                                    String fec2 = fecha;
                                    String nfec2 = fec2.substring(8,10);
                                    cedula = j2+nfec2+txtCedRep.getText();

                                    String sSQL3 = "";
                                    sSQL3 = "SELECT * FROM alumnos WHERE cedula_escolar = " + cedula;
                                    try {
                                        Statement st3 = cn.createStatement();
                                        ResultSet rs3 = st3.executeQuery(sSQL3);

                                        if (rs3.last()){
                                            String ce3 = rs3.getString(1);
                                            String nce3 = ce3.substring(0,1);

                                            int n3 = Integer.parseInt(nce3);
                                            int j3 = n3+1;

                                            String fec3 = fecha;
                                            String nfec3 = fec3.substring(8,10);
                                            cedula = j3+nfec3+txtCedRep.getText();

                                            String sSQL4 = "";
                                            sSQL4 = "SELECT * FROM alumnos WHERE cedula_escolar = " + cedula;
                                            try {
                                                Statement st4 = cn.createStatement();
                                                ResultSet rs4 = st4.executeQuery(sSQL4);

                                                if (rs4.last()){
                                                    String ce4 = rs4.getString(1);
                                                    String nce4 = ce4.substring(0,1);

                                                    int n4 = Integer.parseInt(nce4);
                                                    int j4 = n4+1;

                                                    String fec4 = fecha;
                                                    String nfec4 = fec4.substring(8,10);
                                                    cedula = j4+nfec4+txtCedRep.getText();

                                                    String sSQL5 = "";
                                                    sSQL5 = "SELECT * FROM alumnos WHERE cedula_escolar = " + cedula;
                                                    try {
                                                        Statement st5 = cn.createStatement();
                                                        ResultSet rs5 = st5.executeQuery(sSQL5);

                                                        if (rs5.last()){
                                                        String ce5 = rs5.getString(1);
                                                        String nce5 = ce5.substring(0,1);

                                                        int n5 = Integer.parseInt(nce5);
                                                        int j5 = n5+1;

                                                        String fec5 = fecha;
                                                        String nfec5 = fec5.substring(8,10);
                                                        cedula = j5+nfec5+txtCedRep.getText();

                                                        String sSQL6 = "";
                                                        sSQL6 = "SELECT * FROM alumnos WHERE cedula_escolar = " + cedula;
                                                        try {
                                                            Statement st6 = cn.createStatement();
                                                            ResultSet rs6 = st6.executeQuery(sSQL6);

                                                            if (rs6.last()){
                                                            String ce6 = rs6.getString(1);
                                                            String nce6 = ce6.substring(0,1);

                                                            int n6 = Integer.parseInt(nce6);
                                                            int j6 = n6+1;

                                                            String fec6 = fecha;
                                                            String nfec6 = fec6.substring(8,10);
                                                            cedula = j6+nfec6+txtCedRep.getText();

                                                            String sSQL7 = "";
                                                            sSQL7 = "SELECT * FROM alumnos WHERE cedula_escolar = " + cedula;
                                                            try {
                                                                Statement st7 = cn.createStatement();
                                                                ResultSet rs7 = st7.executeQuery(sSQL7);

                                                                if (rs7.last()){
                                                                    String ce7 = rs7.getString(1);
                                                                    String nce7 = ce7.substring(0,1);

                                                                    int n7 = Integer.parseInt(nce7);
                                                                    int j7 = n7+1;

                                                                    String fec7 = fecha;
                                                                    String nfec7 = fec7.substring(8,10);
                                                                    cedula = j7+nfec7+txtCedRep.getText();


                                                                }
                                                            }catch(Exception e){

                                                            }
                                                        }
                                                    }catch(Exception e){

                                                    }
                                                        }
                                                }catch(Exception e){

                                                }

                                                    }
                                                 }catch(Exception e){

                                                }
                                            }
                                        }catch(Exception e){

                                    }
                                }
                          } catch (Exception e) {
                                
                          }
                        }
                    }catch(SQLException ex){

                    }

                }
            }catch(SQLException ex){
                
            }
            txtCedula.setText(cedula);
        }
    }//GEN-LAST:event_btnGenerarActionPerformed

    private void txtCedRepKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedRepKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))){
            getToolkit().beep();
            evt.consume();
        }
        String Caracteres = txtCedRep.getText();
        if(Caracteres.length()>=8){
            evt.consume();
        }
    }//GEN-LAST:event_txtCedRepKeyTyped

    private void txtCedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))){
            getToolkit().beep();
            evt.consume();
        }
        String Caracteres = txtCedula.getText();
        if(Caracteres.length()>=11){
            evt.consume();
        }
    }//GEN-LAST:event_txtCedulaKeyTyped

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        //Centramos Formulario
        //********************
        Forms f = new Forms(this);
        f.setCenterFrame();
    }//GEN-LAST:event_formComponentShown

    private void txtEdadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEdadKeyTyped
        char c = evt.getKeyChar();
        if(!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))){
            getToolkit().beep();
            evt.consume();
        }
        String Caracteres = txtEdad.getText();
        if(Caracteres.length()>=11){
            evt.consume();
        }
    }//GEN-LAST:event_txtEdadKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel1;
    private javax.swing.JPanel Panel2;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGenerar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox cboSexo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblAlumnos;
    private javax.swing.JTextField txtApellido1;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCedRep;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtEdad;
    private com.toedter.calendar.JDateChooser txtFecha;
    private javax.swing.JTextField txtLugar;
    private javax.swing.JTextField txtNombre1;
    private javax.swing.JTextField txtRepresentante;
    // End of variables declaration//GEN-END:variables

}
