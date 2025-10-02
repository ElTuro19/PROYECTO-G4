import java.util.ArrayList;
import java.util.Date;

public class Cuadrilla {

    //Atributos
    private int id;
    private String nombre;
    private int maximoCosechadores;

    //Asignaciones
    private Supervisor supervisor;
    private PlanCosecha planCosecha;
    private ArrayList<Cosechador>cosechadores;
    //Constructor
    public Cuadrilla(int id, String nom, Supervisor sup, PlanCosecha plan){

        this.id = id;
        this.nombre = nom;
        this.supervisor = sup;
        this.planCosecha = plan;
        this.cosechadores = new ArrayList<>();
    }
    //get y set
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

    public boolean addCosechador(Date fIni, Date fFin, double meta, Cosechador cos){

       if (cosechadores.size() < maximoCosechadores){
           cosechadores.add(cos);
           return true;
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
