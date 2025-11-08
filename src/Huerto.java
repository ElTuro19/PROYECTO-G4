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
        for (Cuartel c : this.cuarteles) {
            if (c.getId() == id) {
                return false;
            }
            Cuartel nuevo = new Cuartel(id, sup, cult, this);
            this.cuarteles.add(nuevo);
            cult.addCuartel(nuevo);
        }
        return true;
    }

    public Cuartel getCuartel(int id) { return this.cuarteles.get(id); }
    public ArrayList<Cuartel> getCuarteles() { return this.cuarteles; }
}
