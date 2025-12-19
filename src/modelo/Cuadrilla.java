package modelo;

import utilidades.GestionHuertosException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cuadrilla implements Serializable {

    private int id;
    private String nombre;
    private int maximoCosechadores;

    private Supervisor supervisor;
    private PlanCosecha planCosecha;

    // En el enunciado suele pedirse CosechadorAsignado (con fechas/meta)
    private final ArrayList<CosechadorAsignado> asignados;

    public Cuadrilla(int id, String nom, Supervisor sup, PlanCosecha plan){
        this.id = id;
        this.nombre = nom;
        this.supervisor = sup;
        this.planCosecha = plan;
        this.asignados = new ArrayList<>();
        this.maximoCosechadores = 9999; // ajusta si tu enunciado fija un máximo distinto
    }

    public int getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public Supervisor getSupervisor() { return supervisor; }

    public PlanCosecha getPlanCosecha() { return planCosecha; }

    /**
     * Agrega cosechador a la cuadrilla con fecha/meta.
     * Valida:
     * - capacidad máxima
     * - cosechador repetido por rut
     */
    public boolean addCosechador(LocalDate fIni, LocalDate fFin, double meta, Cosechador cos)
            throws GestionHuertosException {

        if (cos == null) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }

        if (asignados.size() >= maximoCosechadores) {
            throw new GestionHuertosException("El número de cosechadores ya alcanzó el máximo permitido");
        }

        for (CosechadorAsignado a : asignados) {
            if (a.getCosechador().getRut().equals(cos.getRut())) {
                throw new GestionHuertosException("El cosechador ya tiene una asignación a una cuadrilla con el id indicado en el plan con el id señalado");
            }
        }

        asignados.add(new CosechadorAsignado(fIni, fFin, meta, this, cos));
        return true;
    }

    /**
     * Compatibilidad: devuelve cosechadores "planos" como en tu versión anterior.
     */
    public Cosechador[] getCosechadores(){
        ArrayList<Cosechador> out = new ArrayList<>();
        for (CosechadorAsignado a : asignados) {
            out.add(a.getCosechador());
        }
        return out.toArray(new Cosechador[0]);
    }

    public CosechadorAsignado[] getAsignados() {
        return asignados.toArray(new CosechadorAsignado[0]);
    }

    public boolean tieneCosechador(Cosechador cos) {
        if (cos == null) return false;
        for (CosechadorAsignado a : asignados) {
            if (a.getCosechador().getRut().equals(cos.getRut())) return true;
        }
        return false;
    }

    public int getMaximoCosechadores(){ return maximoCosechadores; }

    public void setMaximoCosechadores(int max){ this.maximoCosechadores = max; }
}
