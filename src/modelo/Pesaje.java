package modelo;

import utilidades.Calidad;
import java.time.LocalDateTime;

public class Pesaje {

    private int id;
    private double cantidadKg;
    private Calidad calidad;
    private LocalDateTime fechaHora;
    private double precioKg;
    private CosechadorAsignado asignacion;
    private PagoPesaje pago;

    public Pesaje(int id, double cant, Calidad cal, LocalDateTime f, CosechadorAsignado a) {
        this.id = id;
        cantidadKg = cant;
        calidad = cal;
        fechaHora = f;
        asignacion = a;
    }

    public int getId() { return id; }
    public double getCantidadKg() { return cantidadKg; }
    public Calidad getCalidad() { return calidad; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setPrecioKg(double p) { precioKg = p; }
    public double getMonto() { return cantidadKg * precioKg; }
    public boolean isPagado() { return pago != null; }
    public void setPago(PagoPesaje p) { pago = p; }
    public PagoPesaje getPagoPesaje() { return pago; }
    public CosechadorAsignado getCosechadorAsignado() { return asignacion; }
}
