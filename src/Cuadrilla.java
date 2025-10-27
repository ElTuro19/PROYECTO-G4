import java.time.LocalDate;
import java.util.ArrayList;

public class Cuadrilla {

    private int id;
    private String nombre;
    private int maximoCosechadores;

    private Supervisor supervisor;
    private PlanCosecha planCosecha;
    private ArrayList<Cosechador>cosechadores;

    public Cuadrilla(int id, String nom, Supervisor sup, PlanCosecha plan){

        this.id = id;
        this.nombre = nom;
        this.supervisor = sup;
        this.planCosecha = plan;
        this.cosechadores = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Supervisor getSupervisor() {
        return supervisor;
    }

    public PlanCosecha getPlanCosecha() {
        return planCosecha;
    }

    public boolean addCosechador(LocalDate fIni, LocalDate fFin, double meta, Cosechador cos){

       if (cosechadores.size() < maximoCosechadores){
           return cosechadores.add(cos);
       }
       return false;
    }
    public Cosechador[] getCosechadores(){
        return cosechadores.toArray(new Cosechador[0]);
    }
    public int getMaximoCosechadores(){
        return maximoCosechadores;
    }
    public void setMaximoCosechadores(int max){
        this.maximoCosechadores = max;
    }
}
