/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import com.birosoft.liquid.LiquidLookAndFeel;
import javax.swing.UIManager;


/**
 *
 * @author Administrador
 */
public class Apariencias {
    
    public Apariencias(){

    }
        public void Apariencia(){
        String Ventana = "";


        try{
            UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
            //LiquidLookAndFeel.setLiquidDecorations(true, "mac" ) ;
        }catch (Exception e){
            e.printStackTrace();
            }
        }

        //com.birosoft.liquid.LiquidLookAndFeel
        //de.muntjak.tinylookandfeel.TinyLookAndFeel
        //new com.lipstikLF.LipstikLookAndFeel()
        //ch.randelshofer.quaqua.QuaquaLookAndFeel
        //com.nilo.plaf.nimrod.NimRODLookAndFeel
        //com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
        //com.sun.java.swing.plaf.windows.WindowsLookAndFeel
        //com.apple.laf.AquaLookAndFeel
        //com.sun.java.swing.plaf.mac.MacLookAndFeel
        //com.sun.java.swing.plaf.metal.MetalLookFeel
        //com.sun.java.swing.plaf.motif.MotifLookFeel
        //org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel
        //org.jdesktop.swingx.plaf.nimbus.NimbusLookAndFeel
        //ch.randelshofer.quaqua.QuaquaLookAndFeel
        //com.nilo.plaf.nimrod.NimRODLookAndFeel
        //com.sun.java.swing.plaf.gtk.GTKLookAndFeel
        //com.lipstikLF.LipstikLookAndFeel();

      /*  Ventana = "new com.lipstikLF.LipstikLookAndFeel()";


        JFrame.setDefaultLookAndFeelDecorated(true);
        try {
            UIManager.setLookAndFeel(Ventana);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Apariencias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Apariencias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Apariencias.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Apariencias.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }*/

}
