import java.util.ArrayList;

public class Cuartel {
    private int id;
    private float superficie;
    private EstadoFenologico estado = EstadoFenologico.REPOSO_INVERNAL;

    private Cultivo cultivo;
    private Huerto huerto;
    private ArrayList<PlanCosecha> planes = new ArrayList<>();

    public Cuartel(int id, float sup, Cultivo cultivo, Huerto huerto){
        this.id = id;
        this.superficie = sup;
        this.cultivo = cultivo;
        this.huerto = huerto;
    }

    public int getId() {
        return id;
    }

    public float getSuperficie() {
        return superficie;
    }

    public float getRendimientoEsperado(){
        return cultivo.getRendimiento();
    }

    public EstadoFenologico getEstado() {
        return estado;
    }

    public void setSuperficie(float superficie) {
        this.superficie = superficie;
    }

    public void setEstado(EstadoFenologico estado) {
        this.estado = estado;
    }

    public Cultivo getCultivo() {return cultivo;}

    public Huerto getHuerto(){
        return huerto;
    }

    public void addPlanCosecha(PlanCosecha planCosecha){
        planes.add(planCosecha);
    }

    public PlanCosecha[] getPlanesCosecha(){
        return planes.toArray(new PlanCosecha[0]);
    }

}
