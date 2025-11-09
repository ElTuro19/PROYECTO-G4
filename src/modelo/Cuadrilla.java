package modelo;

import java.util.ArrayList;

public class Cuadrilla {

    //Atributos
    private int id;
    private String nombre;
    private int maximoCosechadores;

    //Asignaciones
    private Supervisor supervisor;
    private PlanCosecha planCosecha;
    private ArrayList<Cosechador>cosechadores;
    //Constructor
    public Cuadrilla(int id, String nom, Supervisor sup, PlanCosecha plan){

        this.id = id;
        this.nombre = nom;
        this.supervisor = sup;
        this.planCosecha = plan;
        this.cosechadores = new ArrayList<>();
    }

    //get y set
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Supervisor getSupervisor() {
        return supervisor;
    }

    public PlanCosecha getPlanCosecha() {
        return planCosecha;
    }

    private ArrayList<CosechadorAsignado> asignados = new ArrayList<>();
    private int maximoCosechadores = 10;

    public boolean addCosechador(LocalDate fIni, LocalDate fFin, double meta, Cosechador cos){
        if (cos == null) return false;
        if (asignados.size() >= maximoCosechadores) return false;
        for (CosechadorAsignado a : asignados) {
            if (a.getCosechador().getRut().equals(cos.getRut())) return false;
        }
        CosechadorAsignado nuevo = new CosechadorAsignado(fIni, fFin, meta, this, cos);
        asignados.add(nuevo);
        cos.addCuadrilla(nuevo);
        return true;
    }

    public Cosechador[] getCosechadores() {
        ArrayList<Cosechador> lista = new ArrayList<>();
        for (CosechadorAsignado a : asignados) lista.add(a.getCosechador());
        return lista.toArray(new Cosechador[0]);
    }
}
