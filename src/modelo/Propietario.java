package modelo;
/// utilidades
import utilidades.Calidad;
import utilidades.EstadoFenologico;
import utilidades.EstadoPlan;
import utilidades.GestionHuertosException;
import utilidades.Rut;

import java.io.Serializable;
import java.util.ArrayList;

public class Propietario extends Persona implements Serializable {
    private String direccionComercial;
    private ArrayList<Huerto> huertos;
    
    // Constructor
    public Propietario(String rut, String nombre, String email, String direccion, String dirComercial) {
        super(rut, nombre, email, direccion);
        this.direccionComercial = dirComercial;
        this.huertos = new ArrayList<>();
    }
    
    // 
    public String getDireccionComercial() { return direccionComercial; }
    public void setDireccionComercial(String direccionComercial) { this.direccionComercial = direccionComercial; }

    public boolean addHuerto(Huerto huerto) {
        for (Huerto existente : huertos) {
            if (existente.getNombre().equalsIgnoreCase(huerto.getNombre())) {
                return false; 
            }
        }
        huertos.add(huerto);
        return true;
    }
    
    public ArrayList<Huerto> getHuertos() { return huertos; }
}
