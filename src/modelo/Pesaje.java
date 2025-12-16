import java.time.LocalDate;

public class Pesaje {

    private LocalDate fecha;
    private double kilos;
    private double precioKg;
    private Calidad calidad;

    public Pesaje(LocalDate fecha, double kilos, double precioKg, Calidad calidad) {
        this.fecha = fecha;
        this.kilos = kilos;
        this.precioKg = precioKg;
        this.calidad = calidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public double getKilos() {
        return kilos;
    }

    public double getPrecioKg() {
        return precioKg;
    }

    public Calidad getCalidad() {
        return calidad;
    }
}
