import java.util.ArrayList;
import java.util.Date;
public class PlanCosecha {
private int id;
private String nombre;
private Date inicio, fecha, finEstimado, finReal;
private double metaKilos, precioBaseKilo;
private EstadoPlan estado;
    public PlanCosecha(int id, String nombre, Date inicio, Date finEstimado, double metaKilos, double precioBaseKilo, Cuartel cuartel) {
        this.id = id;
        this.nombre = nombre;
        this.inicio = inicio;
        this.finEstimado = finEstimado;
        this.metaKilos = metaKilos;
        this.precioBaseKilo = precioBaseKilo;
        this.cuartel = cuartel;
    }
    public int getId() {
        return this.id;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Date getInicio() {
        return this.inicio;
    }
    public Date getFinEstimado() {
        return this.finEstimado;
    }
    public Date getFinReal() {
        return this.finReal;
    }
    public void setFinReal(Date finReal) {
        this.finReal = finReal;
    }
    public double getMetaKilos() {
        return this.metaKilos;
    }
    public void setMetaKilos(double metaKilos) {
        this.metaKilos = metaKilos;
    }
    public double getPrecioBaseKilo() {
        return this.precioBaseKilo;
    }
    public void setPrecioBaseKilo(double precioBaseKilo) {
        this.precioBaseKilo = precioBaseKilo;
    }
    public EstadoPlan getEstado() {
        return this.estado;
    }
    public void setEstado(EstadoPlan estado) {
        this.estado = estado;
    }
    public Cuartel getCuartel() {
        return this.cuartel;
    }
    public boolean addCuadrilla (int idCuad, String nomCuadrilla, Supervisor supervisor) {

    }
    public boolean addCosechadorToCuadrilla (int idCuad, Date flni,Date fFin, double meta, Cosechador cos) {

    }
    public Cuadrilla[] getCuadrillas () {

    }
}
