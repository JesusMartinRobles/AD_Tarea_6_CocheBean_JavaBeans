/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebacochebean;

import cochebean.frmGestionCoche;
import javax.swing.JFrame;

/**
 *
 * @author Jesus
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // Crear un JFrame de la clase frmGestionCoche
        JFrame frame = new frmGestionCoche();
        // Configurar el tamaño del JFrame
        frame.setSize(600, 450);
        // Configurar la operación por defecto al cerrar el JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Hacer visible el JFrame
        frame.setVisible(true);

    }
}
