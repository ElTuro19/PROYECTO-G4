package modelo;

import utilidades.Calidad;
import java.time.*;

public class CosechadorAsignado {
    private final LocalDate desde;
    private LocalDate hasta;
    private double metaKilos;
    private Cosechador cosechador;
    private Cuartel cuartel;
    private Cuadrilla cuadrilla;

    public CosechadorAsignado(LocalDate desde, LocalDate hasta, double metaKilos, Cuartel cuartel, Cuadrilla cuadrilla, Cosechador cosechador){
        this.desde = desde; this.hasta = hasta; this.metaKilos = metaKilos;
        this.cuartel = cuartel; this.cuadrilla = cuadrilla; this.cosechador = cosechador;
    }

    public LocalDate getDesde(){ return desde; }
    public LocalDate getHasta(){ return hasta; }
    public double getMetaKilos(){ return metaKilos; }
    public Cosechador getCosechador(){ return cosechador; }
    public Cuartel getCuartel(){ return cuartel; }
    public Cuadrilla getCuadrilla(){ return cuadrilla; }

    public void setHasta(LocalDate h){ this.hasta = h; }
}
