package modelo;

import java.util.ArrayList;

public class Huerto {

    private String nombre;
    private float superficie;
    private String ubicacion;
    private Propietario propietario;
    private ArrayList<Cuartel> cuarteles = new ArrayList<>();

    public Huerto(String n, float s, String u, Propietario p) {
        nombre = n;
        superficie = s;
        ubicacion = u;
        propietario = p;
    }

    public String getNombre() { return nombre; }
    public float getSuperficie() { return superficie; }
    public String getUbicacion() { return ubicacion; }
    public Propietario getPropietario() { return propietario; }

    public boolean addCuartel(int id, float sup, Cultivo c) {
        for (Cuartel x : cuarteles)
            if (x.getId() == id) return false;
        Cuartel cu = new Cuartel(id, sup, c, this);
        cuarteles.add(cu);
        c.addCuartel(cu);
        return true;
    }

    public Cuartel getCuartel(int id) {
        for (Cuartel c : cuarteles)
            if (c.getId() == id) return c;
        return null;
    }

    public ArrayList<Cuartel> getCuarteles() { return cuarteles; }
}
