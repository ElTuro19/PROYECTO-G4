package modelo;

import java.util.Date;
import java.util.List;

public class PagoPesaje {

    private int id;
    private Date fecha;
    private List<Pesaje> pesajes;

    public PagoPesaje(int id, Date f, List<Pesaje> p) {
        this.id = id;
        fecha = f;
        pesajes = p;
    }

    public int getId() { return id; }
    public Date getFecha() { return fecha; }

    public double getMonto() {
        return pesajes.stream().mapToDouble(Pesaje::getMonto).sum();
    }

    public Pesaje[] getPesajes() {
        return pesajes.toArray(new Pesaje[0]);
    }
}
