import java.util.Date;
import java.util.List;

public class PagoPesaje {
    private int id;
    private Date fecha;
    private List<Pesaje> pesaje;

    public PagoPesaje(int id, Date fecha, List<Pesaje> pesaje) {
        this.id = id;
        this.fecha = fecha;
        this.pesaje = pesaje;
    }

    public int getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public double getMonto() {
        double monto = 0;
        for (Pesaje p : pesaje) {
            monto += p.getMonto();
        }
        return monto;
    }

    public Pesaje[] getPesajes() {
        return pesaje.toArray(new Pesaje[0]);
    }
}