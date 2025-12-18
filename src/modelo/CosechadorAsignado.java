package modelo;

import java.time.LocalDate;

public class CosechadorAsignado {

    private LocalDate desde;
    private LocalDate hasta;
    private double metaKilos;
    private Cuadrilla cuadrilla;
    private Cosechador cosechador;

    public CosechadorAsignado(LocalDate d, LocalDate h, double meta, Cuadrilla c, Cosechador cos) {
        desde = d;
        hasta = h;
        metaKilos = meta;
        cuadrilla = c;
        cosechador = cos;
    }

    public LocalDate getDesde() { return desde; }
    public LocalDate getHasta() { return hasta; }
    public Cuadrilla getCuadrilla() { return cuadrilla; }
    public Cosechador getCosechador() { return cosechador; }
}
