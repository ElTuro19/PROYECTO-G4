package modelo;

import java.time.LocalDate;

public class CosechadorAsignado {

    //Atributos
    private LocalDate desde;
    private LocalDate hasta;
    private double metaKilos;

    //Asignaciones
    private Cuadrilla cuadrilla;
    private Cosechador cosechador;

    //Constructor
    public CosechadorAsignado(LocalDate fIni, LocalDate fFin, double meta, Cuadrilla cuad, Cosechador cos){
        this.desde = fIni;
        this.hasta = fFin;
        this.metaKilos = meta;
        this.cuadrilla = cuad;
        this.cosechador = cos;
    }
    //Get y Set
    public LocalDate getDesde() {
        return desde != null ? new java.sql.Date(desde.getTime()).toLocalDate() : null;
    }

    public void setDesde(LocalDate desde) {
        this.desde = java.sql.Date.valueOf(desde);
    }

    public LocalDate getHasta() {
        return hasta != null ? new java.sql.Date(hasta.getTime()).toLocalDate() : null;
    }

    public void setHasta(LocalDate hasta) {
        this.hasta = java.sql.Date.valueOf(hasta);
    }

    public double getMetaKilos() {
        return metaKilos;
    }

    public void setMetaKilos(double metaKilos) {
        this.metaKilos = metaKilos;
    }

    public Cuadrilla getCuadrilla() {
        return cuadrilla;
    }

    public Cosechador getCosechador() {
        return cosechador;
    }
}