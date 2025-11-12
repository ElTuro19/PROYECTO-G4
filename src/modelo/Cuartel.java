package modelo;

import utilidades.EstadoFenologico;

public class Cuartel {
    private final int idCuartel;
    private final float superficie;
    private float densidad;
    private EstadoFenologico estado;
    private final Cultivo cultivo;
    private final Huerto huerto;

    public Cuartel(int idCuartel, float superficie, float densidad, Cultivo cultivo, Huerto huerto){
        this.idCuartel = idCuartel; this.superficie = superficie; this.densidad = densidad;
        this.estado = EstadoFenologico.REPOSO_INVERNAL;
        this.cultivo = cultivo; this.huerto = huerto;
    }
    public int getIdCuartel(){ return idCuartel; }
    public float getSuperficie(){ return superficie; }
    public float getDensidad(){ return densidad; }
    public EstadoFenologico getEstado(){ return estado; }
    public Cultivo getCultivo(){ return cultivo; }
    public Huerto getHuerto(){ return huerto; }

    public void setEstado(EstadoFenologico nuevo){ this.estado = nuevo; }
    public void setDensidad(float d){ this.densidad = d; }
}
