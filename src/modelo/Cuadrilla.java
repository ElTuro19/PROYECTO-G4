package modelo;

import utilidades.Calidad;
import utilidades.EstadoPlan;
import java.time.*;
import java.util.*;

public class Cuadrilla {
    private final int id;
    private final String nombre;
    private final LocalDate fecha;
    private final String descripcion;
    private EstadoPlan estado = EstadoPlan.PLANIFICADO;
    private Supervisor supervisor;
    private PlanCosecha plan;
    private final List<CosechadorAsignado> cosechadores = new ArrayList<>();

    public Cuadrilla(int id, String nombre, LocalDate fecha, String descripcion, Supervisor supervisor, PlanCosecha plan){
        this.id = id; this.nombre = nombre; this.fecha = fecha; this.descripcion = descripcion;
        this.supervisor = supervisor; this.plan = plan;
    }

    public int getId(){ return id; }
    public String getNombre(){ return nombre; }
    public LocalDate getFecha(){ return fecha; }
    public String getDescripcion(){ return descripcion; }
    public EstadoPlan getEstado(){ return estado; }
    public Supervisor getSupervisor(){ return supervisor; }
    public PlanCosecha getPlan(){ return plan; }
    public List<CosechadorAsignado> getCosechadores(){ return cosechadores; }

    public void setEstado(EstadoPlan estado){ this.estado = estado; }
    public void addCosechador(CosechadorAsignado ca){ this.cosechadores.add(ca); }
}
