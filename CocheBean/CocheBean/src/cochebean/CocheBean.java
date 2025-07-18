/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package cochebean;

import java.beans.*;
import java.io.Serializable;
import java.sql.*;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesus
 */
public class CocheBean implements Serializable {

    private PropertyChangeSupport propertySupport; // Objeto de la clase PropertyChangeSupport

    // Constructor de la clase
    public CocheBean() {
        propertySupport = new PropertyChangeSupport(this); // Inicializa el objeto de la clase PropertyChangeSupport
        // Inicializa las propiedades de la clase
        try {
            recargarFilas();
        } catch (ClassNotFoundException ex) {
            this.matricula = "";
            this.marca = "";
            this.modelo = "";
            this.km = 0;
            this.yearmat = 0;
            Logger.getLogger(CocheBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected String matricula;
    protected String marca;
    protected String modelo;
    protected int km;
    protected int yearmat;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getYearmat() {
        return yearmat;
    }

    public void setYearmat(int yearmat) {
        this.yearmat = yearmat;
    }

    // Clase Coche que representa un coche con sus propiedades
    public class Coche {

        String matricula;
        String marca;
        String modelo;
        int km;
        int yearmat;

        public Coche() {
        }

        public Coche(String nMatricula, String nMarca, String nmodelo, int nkm, int nyearmat) {
            this.matricula = nMatricula;
            this.marca = nMarca;
            this.modelo = nmodelo;
            this.km = nkm;
            this.yearmat = nyearmat;
        }

        public String toString() {
            return "Matricula: " + this.matricula + ", Marca: " + this.marca + ", Modelo: " + this.modelo + ", Km: " + this.km + ", Año de Matriculación: " + this.yearmat;
        }
    }

    // Método que crea un objeto de la clase Coche
    public Coche createCoche(String nMatricula, String nMarca, String nmodelo, int nkm, int nyearmat) {
        return new Coche(nMatricula, nMarca, nmodelo, nkm, nyearmat);

    }

    private List<Coche> Coches = new ArrayList<Coche>(); // Lista de objetos de la clase Coche

    // Método que recarga las filas de la base de datos
    private void recargarFilas() throws ClassNotFoundException {
        // Cargar los datos de la base de datos
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/coches", "root", "");
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from coches");
            Coches = new ArrayList<>();
            while (rs.next()) {
                Coche a = new Coche(rs.getString("matricula"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getInt("km"),
                        rs.getInt("yearmat"));

                Coches.add(a);
            }
            Coche c = new Coche();
            c = (Coche) Coches.get(0);
            this.matricula = c.matricula;
            this.marca = c.marca;
            this.modelo = c.modelo;
            this.km = c.km;
            this.yearmat = c.yearmat;
            rs.close();
            con.close();
        } catch (SQLException ex) {
            this.matricula = "";
            this.marca = "";
            this.modelo = "";
            this.km = 0;
            this.yearmat = 0;
            Logger.getLogger(CocheBean.class.getName()).log(Level.SEVERE, null, ex); // Log de errores
        }
    }

    // Método que devuelve la lista de coches
    public List<Coche> getCochesList() {
        return Coches;

    }

    // Método que selecciona una fila de la lista de coches
    public void seleccionarFila(int i) {
        // Si el índice es menor que el tamaño de la lista
        if (i <= Coches.size()) {
            Coche c = new Coche();
            c = (Coche) Coches.get(i);
            this.matricula = c.matricula;
            this.marca = c.marca;
            this.modelo = c.modelo;
            this.km = c.km;
            this.yearmat = c.yearmat;
        } else {
            this.matricula = "";
            this.marca = "";
            this.modelo = "";
            this.km = 0;
            this.yearmat = 0;
        }
    }

    private BDModificadaListener receptor; // Objeto de la clase BDModificadaListener

    // Clase que representa un evento de modificación de la base de datos
    public class BDModificadaEvent extends java.util.EventObject {

        // constructor
        public BDModificadaEvent(Object source) {
            super(source);
        }
    }

    //Define la interfaz para el nuevo tipo de evento
    public interface BDModificadaListener extends EventListener {

        // Método que captura un evento de modificación de la base de datos
        public void capturarBDModificada(BDModificadaEvent ev);
    }

    // Método que añade un listener de modificación de la base de datos
    public void addBDModificadaListener(BDModificadaListener receptor) {
        this.receptor = receptor;
    }

    // Método que elimina un listener de modificación de la base de datos
    public void removeBDModificadaListener(BDModificadaListener receptor) {
        this.receptor = null;
    }

    // Método que añade un coche a la base de datos
    public void addCoche(Coche newCoche) throws ClassNotFoundException {

        // Añadir un coche a la base de datos
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/coches", "root", "");
            PreparedStatement s = con.prepareStatement("insert into coches values (?,?,?,?,?)");

            s.setString(1, newCoche.matricula);
            s.setString(2, newCoche.marca);
            s.setString(3, newCoche.modelo);
            s.setInt(4, newCoche.km);
            s.setInt(5, newCoche.yearmat);

            s.executeUpdate();
            recargarFilas();
            BDModificadaEvent b = new BDModificadaEvent(this);
            receptor.capturarBDModificada(b);
        } catch (SQLException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Método que elimina un coche de la base de datos
     *
     * Se presupone que se han usado los métodos set para configurar
     * adecuadamente las propiedades con los datos del nuevo registro.
     *
     * @param deletedMatricula
     * @throws java.lang.ClassNotFoundException
     */
    public void deleteCoche(String deletedMatricula) throws ClassNotFoundException {

        // Eliminar un coche de la base de datos
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/coches", "root", "");
            String deleteQuery = "DELETE FROM Coches WHERE matricula = ?";

            PreparedStatement s = con.prepareStatement(deleteQuery);
            s.setString(1, deletedMatricula);
            s.executeUpdate();
            recargarFilas();
            receptor.capturarBDModificada(new BDModificadaEvent(this));
        } catch (SQLException ex) {
            Logger.getLogger(Coche.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método que actualiza un coche de la base de datos
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    // Método que elimina un listener de modificación de la base de datos
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

}
