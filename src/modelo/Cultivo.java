package modelo;
public class Cultivo {
    private final int id;
    private final String especie;
    private final String variedad;
    private final String signo;
    private final float rendimiento;

    public Cultivo(int id, String especie, String variedad, String signo, float rendimiento){
        this.id = id; this.especie = especie; this.variedad = variedad; this.signo = signo; this.rendimiento = rendimiento;
    }

    public int getId(){ return id; }
    public String getEspecie(){ return especie; }
    public String getVariedad(){ return variedad; }
    public String getSigno(){ return signo; }
    public float getRendimiento(){ return rendimiento; }
    @Override public String toString(){ return especie + " " + variedad; }
}
