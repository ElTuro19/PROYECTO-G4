package modelo;

import utilidades.Rut;
import java.time.LocalDate;
import java.util.Objects;

public abstract class Persona {
    protected final Rut rut;
    protected String nombre;
    protected String email;
    protected String direccion;
    protected LocalDate fechaNacimiento;
    protected String telefono;
    protected String profesion;

    protected Persona(Rut rut, String nombre, String email, String direccion, String telefono, LocalDate fechaNacimiento, String profesion){
        this.rut = Objects.requireNonNull(rut);
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.profesion = profesion;
    }

    public Rut getRut(){ return rut; }
    public String getNombre(){ return nombre; }
    public String getEmail(){ return email; }
    public String getDireccion(){ return direccion; }
    public String getTelefono(){ return telefono; }
    public LocalDate getFechaNacimiento(){ return fechaNacimiento; }
    public String getProfesion(){ return profesion; }

    public void setNombre(String n){ this.nombre = n; }
    public void setEmail(String e){ this.email = e; }
    public void setDireccion(String d){ this.direccion = d; }
    public void setTelefono(String t){ this.telefono = t; }
    public void setProfesion(String p){ this.profesion = p; }
}
