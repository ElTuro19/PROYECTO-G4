package com.Huerto;

import java.util.ArrayList;

public class Propietario extends Persona {
    private String direccionComercial;
    private ArrayList<Huerto> huertos;
    
    // Constructor
    public Propietario(Rut rut, String nombre, String email, String direccion, String dirComercial) {
        super(rut, nombre, email, direccion);
        this.direccionComercial = dirComercial;
        this.huertos = new ArrayList<>();
    }
    
    // 
    public String getDireccionComercial() { return direccionComercial; }
    public void setDireccionComercial(String direccionComercial) { this.direccionComercial = direccionComercial; }

    public boolean addHuerto(Huerto h) {
        for (Huerto existente : huertos) {
            if (existente.getNombre().equalsIgnoreCase(h.getNombre())) {
                return false; // ya existe un huerto con ese nombre
            }
        }
        huertos.add(h);
        return true;
    }
    
    public ArrayList<Huerto> getHuertos() { return huertos; }
}
