package modelo;

public class Supervisor extends Persona {

    private String profesion;
    private Cuadrilla cuadrilla;

    public Supervisor(String rut, String nombre, String email, String direccion, String profesion) {
        super(rut, nombre, email, direccion);
        this.profesion = profesion;
    }

    public String getProfesion() { return profesion; }
    public Cuadrilla getCuadrilla() { return cuadrilla; }
    public void setCuadrilla(Cuadrilla c) { this.cuadrilla = c; }
}
