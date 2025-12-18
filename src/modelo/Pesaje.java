package modelo;
/// utilidades
import utilidades.Calidad;
import utilidades.EstadoFenologico;
import utilidades.EstadoPlan;
import utilidades.GestionHuertosException;
import utilidades.Rut;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Pesaje implements Serializable {
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
        if (cosAsign != null &&
                cosAsign.getCuadrilla() != null &&
                cosAsign.getCuadrilla().getPlanCosecha() != null) {
            return cosAsign.getCuadrilla().getPlanCosecha().getPrecioBaseKilo();
        }
        return 0.0;
    }

    public double getMonto() {
        return cantidadKg * getPrecioKg();
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
