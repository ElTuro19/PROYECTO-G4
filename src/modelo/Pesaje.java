import java.time.DateTimeException;
import java.time.LocalDateTime;

public class Pesaje {
    private int id;
    private double cantidadKg;
    private Calidad cal;
    private LocalDateTime fechaHora;
    private double precioKg;
    private CosechadorAsignado cosAsign;
    private PagoPesaje pago;

    public Pesaje(int id, double cant, Calidad cal, LocalDateTime fechaHora, CosechadorAsignado cosAsign) {
        this.id = id;
        this.cantidadKg = cant;
        this.cal = cal;
        this.fechaHora = fechaHora;
        this.cosAsign = cosAsign;
    }

    public int getId() {
        return id;
    }

    public double getCantidadKg() {
        return cantidadKg;
    }

    public Calidad getCalidad() {
        return cal;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public double getPrecioKg() {
        return precioKg;
    }

    public double getMonto() {
        return cantidadKg * precioKg;
    }

    public CosechadorAsignado getCosechadorAsignado() {
        return cosAsign;
    }

    public void setPago(PagoPesaje pago) {
        this.pago = pago;
    }

    public boolean isPagado() {
        return pago != null;
    }

    public PagoPesaje getPagoPesaje() {
        return pago;
    }
}
