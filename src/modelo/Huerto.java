package modelo;
/// utilidades

import java.io.Serializable;
import java.util.ArrayList;

public class Huerto implements Serializable {
    private String nombre;
    private float superficie;
    private String ubicacion;
    private Propietario propietario;
    private ArrayList<Cuartel> cuarteles;
    
    // Constructor
    public Huerto(String nombre, float superficie, String ubicacion, Propietario prop) {
        this.nombre = nombre;
        this.superficie = superficie;
        this.ubicacion = ubicacion;
        this.propietario = prop;
        this.cuarteles = new ArrayList<>();
    }
    
    //
    public String getNombre() { return nombre; }
    
    public float getSuperficie() { return superficie; }
    public void setSuperficie(float superficie) { this.superficie = superficie; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    
    public Propietario getPropietario() { return propietario; }
    public void setPropietario(Propietario propietario) { this.propietario = propietario; }
    
    public boolean addCuartel(int id, float sup, Cultivo cult) {
        for (Cuartel c : cuarteles) {
            if (c.getId() == id) {
                return false;
            }
        }
        Cuartel nuevo = new Cuartel(id, sup, cult, this);
        cult.addCuartel(nuevo);
        return cuarteles.add(nuevo);
    }

    public Cuartel getCuartel(int id) {
        Cuartel cua = null;
        for(Cuartel c : cuarteles){
            if(c.getId() == id){
                cua = c;
                return cua;
            }
        }
        return cua;
    }
    public ArrayList<Cuartel> getCuarteles() { return cuarteles; }
}
