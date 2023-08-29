/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema;

import Clases.Apariencias;

/**
 *
 * @author Angel
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Apariencias a = new Apariencias();
        a.Apariencia();
        Acceso frm = new Acceso();
        frm.setVisible(true);

    }
}


