package modelo;

import java.util.ArrayList;

public class Propietario extends Persona {

    private String direccionComercial;
    private ArrayList<Huerto> huertos = new ArrayList<>();

    public Propietario(String rut, String nombre, String email, String direccion, String dirComercial) {
        super(rut, nombre, email, direccion);
        this.direccionComercial = dirComercial;
    }

    public String getDireccionComercial() { return direccionComercial; }

    public boolean addHuerto(Huerto h) {
        for (Huerto x : huertos)
            if (x.getNombre().equalsIgnoreCase(h.getNombre())) return false;
        huertos.add(h);
        return true;
    }

    public ArrayList<Huerto> getHuertos() { return huertos; }
}
