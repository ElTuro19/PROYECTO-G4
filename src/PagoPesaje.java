import java.util.Date;
import java.util.List;

public class PagoPesaje {
    private int id;
    private Date fecha;
    private List<Pesaje> pesajes;

    public PagoPesaje(int id, Date fecha, List<Pesaje> pesajes) {
        this.id = id;
        this.fecha = fecha;
        this.pesajes = pesajes;

        for (Pesaje p : pesajes){
            p.setPago(this)
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        double total = 0;
        for (Pesaje p : pesajes) {
            total += p.getMonto();
        }
        return total;
    }

    public Pesaje[] getPesajes() {
        return pesajes.toArray(new Pesaje[0]);
    }
}