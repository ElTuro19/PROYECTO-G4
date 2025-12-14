package modelo;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cuadrilla {

    private int id;
    private String nombre;
    private Supervisor supervisor;
    private PlanCosecha planCosecha;
    private ArrayList<Cosechador> cosechadores = new ArrayList<>();

    public Cuadrilla(int id, String nom, Supervisor sup, PlanCosecha plan) {
        this.id = id;
        this.nombre = nom;
        supervisor = sup;
        planCosecha = plan;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Supervisor getSupervisor() { return supervisor; }
    public PlanCosecha getPlanCosecha() { return planCosecha; }

    public boolean addCosechador(LocalDate i, LocalDate f, double m, Cosechador c) {
        return cosechadores.add(c);
    }

    public Cosechador[] getCosechadores() {
        return cosechadores.toArray(new Cosechador[0]);
    }
}
