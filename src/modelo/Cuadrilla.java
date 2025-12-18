package modelo;



import java.io.Serializable;
import excepciones.GestionHuertosException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cuadrilla implements Serializable {

    private static final long serialVersionUID = 1L;



    private static final int MAX_COSECHADORES = 10;
    private int id;
    private String nombre;
    private Supervisor supervisor;
    private PlanCosecha planCosecha;
    private ArrayList<CosechadorAsignado> cosechadores = new ArrayList<>();

    public Cuadrilla(int id, String nom, Supervisor sup, PlanCosecha plan) {
        this.id = id;
        this.nombre = nom;
        supervisor = sup;
        planCosecha = plan;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Supervisor getSupervisor() { return supervisor; }
    public PlanCosecha getPlanCosecha() { return planCosecha; }

    public void addCosechador(LocalDate i, LocalDate f, double m, Cosechador c) throws GestionHuertosException {
        for (CosechadorAsignado ca : cosechadores) {
            if (ca.getCosechador().getRut().equals(c.getRut())) {
                throw new GestionHuertosException("Ya existe un cosechador en la cuadrilla con el mismo rut del cosechador recibido como parámetro");
            }
        }
        if (cosechadores.size() >= MAX_COSECHADORES) {
            throw new GestionHuertosException("No es posible agregar el nuevo cosechador porque ya se alcanzó el máximo número de cosechadores en una cuadrilla");
        }
        cosechadores.add(new CosechadorAsignado(c, i, f, m));
    }

    public CosechadorAsignado[] getCosechadores() {
        return cosechadores.toArray(new CosechadorAsignado[0]);
    }
}
