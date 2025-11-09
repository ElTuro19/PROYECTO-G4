import java.util.ArrayList;
public class Cultivo {
    private int id;
    private String especie;
    private String variedad;
    private float rendimiento;

    private ArrayList<Cuartel>cuarteles = new ArrayList<>();


    public Cultivo(int id, String esp, String var, double rend) {
        this.id = id;
        this.especie = esp;
        this.variedad = var;
        this.rendimiento = (float) rend;
    }

    public int getId() {
        return id;
    }

    public String getEspecie() {
        return especie;
    }

    public String getVariedad() {
        return variedad;
    }

    public float getRendimiento() {
        return rendimiento;
    }

    public void setRendimiento(float rendimiento) {
        this.rendimiento = rendimiento;
    }

    public boolean addCuartel(Cuartel cuartel){
        for(Cuartel c:cuarteles){
            if(c.getId()==cuartel.getId()){
                return false;
            }
        }
        return cuarteles.add(cuartel);
    }

    public Cuartel[] getCuarteles(){
        return cuarteles.toArray(new Cuartel[0]);
    }
}
