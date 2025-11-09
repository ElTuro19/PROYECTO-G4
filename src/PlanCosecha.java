import java.util.ArrayList;
import java.time.LocalDate;
public class PlanCosecha {
private int id;
private String nombre;
private Cuartel cuartel;
private LocalDate inicio;
    private LocalDate finEstimado;
    private LocalDate finReal;
private double metaKilos, precioBaseKilo;
private final ArrayList<Cuadrilla> cuadrillas;
    private EstadoPlan estado;
    public PlanCosecha(int id, String nombre, LocalDate inicio, LocalDate finEstimado, double metaKilos, double precioBaseKilo, Cuartel cuartel) {
        this.id = id;
        this.nombre = nombre;
        this.inicio = inicio;
        this.finEstimado = finEstimado;
        this.metaKilos = metaKilos;
        this.precioBaseKilo = precioBaseKilo;
        this.cuadrillas  = new ArrayList<>();
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
    public LocalDate getInicio() {
        return this.inicio;
    }
    public LocalDate getFinEstimado() {
        return this.finEstimado;
    }
    public LocalDate getFinReal() {
        return this.finReal;
    }
    public void setFinReal(LocalDate finReal) {
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
    private Cuadrilla findCuadrillaById(int idCuad){
        for (Cuadrilla cuadrilla : cuadrillas) {if (cuadrilla.getId()==idCuad) {return cuadrilla;}}
        return null;
    }
    public boolean addCuadrilla (int idCuad, String nomCuadrilla, Supervisor supervisor) {
        if (findCuadrillaById(idCuad)!=null) {return false;}
        Cuadrilla cuad1 = new Cuadrilla(idCuad, nomCuadrilla, supervisor, this);
        supervisor.setCuadrilla(cuad1);
        cuadrillas.add(cuad1);
        return true;
    }
    public boolean addCosechadorToCuadrilla (int idCuad, LocalDate flni, LocalDate fFin, double meta, Cosechador cos) {
        for (Cuadrilla cuadrilla : cuadrillas) {
                if (cuadrilla == null) {return false;}
            }
        if (findCuadrillaById(idCuad)==null) {
            return false;
        }
        Cuadrilla ObjSel = findCuadrillaById(idCuad);
        return ObjSel.addCosechador(flni, fFin, meta, cos);
    }

    public Cuadrilla[] getCuadrillas () { return cuadrillas.toArray(new Cuadrilla[0]); }
}
