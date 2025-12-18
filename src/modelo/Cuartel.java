package modelo;

import utilidades.EstadoFenologico;
import java.util.ArrayList;

public class Cuartel {

    private int id;
    private float superficie;
    private EstadoFenologico estado = EstadoFenologico.REPOSO_INVERNAL;
    private Cultivo cultivo;
    private Huerto huerto;
    private ArrayList<PlanCosecha> planes = new ArrayList<>();

    public Cuartel(int id, float sup, Cultivo c, Huerto h) {
        this.id = id;
        superficie = sup;
        cultivo = c;
        huerto = h;
    }

    public int getId() { return id; }
    public float getSuperficie() { return superficie; }
    public EstadoFenologico getEstado() { return estado; }
    public void setEstado(EstadoFenologico e) { estado = e; }
    public Huerto getHuerto() { return huerto; }

    public void addPlanCosecha(PlanCosecha p) { planes.add(p); }
}
