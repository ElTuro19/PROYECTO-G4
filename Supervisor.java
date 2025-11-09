// import x.Rut;
// import x.Cuadrilla;

public class Supervisor extends Persona {
    
    private String profesion;
    private Cuadrilla cuadrilla;
    
    public Supervisor(Rut rut, String nombre, String email, String direccion, String profesion) {
        super(rut, nombre, email, direccion);
        this.profesion = profesion;
        
        this.cuadrilla = null; 
    }
    
    public String getProfesion() { 
        return profesion; 
    }
    public void setProfesion(String profesion){ 
        this.profesion = profesion; 
    }
    public void setCuadrilla(Cuadrilla cuadrilla) { // El UML dice que el parámetro se llama 'cuadrilla'
        this.cuadrilla = cuadrilla; 
    }
    public Cuadrilla getCuadrilla() { 
        return this.cuadrilla; 
    }
}