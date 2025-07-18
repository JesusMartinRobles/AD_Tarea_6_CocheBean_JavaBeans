/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pruebacochebean;
import cochebean.CocheBean;
import cochebean.CocheBean.BDModificadaEvent;
import cochebean.CocheBean.BDModificadaListener;

/**
 *
 * @author Jesus
 */
public class AccedeBD implements BDModificadaListener{

    CocheBean coche; // Objeto de la clase CocheBean
    // Constructor de la clase
    public AccedeBD() {
         coche = new CocheBean();
         coche.addBDModificadaListener((CocheBean.BDModificadaListener)(BDModificadaListener)this); // Añade el listener a la clase CocheBean
    }

    // Método que añade un coche a la base de datos
    public void listado()
    {
        for(int i=0; i<4; i++)
        {
            coche.seleccionarFila(i);
            System.out.println("Coche " + i );
            System.out.println("Matricula:" + coche.getMatricula());
            System.out.println("Marca:" + coche.getMarca());
            System.out.println("Modelo:" + coche.getModelo());
            System.out.println("Km:" + coche.getKm());
            System.out.println("Año Matriculacion:" + coche.getYearmat());           
        }
    }

    // Implementación del método de la interfaz BDModificadaListener
    public void capturarBDModificada(BDModificadaEvent ev)
    {
        System.out.println("Se ha añadido un elemento a la base de datos");
    }
}
