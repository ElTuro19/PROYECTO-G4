package com.Huerto;

import java.util.ArrayList;

public class Supervisor {
    
    private String profesion;
    //
    public String getProfesion() { return profesion; }
    
    public void setProfesion(String profesion){ this.profesion=profesion; }
    
    public void setCuadrilla(Cuadrilla cuad) { Cuadrilla=cuad; }
    
    public Cuadrilla getCuadrilla() { return Cuadrilla; }
    
}