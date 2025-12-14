package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class Cosechador extends Persona {

    private LocalDate fechaNacimiento;
    private ArrayList<CosechadorAsignado> asignaciones = new ArrayList<>();

    public Cosechador(String rut, String nombre, String email, String direccion, LocalDate fechaNacimiento) {
        super(rut, nombre, email, direccion);
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public void addCuadrilla(CosechadorAsignado ca) {
        asignaciones.add(ca);
    }

    public Optional<CosechadorAsignado> getAsignacion(int idPlan, int idCuadrilla) {
        return asignaciones.stream()
                .filter(a -> a.getCuadrilla().getId() == idCuadrilla
                        && a.getCuadrilla().getPlanCosecha().getId() == idPlan)
                .findFirst();
    }

    public Cuadrilla[] getCuadrillas() {
        return asignaciones.stream()
                .map(CosechadorAsignado::getCuadrilla)
                .toArray(Cuadrilla[]::new);
    }
}
