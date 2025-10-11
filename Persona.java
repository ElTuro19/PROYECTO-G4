package com.Huerto;

import java.util.ArrayList;

public class Persona {
    protected Rut rut;
    protected String nombre;
    protected String email;
    protected String direccion;

    // Constructor
    public Persona(Rut rut, String nombre, String email, String direccion) {
        this.rut = rut;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
    }
    
    //
    public Rut getRut() { return rut; }
    
    public String getNombre() { return nombre; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
