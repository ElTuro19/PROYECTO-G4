package com.Huerto;

import java.util.ArrayList;

public class Huerto {
    private String nombre;
    private float superficie;
    private String ubicacion;
    private Propietario propietario;
    private ArrayList<Cuartel> cuarteles;
    
    // Constructor
    public Huerto(String nombre, float superficie, String ubicacion, Propietario propietario) {
        this.nombre = nombre;
        this.superficie = superficie;
        this.ubicacion = ubicacion;
        this.propietario = propietario;
        this.cuarteles = new ArrayList<>();
    }
    
    //
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public float getSuperficie() { return superficie; }
    public void setSuperficie(float superficie) { this.superficie = superficie; }
    
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    
    public Propietario getPropietario() { return propietario; }
    public void setPropietario(Propietario propietario) { this.propietario = propietario; }
    
    public ArrayList<Cuartel> getCuarteles() { return cuarteles; }
    
    //
    public boolean addCuartel(Cuartel c) {
        for (Cuartel existente : cuarteles) {
            if (existente.getId() == c.getId()) return false;
        }
        cuarteles.add(c);
        return true;
    }
}
