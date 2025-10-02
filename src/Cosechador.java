import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cosechador {
    //Atributo
    private Date fechaNacimiento;
    //Guarda las asignaciones de cosechador en las distintas cuadrillas
    private final List<CosechadorAsignado>asignaciones = new ArrayList<>();

    public Date getFechaNacimiento(){
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fNac) {
        this.fechaNacimiento = fNac;
    }
    //Agregar un cosechador a la cuadrilla
    public void addCuadrilla(CosechadorAsignado cosAs){
        if (cosAs == null) return;
        asignaciones.add(cosAs);
    }
    //Muestra las cuadrillas a las que está asociado
    public Cuadrilla[] getCuadrillas(){
        return asignaciones.stream().map(CosechadorAsignado::getCuadrilla).toArray(Cuadrilla[]::new);
    }
}
