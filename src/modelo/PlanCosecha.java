import java.time.LocalDate;

public class PlanCosecha {

    private String nombre;
    private LocalDate inicio;
    private LocalDate finEstimado;
    private double metaKilos;
    private EstadoPlan estado;

    public PlanCosecha(String nombre, LocalDate inicio, LocalDate finEstimado, double metaKilos) {
        this.nombre = nombre;
        this.inicio = inicio;
        this.finEstimado = finEstimado;
        this.metaKilos = metaKilos;
        this.estado = EstadoPlan.PLANIFICADO;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public LocalDate getFinEstimado() {
        return finEstimado;
    }

    public double getMetaKilos() {
        return metaKilos;
    }

    public EstadoPlan getEstado() {
        return estado;
    }

    public void setEstado(EstadoPlan estado) {
        this.estado = estado;
    }
}
