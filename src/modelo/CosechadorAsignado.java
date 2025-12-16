import java.time.LocalDate;

public class CosechadorAsignado {

    private Cosechador cosechador;
    private LocalDate inicio;
    private LocalDate fin;
    private double metaKilos;

    public CosechadorAsignado(Cosechador c, LocalDate inicio, LocalDate fin, double metaKilos) {
        this.cosechador = c;
        this.inicio = inicio;
        this.fin = fin;
        this.metaKilos = metaKilos;
    }

    public Cosechador getCosechador() {
        return cosechador;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public double getMetaKilos() {
        return metaKilos;
    }
}
