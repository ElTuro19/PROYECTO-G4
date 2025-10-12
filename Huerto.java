package com.Huerto;

import java.util.ArrayList;

public class Huerto {
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
        propietario = prop;
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
    
    public boolean addCuartel(Cuartel cuartel) {
        for (Cuartel existente : cuarteles) {
            if (existente.getId() == cuartel.getId()) return false;
        }
        cuarteles.add(cuartel);
        return true;
    }

    public Cuartel getCuartel(int id) { return cuarteles.get(id); }
    public ArrayList<Cuartel> getCuarteles() { return cuarteles; }
}
