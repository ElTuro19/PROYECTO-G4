package modelo;

import utilidades.EstadoPlan;
import utilidades.GestionHuertosException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class PlanCosecha implements Serializable {
    private int id;
    private String nombre;
    private Cuartel cuartel;
    private LocalDate inicio;
    private LocalDate finEstimado;
    private LocalDate finReal;
    private double metaKilos;
    private double precioBaseKilo;

    private final ArrayList<Cuadrilla> cuadrillas;
    private EstadoPlan estado = EstadoPlan.PLANIFICADO;

    public PlanCosecha(int id, String nombre, LocalDate inicio, LocalDate finEstimado,
                       double metaKilos, double precioBaseKilo, Cuartel cuartel) {
        this.id = id;
        this.nombre = nombre;
        this.inicio = inicio;
        this.finEstimado = finEstimado;
        this.metaKilos = metaKilos;
        this.precioBaseKilo = precioBaseKilo;
        this.cuadrillas  = new ArrayList<>();
        this.cuartel = cuartel;
    }

    public int getId() { return this.id; }

    public String getNombre() { return this.nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public LocalDate getInicio() { return this.inicio; }

    public LocalDate getFinEstimado() { return this.finEstimado; }

    public LocalDate getFinReal() { return this.finReal; }

    public void setFinReal(LocalDate finReal) { this.finReal = finReal; }

    public double getMetaKilos() { return this.metaKilos; }

    public void setMetaKilos(double metaKilos) { this.metaKilos = metaKilos; }

    public double getPrecioBaseKilo() { return this.precioBaseKilo; }

    public void setPrecioBaseKilo(double precioBaseKilo) { this.precioBaseKilo = precioBaseKilo; }

    public EstadoPlan getEstado() { return this.estado; }

    public boolean setEstado(EstadoPlan nuevo) {
        if (nuevo == null) return false;
        if (nuevo == estado) return false;

        switch (estado) {
            case PLANIFICADO -> {
                if (nuevo == EstadoPlan.EJECUTANDO || nuevo == EstadoPlan.CANCELADO) {
                    estado = nuevo;
                    return true;
                }
            }
            case EJECUTANDO -> {
                if (nuevo == EstadoPlan.CERRADO) {
                    estado = nuevo;
                    return true;
                }
            }
            default -> { /* CERRADO/CANCELADO */ }
        }
        return false;
    }

    public Cuartel getCuartel() { return this.cuartel; }

    private Cuadrilla findCuadrillaById(int idCuad){
        for (Cuadrilla cuadrilla : cuadrillas) {
            if (cuadrilla.getId() == idCuad) return cuadrilla;
        }
        return null;
    }

    public boolean addCuadrilla(int idCuad, String nomCuadrilla, Supervisor supervisor) throws GestionHuertosException {
        if (findCuadrillaById(idCuad) != null) {
            throw new GestionHuertosException("Ya existe una cuadrilla con el id indicado");
        }
        if (supervisor == null) {
            throw new GestionHuertosException("No existe Supervisor con el rut Indicado");
        }
        this.cuadrillas.add(new Cuadrilla(idCuad, nomCuadrilla, supervisor, this));
        return true;
    }

    public boolean addCosechadorToCuadrilla(int idCuad, LocalDate fIni, LocalDate fFin, double meta, Cosechador cos)
            throws GestionHuertosException {

        Cuadrilla objSel = findCuadrillaById(idCuad);
        if (objSel == null) return false;

        objSel.addCosechador(fIni, fFin, meta, cos); // aquí se valida y puede lanzar excepción
        return true;
    }

    public Cuadrilla[] getCuadrillas () { return cuadrillas.toArray(new Cuadrilla[0]); }
}
