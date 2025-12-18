package modelo;


import java.io.Serializable;
import utilidades.EstadoPlan;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlanCosecha implements Serializable {

    private static final long serialVersionUID = 1L;


    private int id;
    private String nombre;
    private LocalDate inicio;
    private LocalDate finEstimado;
    private double metaKilos;
    private double precioBase;
    private EstadoPlan estado = EstadoPlan.PLANIFICADO;
    private Cuartel cuartel;
    private ArrayList<Cuadrilla> cuadrillas = new ArrayList<>();

    public PlanCosecha(int id, String n, LocalDate i, LocalDate f, double m, double p, Cuartel c) {
        this.id = id;
        nombre = n;
        inicio = i;
        finEstimado = f;
        metaKilos = m;
        precioBase = p;
        cuartel = c;
    }

    public int getId() { return id; }
    public EstadoPlan getEstado() { return estado; }
    public void setEstado(EstadoPlan e) { estado = e; }
    public Cuartel getCuartel() { return cuartel; }
    public double getPrecioBaseKilo() { return precioBase; }

    public boolean addCuadrilla(int id, String n, Supervisor s) {
        for (Cuadrilla c : cuadrillas)
            if (c.getId() == id) return false;
        cuadrillas.add(new Cuadrilla(id, n, s, this));
        return true;
    }

    public Cuadrilla[] getCuadrillas() {
        return cuadrillas.toArray(new Cuadrilla[0]);
    }
}
