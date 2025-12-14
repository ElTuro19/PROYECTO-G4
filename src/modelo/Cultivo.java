package modelo;

import java.util.ArrayList;

public class Cultivo {

    private int id;
    private String especie;
    private String variedad;
    private float rendimiento;
    private ArrayList<Cuartel> cuarteles = new ArrayList<>();

    public Cultivo(int id, String e, String v, double r) {
        this.id = id;
        especie = e;
        variedad = v;
        rendimiento = (float) r;
    }

    public int getId() { return id; }
    public String getEspecie() { return especie; }
    public String getVariedad() { return variedad; }
    public float getRendimiento() { return rendimiento; }

    public boolean addCuartel(Cuartel c) {
        for (Cuartel x : cuarteles)
            if (x.getId() == c.getId()) return false;
        cuarteles.add(c);
        return true;
    }

    public Cuartel[] getCuarteles() {
        return cuarteles.toArray(new Cuartel[0]);
    }
}
