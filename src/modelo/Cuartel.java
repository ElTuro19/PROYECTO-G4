package modelo;

import utilidades.EstadoFenologico;

import java.io.Serializable;
import java.util.ArrayList;

public class Cuartel implements Serializable {
    private int id;
    private float superficie;

    // Por defecto según tu código original
    private EstadoFenologico estado = EstadoFenologico.REPOSO_INVERNAL;

    private Cultivo cultivo;
    private Huerto huerto;
    private final ArrayList<PlanCosecha> planes = new ArrayList<>();

    public Cuartel(int id, float sup, Cultivo cultivo, Huerto huerto){
        this.id = id;
        this.superficie = sup;
        this.cultivo = cultivo;
        this.huerto = huerto;
    }

    public int getId() { return id; }

    public float getSuperficie() { return superficie; }

    public void setSuperficie(float superficie) { this.superficie = superficie; }

    public float getRendimientoEsperado(){
        return cultivo.getRendimiento();
    }

    public EstadoFenologico getEstado() { return estado; }

    /**
     * Cambia estado fenológico controlando transiciones.
     * Regla general: no retrocede.
     * Excepción común: POSTCOSECHA -> REPOSO_INVERNAL (si tu enum lo contempla).
     */
    public boolean setEstado(EstadoFenologico nuevo) {
        if (nuevo == null) return false;
        if (nuevo == estado) return false;

        // No retroceder (según ordinal)
        boolean retrocede = nuevo.ordinal() < estado.ordinal();

        boolean excepcionPost = false;
        try {
            // Estos nombres pueden variar según tu enum; si no existen, no rompe.
            EstadoFenologico post = EstadoFenologico.valueOf("POSTCOSECHA");
            EstadoFenologico inv  = EstadoFenologico.valueOf("REPOSO_INVERNAL");
            excepcionPost = (estado == post && nuevo == inv);
        } catch (Exception ignored) {}

        if (retrocede && !excepcionPost) {
            return false;
        }

        estado = nuevo;
        return true;
    }

    public Cultivo getCultivo() { return cultivo; }

    public Huerto getHuerto(){ return huerto; }

    public void addPlanCosecha(PlanCosecha planCosecha){
        planes.add(planCosecha);
    }

    public PlanCosecha[] getPlanesCosecha(){
        return planes.toArray(new PlanCosecha[0]);
    }
}
