package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cosechador extends Persona {
    private LocalDate fechaNacimiento;
    private List<CosechadorAsignado> asignaciones;

    public Cosechador(Rut rut, String nombre, String email, String direccion, LocalDate fechaNacimiento) {
        super(rut, nombre, email, direccion);
        this.fechaNacimiento = fechaNacimiento;
        this.asignaciones = new ArrayList<>();
    }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }

    public CosechadorAsignado[] getAsignaciones() {
        return asignaciones.toArray(new CosechadorAsignado[0]);
    }

    public Cuadrilla[] getCuadrillas() {
        return asignaciones.stream()
                .map(CosechadorAsignado::getCuadrilla)
                .toArray(Cuadrilla[]::new);
    }

    public java.util.Optional<CosechadorAsignado> getAsignacion(int idPlan, int idCuadrilla) {
        return asignaciones.stream()
                .filter(asig -> asig.getCuadrilla().getId() == idCuadrilla &&
                        asig.getCuadrilla().getPlan().getId() == idPlan)
                .findFirst();
    }

    public void addAsignacion(CosechadorAsignado asignacion) {
        asignaciones.add(asignacion);
    }
}