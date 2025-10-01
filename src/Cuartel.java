public class Cuartel {
    private int id;
    private float superficie;
    private EstadoFenologico estado;

    private Cultivo cultivo;
    private Huerto huerto;

    public Cuartel(int id, float sup, Cultivo cultivo, Huerto huerto){
        this.id = id;
        sup = superficie;
        this.cultivo = cultivo;
        this.huerto = huerto;
    }

    public int getId() {
        return id;
    }

    public float getSuperficie() {
        return superficie;
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

    public Huerto getHuerto(){
        return huerto;
    }

    public PlanCosecha[] getPlanesCosecha(){
        return planes.toArray(new PlanCosecha[0]);
    }


}
