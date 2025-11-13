package modelo;
/// utilidades
import utilidades.Calidad;
import utilidades.EstadoFenologico;
import utilidades.EstadoPlan;
import utilidades.GestionHuertosException;
import utilidades.Rut;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cosechador extends Persona {
    //Atributo
    private LocalDate fechaNacimiento;
    //Guarda las asignaciones de cosechador en las distintas cuadrillas
    private final List<CosechadorAsignado> asignaciones = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Cosechador(String rut, String nombre, String email, String direccion, LocalDate fechaNacimiento) {
        super(rut, nombre, email, direccion);
        this.fechaNacimiento = fechaNacimiento;

    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fNac) {
        this.fechaNacimiento = fNac;
    }

    //Agregar un cosechador a la cuadrilla
    public void addCuadrilla(CosechadorAsignado cosAs) {
        if (cosAs == null) return;
        asignaciones.add(cosAs);
    }

    //Muestra las cuadrillas a las que está asociado
    public Cuadrilla[] getCuadrillas() {
        Cuadrilla[] cuadrillas = new Cuadrilla[asignaciones.size()];
        for (int i = 0; i < asignaciones.size(); i++) {
            cuadrillas[i] = asignaciones.get(i).getCuadrilla();
        }
        return cuadrillas;
    }

    public Optional<CosechadorAsignado> getAsignacion(int idPlan, int idCuadrilla) {
        for (CosechadorAsignado cosAsignado : asignaciones) {
            if (cosAsignado.getCuadrilla().equals(idCuadrilla)) {
                if (cosAsignado.getCuadrilla().getPlanCosecha().equals(idPlan)) {
                    return  Optional.of(cosAsignado);
                }
            }
        }
        return Optional.empty();
    }



public void addAsignacion(CosechadorAsignado asignacion) {
        asignaciones.add(asignacion);
    }
}
