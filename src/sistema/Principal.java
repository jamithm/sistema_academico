/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Principal.java
 *
 * Created on 30/12/2011, 09:34:25 PM
 */

package sistema;

import Clases.Users;
import Clases.Modules;
import java.sql.*;
import Clases.ConexionMySQL;
import java.awt.event.KeyEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
/**
 *
 * @author Administrador
 */
public class Principal extends javax.swing.JFrame implements Runnable{
    String hora,minutos,segundos,ampm;
    Calendar calendario;
    Thread h1;
    //Alumnos alum = new Alumnos();
    PreparedStatement INSERTAR;
    public static int CED_USUARIO;
    public static int CLAVE;
    public static int MOD;
    public static String FECHA;
    public static String HORA;


    /** Creates new form Principal */
    public Principal() {
        initComponents();
        //Localizacion
        //************
        this.setLocationRelativeTo(null);
        //obtener fecha
        //*************
        Date hoy = new Date();
        FECHA = ( hoy.getDate()+"/"+(hoy.getMonth()+1) +"/"+(hoy.getYear()+1900) );
        //obtener la hora
        //***************
        h1 = new Thread(this);
        h1.start();
        //obtener usuario
        //***************
        this.buscar_usuario();
        //Maximizamos el Formulario
        //*************************
        this.setExtendedState(Principal.MAXIMIZED_BOTH);
        //Fondo de Escritorio
        //*******************
        //((DesktopConFondo) PANEL).setImagen((Image) null);
        PANEL.setBorder(new ImagenFondo());
        //Accion de Bloquear
        //******************
        Barra.setSize(1380, 50);
        Label1.setVisible(false);
        Label2.setVisible(false);
        txtUsuario.setVisible(false);
        txtClave.setVisible(false);
        btnAcceder.setVisible(false);
        mnuCR.setVisible(false);
    }

        //Metodo para la Capturar la Auditorias
    //************************************
    private void auditoria(){
        //Creamos Conexion
        //****************
        ConexionMySQL mysql = new ConexionMySQL();
        Connection cn = mysql.Conectar();
            String SQL = "";
            //Creamos Sentencia SQL
            //*********************
            SQL += " INSERT INTO auditoria ";
            SQL += " (user, fecha, hora, modulo) ";
            SQL += " VALUES(?, ?, ?, ?) ";

            try{
                //Ejecutamos Accion Insertar
                //**************************
                INSERTAR  = cn.prepareStatement(SQL);
                INSERTAR.setInt(1, CED_USUARIO);
                INSERTAR.setString(2, FECHA);
                INSERTAR.setString(3, HORA);
                INSERTAR.setInt(4, MOD);
                int n = INSERTAR.executeUpdate();
                if(n == 1){
                    //JOptionPane.showMessageDialog(null, MENSAJE);
                }
            } catch(SQLException ex){
                String mensaje = "";
                switch (ex.getErrorCode()){
                    case 1062:
                        mensaje += "Error\n";
                        mensaje += "Ya Registrado...!";
                        break;
                    default:
                        mensaje += "Error\n";
                        mensaje += "Problemas al Guardar Registro...!";
                        break;
                    }
            JOptionPane.showMessageDialog(null, mensaje, "Error...!", JOptionPane.ERROR_MESSAGE);
            }
    }

    //Metodo para calcular la hora
    //***************************
    public void calcula () {
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();


        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM)==Calendar.AM?"AM":"PM";

        if(ampm.equals("PM")){
            int h = calendario.get(Calendar.HOUR_OF_DAY)-12;
            hora = h>9?""+h:"0"+h;
        }else{
            hora = calendario.get(Calendar.HOUR_OF_DAY)>9?""+calendario.get(Calendar.HOUR_OF_DAY):"0"+calendario.get(Calendar.HOUR_OF_DAY);
        }
        minutos = calendario.get(Calendar.MINUTE)>9?""+calendario.get(Calendar.MINUTE):"0"+calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND)>9?""+calendario.get(Calendar.SECOND):"0"+calendario.get(Calendar.SECOND);
    }

    //Metodo para obtener el usuario
    //******************************
    private void buscar_usuario(){
        ConexionMySQL mySQL= new ConexionMySQL();
        Connection cn = mySQL.Conectar();

        String SQL = "";
        SQL = "select cedula from usuarios where usuario = '" + Acceso.USUARIO + "'";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            if (rs.next()){
                CED_USUARIO = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //Metodo para obtener el modulo
    //*****************************
    private void buscar_modulo(){
        ConexionMySQL mySQL= new ConexionMySQL();
        Connection cn = mySQL.Conectar();

        String SQL = "";
        SQL = "select id from modulos where clave = " + CLAVE + "";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(SQL);

            if (rs.next()){
                MOD = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    //Metodo para Bloquear el sistema
    //*******************************
    private void bloquear(Boolean v){
        mnuArchivo.setEnabled(v);
        mnuMatricula.setEnabled(v);
        mnuConsultas.setEnabled(v);
        mnuReportes.setEnabled(v);
        mnuMantenimiento.setEnabled(v);
        mnuAyuda.setEnabled(v);
        Barra.setVisible(v);
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
        PANEL = new javax.swing.JDesktopPane();
        Label1 = new javax.swing.JLabel();
        Label2 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtClave = new javax.swing.JPasswordField();
        btnAcceder = new javax.swing.JButton();
        Barra = new javax.swing.JToolBar();
        btnAlumnos = new javax.swing.JButton();
        btnRepresentantes = new javax.swing.JButton();
        btnMatriculas = new javax.swing.JButton();
        btnDocentes = new javax.swing.JButton();
        btnUsuarios = new javax.swing.JButton();
        btnBloquear = new javax.swing.JButton();
        btnManual = new javax.swing.JButton();
        btnAcerca = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuArchivo = new javax.swing.JMenu();
        mnuAlumnos = new javax.swing.JMenuItem();
        mnuRepresentante = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mnuDocentes = new javax.swing.JMenuItem();
        mnuMaterias = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mnuPlanteles = new javax.swing.JMenuItem();
        mnuAperturar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuGrados = new javax.swing.JMenuItem();
        mnuSecciones = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mnuSalir = new javax.swing.JMenuItem();
        mnuMatricula = new javax.swing.JMenu();
        mnuInscripcion = new javax.swing.JMenuItem();
        mnuAsignar = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        mnuReportes = new javax.swing.JMenu();
        mnuCN = new javax.swing.JMenuItem();
        mnuCE = new javax.swing.JMenuItem();
        mnuCC = new javax.swing.JMenuItem();
        mnuCR = new javax.swing.JMenuItem();
        mnuCTT = new javax.swing.JMenuItem();
        mnuConsultas = new javax.swing.JMenu();
        mnuCon_Alumno = new javax.swing.JMenuItem();
        mnuCon_Boletines = new javax.swing.JMenuItem();
        mnuCon_Matriculas = new javax.swing.JMenuItem();
        mnuMantenimiento = new javax.swing.JMenu();
        mnuRespaldo = new javax.swing.JMenuItem();
        mnuAccesos = new javax.swing.JMenuItem();
        mnuRoles = new javax.swing.JMenuItem();
        mnuUsuarios = new javax.swing.JMenuItem();
        mnuModulos = new javax.swing.JMenuItem();
        mnuAuditoria = new javax.swing.JMenuItem();
        mnuBloquear = new javax.swing.JMenuItem();
        mnuAyuda = new javax.swing.JMenu();
        mnuManual = new javax.swing.JMenuItem();
        mnuAcerca = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISTEMA DE ESTION PARA LOS PROCESOS ACADEMICOS DE LA U.E DR. CANDELARIO OQUENDO"); // NOI18N

        PANEL.setBackground(new java.awt.Color(255, 255, 255));

        Label1.setFont(new java.awt.Font("Tahoma", 1, 18));
        Label1.setText("SISTEMA BLOQUEADO");
        Label1.setBounds(580, 60, 200, 22);
        PANEL.add(Label1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        Label2.setFont(new java.awt.Font("Tahoma", 1, 18));
        Label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Label2.setText("INGRESE SU CONTRASEÑA");
        Label2.setBounds(540, 80, 280, 22);
        PANEL.add(Label2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        txtUsuario.setEditable(false);
        txtUsuario.setFont(new java.awt.Font("Tahoma", 1, 14));
        txtUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsuario.setBounds(540, 110, 280, 23);
        PANEL.add(txtUsuario, javax.swing.JLayeredPane.DEFAULT_LAYER);

        txtClave.setFont(new java.awt.Font("Tahoma", 1, 14));
        txtClave.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtClave.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtClaveKeyPressed(evt);
            }
        });
        txtClave.setBounds(540, 140, 280, 23);
        PANEL.add(txtClave, javax.swing.JLayeredPane.DEFAULT_LAYER);

        btnAcceder.setFont(new java.awt.Font("Tahoma", 1, 14));
        btnAcceder.setText("DESBLOQUEAR");
        btnAcceder.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAcceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccederActionPerformed(evt);
            }
        });
        btnAcceder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnAccederKeyPressed(evt);
            }
        });
        btnAcceder.setBounds(540, 170, 280, 30);
        PANEL.add(btnAcceder, javax.swing.JLayeredPane.DEFAULT_LAYER);

        Barra.setRollover(true);

        btnAlumnos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/distanacia alumnos.png"))); // NOI18N
        btnAlumnos.setToolTipText("Alumnos");
        btnAlumnos.setFocusable(false);
        btnAlumnos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAlumnos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlumnosActionPerformed(evt);
            }
        });
        Barra.add(btnAlumnos);

        btnRepresentantes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/parentezco.png"))); // NOI18N
        btnRepresentantes.setToolTipText("Representantes");
        btnRepresentantes.setFocusable(false);
        btnRepresentantes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRepresentantes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRepresentantes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRepresentantesActionPerformed(evt);
            }
        });
        Barra.add(btnRepresentantes);

        btnMatriculas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/matricula.png"))); // NOI18N
        btnMatriculas.setToolTipText("Inscripciones");
        btnMatriculas.setFocusable(false);
        btnMatriculas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnMatriculas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMatriculas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMatriculasActionPerformed(evt);
            }
        });
        Barra.add(btnMatriculas);

        btnDocentes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/cargo.png"))); // NOI18N
        btnDocentes.setToolTipText("Docentes");
        btnDocentes.setFocusable(false);
        btnDocentes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDocentes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDocentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocentesActionPerformed(evt);
            }
        });
        Barra.add(btnDocentes);

        btnUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/Accept-Male-User.png"))); // NOI18N
        btnUsuarios.setToolTipText("Usuarios");
        btnUsuarios.setFocusable(false);
        btnUsuarios.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUsuarios.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        Barra.add(btnUsuarios);

        btnBloquear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/document-encrypt.png"))); // NOI18N
        btnBloquear.setToolTipText("Bloquear Sistema");
        btnBloquear.setFocusable(false);
        btnBloquear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBloquear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBloquearActionPerformed(evt);
            }
        });
        Barra.add(btnBloquear);

        btnManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/reportes.png"))); // NOI18N
        btnManual.setToolTipText("Manual");
        btnManual.setFocusable(false);
        btnManual.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnManual.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManualActionPerformed(evt);
            }
        });
        Barra.add(btnManual);

        btnAcerca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/computador.png"))); // NOI18N
        btnAcerca.setToolTipText("Acerca");
        btnAcerca.setFocusable(false);
        btnAcerca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAcerca.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcercaActionPerformed(evt);
            }
        });
        Barra.add(btnAcerca);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/application-exit-3.png"))); // NOI18N
        btnSalir.setToolTipText("Salir del Sistema");
        btnSalir.setFocusable(false);
        btnSalir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSalir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        Barra.add(btnSalir);

        Barra.setBounds(0, 0, 830, 40);
        PANEL.add(Barra, javax.swing.JLayeredPane.DEFAULT_LAYER);

        mnuArchivo.setText("Archivo");

        mnuAlumnos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/distanacia alumnos.png"))); // NOI18N
        mnuAlumnos.setText("Alumnos");
        mnuAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAlumnosActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuAlumnos);

        mnuRepresentante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/parentezco.png"))); // NOI18N
        mnuRepresentante.setText("Representantes");
        mnuRepresentante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRepresentanteActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuRepresentante);
        mnuArchivo.add(jSeparator2);

        mnuDocentes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/cargo.png"))); // NOI18N
        mnuDocentes.setText("Docentes");
        mnuDocentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuDocentesActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuDocentes);

        mnuMaterias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/Library2.png"))); // NOI18N
        mnuMaterias.setText("Materias");
        mnuMaterias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuMateriasActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuMaterias);
        mnuArchivo.add(jSeparator3);

        mnuPlanteles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/colegio.png"))); // NOI18N
        mnuPlanteles.setText("Planteles");
        mnuPlanteles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuPlantelesActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuPlanteles);

        mnuAperturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/años escolar.png"))); // NOI18N
        mnuAperturar.setText("Aperturar Inscripcion");
        mnuAperturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAperturarActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuAperturar);
        mnuArchivo.add(jSeparator1);

        mnuGrados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/Hoja (1).png"))); // NOI18N
        mnuGrados.setText("Grados");
        mnuGrados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuGradosActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuGrados);

        mnuSecciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/literal.png"))); // NOI18N
        mnuSecciones.setText("Secciones");
        mnuSecciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSeccionesActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuSecciones);
        mnuArchivo.add(jSeparator5);

        mnuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        mnuSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/application-exit-3.png"))); // NOI18N
        mnuSalir.setText("Salir del Sistema");
        mnuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSalirActionPerformed(evt);
            }
        });
        mnuArchivo.add(mnuSalir);

        jMenuBar1.add(mnuArchivo);

        mnuMatricula.setText("Matricula");

        mnuInscripcion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/matricula.png"))); // NOI18N
        mnuInscripcion.setText("Inscripcion");
        mnuInscripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuInscripcionActionPerformed(evt);
            }
        });
        mnuMatricula.add(mnuInscripcion);

        mnuAsignar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/Docente.png"))); // NOI18N
        mnuAsignar.setText("Asignacion de Docente");
        mnuAsignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAsignarActionPerformed(evt);
            }
        });
        mnuMatricula.add(mnuAsignar);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/logoBD.png"))); // NOI18N
        jMenuItem1.setText("Contro de Notas ");
        mnuMatricula.add(jMenuItem1);

        jMenuBar1.add(mnuMatricula);

        mnuReportes.setText("Reportes");

        mnuCN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/listado (1).png"))); // NOI18N
        mnuCN.setText("Constancia de Notas");
        mnuCN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCNActionPerformed(evt);
            }
        });
        mnuReportes.add(mnuCN);

        mnuCE.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/listado (1).png"))); // NOI18N
        mnuCE.setText("Constancia de Estudios");
        mnuCE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCEActionPerformed(evt);
            }
        });
        mnuReportes.add(mnuCE);

        mnuCC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/listado (1).png"))); // NOI18N
        mnuCC.setText("Constancia de Conducta");
        mnuCC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCCActionPerformed(evt);
            }
        });
        mnuReportes.add(mnuCC);

        mnuCR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/listado (1).png"))); // NOI18N
        mnuCR.setText("Constancia de Retiro");
        mnuCR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCRActionPerformed(evt);
            }
        });
        mnuReportes.add(mnuCR);

        mnuCTT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/listado (1).png"))); // NOI18N
        mnuCTT.setText("Constancia de Tramitacion de Titulos");
        mnuCTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCTTActionPerformed(evt);
            }
        });
        mnuReportes.add(mnuCTT);

        jMenuBar1.add(mnuReportes);

        mnuConsultas.setText("Consultas");

        mnuCon_Alumno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/reporte1.png"))); // NOI18N
        mnuCon_Alumno.setText("Alumnos");
        mnuCon_Alumno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCon_AlumnoActionPerformed(evt);
            }
        });
        mnuConsultas.add(mnuCon_Alumno);

        mnuCon_Boletines.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/reporte1.png"))); // NOI18N
        mnuCon_Boletines.setText("Boletines");
        mnuCon_Boletines.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCon_BoletinesActionPerformed(evt);
            }
        });
        mnuConsultas.add(mnuCon_Boletines);

        mnuCon_Matriculas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/reporte1.png"))); // NOI18N
        mnuCon_Matriculas.setText("Matriculas");
        mnuCon_Matriculas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCon_MatriculasActionPerformed(evt);
            }
        });
        mnuConsultas.add(mnuCon_Matriculas);

        jMenuBar1.add(mnuConsultas);

        mnuMantenimiento.setText("Mantenimiento");

        mnuRespaldo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/db.png"))); // NOI18N
        mnuRespaldo.setText("Respaldo");
        mnuRespaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRespaldoActionPerformed(evt);
            }
        });
        mnuMantenimiento.add(mnuRespaldo);

        mnuAccesos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/identity-2.png"))); // NOI18N
        mnuAccesos.setText("Accesos");
        mnuAccesos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAccesosActionPerformed(evt);
            }
        });
        mnuMantenimiento.add(mnuAccesos);

        mnuRoles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/identity.png"))); // NOI18N
        mnuRoles.setText("Roles");
        mnuRoles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRolesActionPerformed(evt);
            }
        });
        mnuMantenimiento.add(mnuRoles);

        mnuUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/Accept-Male-User.png"))); // NOI18N
        mnuUsuarios.setText("Usuarios");
        mnuUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuUsuariosActionPerformed(evt);
            }
        });
        mnuMantenimiento.add(mnuUsuarios);

        mnuModulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/kgpg_info.png"))); // NOI18N
        mnuModulos.setText("Modulos");
        mnuModulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuModulosActionPerformed(evt);
            }
        });
        mnuMantenimiento.add(mnuModulos);

        mnuAuditoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/AUDITORIA1.png"))); // NOI18N
        mnuAuditoria.setText("Auditoria");
        mnuAuditoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAuditoriaActionPerformed(evt);
            }
        });
        mnuMantenimiento.add(mnuAuditoria);

        mnuBloquear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/document-encrypt.png"))); // NOI18N
        mnuBloquear.setText("Bloquear el Sistema");
        mnuBloquear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuBloquearActionPerformed(evt);
            }
        });
        mnuMantenimiento.add(mnuBloquear);

        jMenuBar1.add(mnuMantenimiento);

        mnuAyuda.setText("Ayuda");

        mnuManual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/reportes.png"))); // NOI18N
        mnuManual.setText("Manual");
        mnuManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuManualActionPerformed(evt);
            }
        });
        mnuAyuda.add(mnuManual);

        mnuAcerca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sistema/iconos/computador.png"))); // NOI18N
        mnuAcerca.setText("Acerca");
        mnuAcerca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAcercaActionPerformed(evt);
            }
        });
        mnuAyuda.add(mnuAcerca);

        jMenuBar1.add(mnuAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PANEL, javax.swing.GroupLayout.DEFAULT_SIZE, 829, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PANEL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuInscripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuInscripcionActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_INSCRIPCION))
        {
            return;
        }

        CLAVE = Modules.MOD_INSCRIPCION;
        this.buscar_modulo();
        this.auditoria();

        Inscripciones frm = null;
        frm = new Inscripciones();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuInscripcionActionPerformed

    private void mnuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSalirActionPerformed
        int opcion = JOptionPane.showConfirmDialog(null,"¿Desea Salir del Sistema...?", "Salir del Sistema...!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (opcion == 0){
            this.dispose();
        }
    }//GEN-LAST:event_mnuSalirActionPerformed

    private void mnuAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAlumnosActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_ALUMNOS))
        {
            return;
        }

        CLAVE = Modules.MOD_ALUMNOS;
        this.buscar_modulo();
        this.auditoria();

        Alumnos frm = null;
        frm = new Alumnos();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuAlumnosActionPerformed

    private void mnuRepresentanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRepresentanteActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_REPRESENTANTES))
        {
            return;
        }

        CLAVE = Modules.MOD_REPRESENTANTES;
        this.buscar_modulo();
        this.auditoria();

        Representantes frm = null;
        frm = new Representantes();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuRepresentanteActionPerformed

    private void mnuDocentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuDocentesActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_DOCENTES))
        {
            return;
        }

        CLAVE = Modules.MOD_DOCENTES;
        this.buscar_modulo();
        this.auditoria();

        Docentes frm = null;
        frm = new Docentes();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuDocentesActionPerformed

    private void mnuMateriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuMateriasActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_MATERIAS))
        {
            return;
        }

        CLAVE = Modules.MOD_MATERIAS;
        this.buscar_modulo();
        this.auditoria();

        Materias frm = null;
        frm = new Materias();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuMateriasActionPerformed

    private void mnuAperturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAperturarActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_APERTURAR))
        {
            return;
        }

        CLAVE = Modules.MOD_APERTURAR;
        this.buscar_modulo();
        this.auditoria();

        Aperturar frm = null;
        frm = new Aperturar();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuAperturarActionPerformed

    private void mnuPlantelesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuPlantelesActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_PLANTELES))
        {
            return;
        }

        CLAVE = Modules.MOD_PLANTELES;
        this.buscar_modulo();
        this.auditoria();

        Planteles frm = null;
        frm = new Planteles();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuPlantelesActionPerformed

    private void mnuCNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCNActionPerformed

    }//GEN-LAST:event_mnuCNActionPerformed

    private void mnuCEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCEActionPerformed

    }//GEN-LAST:event_mnuCEActionPerformed

    private void mnuBloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuBloquearActionPerformed
        Label1.setVisible(true);
        Label2.setVisible(true);
        txtUsuario.setVisible(true);
        txtClave.setVisible(true);
        btnAcceder.setVisible(true);
        bloquear(false);
        txtUsuario.setText(Acceso.USUARIO);
        txtClave.requestFocus();
    }//GEN-LAST:event_mnuBloquearActionPerformed

    private void btnAccederActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccederActionPerformed
        //Validamos  Campo Clave
        //**********************
        if (txtClave.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Ingrese su Clave...!");
            txtClave.requestFocus();
        }else{
            //Creamos Conexion
            //****************
            ConexionMySQL mysql = new ConexionMySQL();
            Connection cn = mysql.Conectar();
            //Creamos Sentencia SQL
            //*********************
            String sSQL="";
            sSQL = "SELECT * FROM usuarios WHERE usuario = ? and clave = ?";
            //Ejecutamos la Sentencia
            //***********************
            try {
                PreparedStatement pst = cn.prepareStatement(sSQL);
                pst.setString(1, txtUsuario.getText());
                pst.setString(2, txtClave.getText());
                ResultSet rs = pst.executeQuery();
                if (rs.next()){
                    //Bloquamos el Menu
                    //*****************
                    bloquear(true);
                    //Llamamos Campos
                    //***************
                    Label1.setVisible(false);
                    Label2.setVisible(false);
                    txtUsuario.setVisible(false);
                    txtClave.setVisible(false);
                    btnAcceder.setVisible(false);
                    txtClave.setText("");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Error: Clave Incorrecta");
                    txtClave.setText("");
                    txtClave.requestFocus();
                }
           }
           catch(SQLException e){
           JOptionPane.showMessageDialog(null,e);
        }
        }
    }//GEN-LAST:event_btnAccederActionPerformed

    private void mnuCon_MatriculasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCon_MatriculasActionPerformed

    }//GEN-LAST:event_mnuCon_MatriculasActionPerformed

    private void mnuCRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCRActionPerformed

    }//GEN-LAST:event_mnuCRActionPerformed

    private void mnuCon_BoletinesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCon_BoletinesActionPerformed

    }//GEN-LAST:event_mnuCon_BoletinesActionPerformed

    private void mnuCon_AlumnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCon_AlumnoActionPerformed

    }//GEN-LAST:event_mnuCon_AlumnoActionPerformed

    private void mnuUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuUsuariosActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_USUARIOS))
        {
            return;
        }

        CLAVE = Modules.MOD_USUARIOS;
        this.buscar_modulo();
        this.auditoria();

        Usuarios frm = null;
        frm = new Usuarios();

        PANEL.add(frm);
        frm.setVisible(true);
}//GEN-LAST:event_mnuUsuariosActionPerformed

    private void mnuCCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCCActionPerformed

    }//GEN-LAST:event_mnuCCActionPerformed

    private void mnuCTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCTTActionPerformed

    }//GEN-LAST:event_mnuCTTActionPerformed

    private void mnuRespaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRespaldoActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_RESPALDO))
        {
            return;
        }

        CLAVE = Modules.MOD_RESPALDO;
        this.buscar_modulo();
        this.auditoria();

        Respaldo frm = null;
        frm = new Respaldo();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuRespaldoActionPerformed

    private void btnAccederKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnAccederKeyPressed
        btnAcceder.requestFocus();
        if (evt.getKeyCode()==KeyEvent.VK_ENTER){
            
            //JOptionPane.showMessageDialog(null, "ok");
        }
    }//GEN-LAST:event_btnAccederKeyPressed

    private void txtClaveKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClaveKeyPressed
        //btnAcceder.requestFocus();
    }//GEN-LAST:event_txtClaveKeyPressed

    private void mnuRolesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRolesActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_ROLES))
        {
            return;
        }

        CLAVE = Modules.MOD_ROLES;
        this.buscar_modulo();
        this.auditoria();

        Roles frm = null;
        frm = new Roles(); 

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuRolesActionPerformed

    private void mnuModulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuModulosActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_MODULOS)){
            return;
        }

        CLAVE = Modules.MOD_MODULOS;
        this.buscar_modulo();
        this.auditoria();

        Modulos frm = null;
        frm = new Modulos();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuModulosActionPerformed

    private void mnuAccesosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAccesosActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_ACCESOS)){
            return;
        }

        CLAVE = Modules.MOD_ACCESOS;
        this.buscar_modulo();
        this.auditoria();

        Accesos frm = null;
        frm = new Accesos();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuAccesosActionPerformed

    private void mnuAuditoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAuditoriaActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_AUDITORIA)){
            return;
        }

        CLAVE = Modules.MOD_AUDITORIA;
        this.buscar_modulo();
        this.auditoria();

        Auditorias frm = null;
        frm = new Auditorias();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuAuditoriaActionPerformed

    private void mnuGradosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuGradosActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_GRADOS)){
            return;
        }

        CLAVE = Modules.MOD_GRADOS;
        this.buscar_modulo();
        this.auditoria();

        Grados frm = null;
        frm = new Grados();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuGradosActionPerformed

    private void mnuSeccionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSeccionesActionPerformed
        //Validamos si el usuario puede acceder a este modulo
        //***************************************************
        Users u = new Users();
        if (!u.checkModule(Modules.MOD_SECCIONES))
        {
            return;
        }

        CLAVE = Modules.MOD_SECCIONES;
        this.buscar_modulo();
        this.auditoria();

        Secciones frm = null;
        frm = new Secciones();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuSeccionesActionPerformed

    private void mnuManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuManualActionPerformed

    }//GEN-LAST:event_mnuManualActionPerformed

    private void mnuAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAcercaActionPerformed
        Acerca frm = null;
        frm = new Acerca();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuAcercaActionPerformed

    private void btnAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlumnosActionPerformed
        mnuAlumnosActionPerformed(evt);
    }//GEN-LAST:event_btnAlumnosActionPerformed

    private void btnRepresentantesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRepresentantesActionPerformed
        mnuRepresentanteActionPerformed(evt);
    }//GEN-LAST:event_btnRepresentantesActionPerformed

    private void btnMatriculasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMatriculasActionPerformed
        mnuInscripcionActionPerformed(evt);
    }//GEN-LAST:event_btnMatriculasActionPerformed

    private void btnDocentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocentesActionPerformed
        mnuDocentesActionPerformed(evt);
    }//GEN-LAST:event_btnDocentesActionPerformed

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        mnuUsuariosActionPerformed(evt);
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnBloquearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBloquearActionPerformed
        mnuBloquearActionPerformed(evt);
    }//GEN-LAST:event_btnBloquearActionPerformed

    private void btnManualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManualActionPerformed
        mnuManualActionPerformed(evt);
    }//GEN-LAST:event_btnManualActionPerformed

    private void btnAcercaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcercaActionPerformed
        mnuAcercaActionPerformed(evt);
    }//GEN-LAST:event_btnAcercaActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        mnuSalirActionPerformed(evt);
    }//GEN-LAST:event_btnSalirActionPerformed

    private void mnuAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAsignarActionPerformed
        Asignar_Docentes frm = null;
        frm = new Asignar_Docentes();

        PANEL.add(frm);
        frm.setVisible(true);
    }//GEN-LAST:event_mnuAsignarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToolBar Barra;
    private javax.swing.JLabel Label1;
    private javax.swing.JLabel Label2;
    public static javax.swing.JDesktopPane PANEL;
    private javax.swing.JButton btnAcceder;
    private javax.swing.JButton btnAcerca;
    private javax.swing.JButton btnAlumnos;
    private javax.swing.JButton btnBloquear;
    private javax.swing.JButton btnDocentes;
    private javax.swing.JButton btnManual;
    private javax.swing.JButton btnMatriculas;
    private javax.swing.JButton btnRepresentantes;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JMenuItem mnuAccesos;
    private javax.swing.JMenuItem mnuAcerca;
    private javax.swing.JMenuItem mnuAlumnos;
    private javax.swing.JMenuItem mnuAperturar;
    private javax.swing.JMenu mnuArchivo;
    private javax.swing.JMenuItem mnuAsignar;
    private javax.swing.JMenuItem mnuAuditoria;
    private javax.swing.JMenu mnuAyuda;
    private javax.swing.JMenuItem mnuBloquear;
    private javax.swing.JMenuItem mnuCC;
    private javax.swing.JMenuItem mnuCE;
    private javax.swing.JMenuItem mnuCN;
    private javax.swing.JMenuItem mnuCR;
    private javax.swing.JMenuItem mnuCTT;
    private javax.swing.JMenuItem mnuCon_Alumno;
    private javax.swing.JMenuItem mnuCon_Boletines;
    private javax.swing.JMenuItem mnuCon_Matriculas;
    private javax.swing.JMenu mnuConsultas;
    private javax.swing.JMenuItem mnuDocentes;
    private javax.swing.JMenuItem mnuGrados;
    private javax.swing.JMenuItem mnuInscripcion;
    private javax.swing.JMenu mnuMantenimiento;
    private javax.swing.JMenuItem mnuManual;
    private javax.swing.JMenuItem mnuMaterias;
    private javax.swing.JMenu mnuMatricula;
    private javax.swing.JMenuItem mnuModulos;
    private javax.swing.JMenuItem mnuPlanteles;
    private javax.swing.JMenu mnuReportes;
    private javax.swing.JMenuItem mnuRepresentante;
    private javax.swing.JMenuItem mnuRespaldo;
    private javax.swing.JMenuItem mnuRoles;
    private javax.swing.JMenuItem mnuSalir;
    private javax.swing.JMenuItem mnuSecciones;
    private javax.swing.JMenuItem mnuUsuarios;
    private javax.swing.JPasswordField txtClave;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables

    public void run() {
        Thread ct = Thread.currentThread();
        while(ct == h1) {
            calcula();
            HORA = (hora + ":" + minutos + ":" + segundos + " "+ampm);
            try {
                Thread.sleep(1000);
            }catch(InterruptedException e) {}
        }
    }
}
