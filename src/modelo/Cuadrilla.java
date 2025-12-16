import java.time.LocalDate;
import java.util.ArrayList;

public class Cuadrilla {

    private String nombre;
    private ArrayList<CosechadorAsignado> cosechadores;

    public Cuadrilla(String nombre) {
        this.nombre = nombre;
        this.cosechadores = new ArrayList<>();
    }

    public boolean addCosechador(LocalDate inicio, LocalDate fin, double meta, Cosechador c) {
        CosechadorAsignado ca = new CosechadorAsignado(c, inicio, fin, meta);
        return cosechadores.add(ca);
    }

    public ArrayList<CosechadorAsignado> getCosechadores() {
        return new ArrayList<>(cosechadores);
    }
}
