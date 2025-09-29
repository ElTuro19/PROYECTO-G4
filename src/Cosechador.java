import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cosechador {

    private Date fechaNacimiento;

    private final List<CosechadorAsignado>asignaciones = new ArrayList<>();

    public Date getFechaNacimiento(){
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fNac) {
        this.fechaNacimiento = fNac;
    }

    public void addCuadrilla(CosechadorAsignado cosAs){
        if (cosAs == null) return;
        asignaciones.add(cosAs);
    }

    public Cuadrilla[] getCuadrillas(){
        return asignaciones.stream().map(CosechadorAsignado::getCuadrilla).toArray(Cuadrilla[]::new);
    }
}
