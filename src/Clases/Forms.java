/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.awt.Component;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author Administrador
 */
public class Forms
{
    private Component frm;
    public Forms(Component frm)
    {
        this.frm = frm;
    }
    /**
     * @return the form
     */
    public Component getForm()
    {
        return frm;
    }
    /**
     * Centra un Frame 
     */
    public void setCenterFrame()
    {
        //Cuendo sea un JFrame
        //********************
        if (this.getForm() instanceof JFrame)
        {
            JFrame f = (JFrame) this.getForm();
            f.setLocationRelativeTo(null);
        }
        //Cuando sea un JInternalFrame
        //****************************
        if (this.getForm() instanceof JInternalFrame)
        {
            this.getForm().setLocation(this.getForm().getParent().getWidth() / 2 - this.getForm().getWidth() / 2,
                                       this.getForm().getParent().getHeight() / 2 - this.getForm().getHeight() / 2 - 3);

        }
        //Cuando sea un JDialog
        //*********************
        if (this.getForm() instanceof JDialog)
        {
            this.getForm().setLocation(this.getForm().getParent().getWidth() / 2 - this.getForm().getWidth() / 2,
                                       this.getForm().getParent().getHeight() / 2 - this.getForm().getHeight() / 2 - 20);
        }
    }
    /**
     * Expande la ventana al tamaño del escritorio
     */
    public void setFrameToScreen()
    {
        //Ajustamos al ancho y alto el escritorio
        //***************************************
        int nW = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int nH = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

        this.getForm().setSize(nW,
                               nH - 28);
    }
}
