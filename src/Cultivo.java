public class Cultivo {
    private int id;
    private String especie;
    private String variedad;
    private float rendimiento;

    public Cultivo(int id, String especie, String variedad, float rendimiento) {
        this.id = id;
        this.especie = especie;
        this.variedad = variedad;
        this.rendimiento = rendimiento;
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
}
