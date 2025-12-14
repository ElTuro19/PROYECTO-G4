package modelo;

public class Persona {

    protected String rut;
    protected String nombre;
    protected String email;
    protected String direccion;

    public Persona(String rut, String nombre, String email, String direccion) {
        this.rut = rut;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
    }

    public String getRut() { return rut; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getDireccion() { return direccion; }

    public void setEmail(String email) { this.email = email; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
