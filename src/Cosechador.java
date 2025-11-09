import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Cosechador extends Persona{
    //Atributo
    private LocalDate fechaNacimiento;
    //Guarda las asignaciones de cosechador en las distintas cuadrillas
    private final List<CosechadorAsignado>asignaciones = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Cosechador(String rut, String nombre, String email, String direccion, LocalDate fechaNacimiento) {
        super(rut, nombre, email, direccion);
        this.fechaNacimiento = fechaNacimiento;

    }

    public LocalDate getFechaNacimiento(){
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fNac) {
        this.fechaNacimiento = fNac;
    }
    //Agregar un cosechador a la cuadrilla
    public void addCuadrilla(CosechadorAsignado cosAs){
        if (cosAs == null) return;
        asignaciones.add(cosAs);
    }
    //Muestra las cuadrillas a las que está asociado
    public Cuadrilla[] getCuadrillas(){
        return asignaciones.toArray(new Cuadrilla[0]);
    }
}
