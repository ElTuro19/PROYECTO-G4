import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;

public class ControlProduccion {
    private ArrayList<Cultivo> cultivos = new ArrayList<>();
    private ArrayList<Supervisor> supervisores = new ArrayList<>();
    private ArrayList<EstadoFenologico> estados = new ArrayList<>();
    private ArrayList<PlanCosecha> planesDeCosecha = new ArrayList<>();
    private ArrayList<Propietario> propietarios = new ArrayList<>();
    private ArrayList<Persona> personas = new ArrayList<>();
    private ArrayList<Cosechador> cosechadores = new ArrayList<>();
    private ArrayList<Huerto> huertos = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public boolean createPropietario(String rut, String nombre, String email, String dirParticular, String dirComercial) {
        for (Propietario p : propietarios) {
            if (p.getRut().equals(rut)) {
                return false;
            }
        }
        Propietario nuevo = new Propietario(rut, nombre, email, dirParticular, dirComercial);
        propietarios.add(nuevo);
        return true;
    }

    public boolean createSupervisor(String rut, String nombre, String email, String direccion, String profesion) {
        for (Supervisor s : supervisores) {
            if (s.getRut().equals(rut)) {
                return false;
            }
        }
        Supervisor nuevo = new Supervisor(rut, nombre, email, direccion, profesion);
        supervisores.add(nuevo);
        return true;
    }

    public boolean createCosechador(String rut, String nombre, String email, String direccion, LocalDate fechaNacimiento) {
        for (Cosechador c : cosechadores) {
            if (c.getRut().equals(rut)) {
                return false;
            }
        }
        Cosechador nuevo = new Cosechador(rut, nombre, email, direccion, fechaNacimiento);
        cosechadores.add(nuevo);
        return true;
    }

    public boolean createCultivo(int id, String especie, String variedad, double rendimiento) {
        for (Cultivo c : cultivos) {
            if (c.getId() == id) {
                return false;
            }
        }
        Cultivo nuevo = new Cultivo(id, especie, variedad, rendimiento);
        cultivos.add(nuevo);
        return true;
    }

    public boolean createHuerto(String nombre, float superficie, String ubicacion, String rutPropietario) {
        Propietario propietario = null;
        for (Huerto h : huertos) {
            if (h.getNombre().equals(nombre)) {
                return false;
            }
        }

        for(Propietario p : propietarios){
            if(p.getRut().equals(rutPropietario)){
                propietario = p;
            }
        }

        if(propietario == null){
            return false;
        }

        Huerto nuevo = new Huerto(nombre, superficie, ubicacion, propietario);
        huertos.add(nuevo);
        return true;
    }

    public boolean addCuartelToHuerto(String nombreHuerto, int idCuartel, float superficie, int idCultivo) {
        Huerto huerto;
        Cultivo cultivo;
        for (Huerto h : huertos){
            if(h.getNombre().equals(nombreHuerto)){
                huerto = h;
                for(Cultivo c : cultivos){
                    if(c.getId() == idCultivo){
                        cultivo = c;
                        Cuartel[] cuarteles = cultivo.getCuarteles();
                        for(int i=0; i< cuarteles.length; i++){
                            if(cuarteles[i].getId() == idCuartel){
                                return false;
                            }
                        }
                        huerto.addCuartel(idCuartel, superficie, cultivo);
                        return true;
                    }
                }
            }
        }
        return true;
    }

    public boolean createPlanCosecha(int idPlan, String nom, LocalDate inicio, LocalDate finEstim, double meta, float precioBase, String nomHuerto, int idCuartel) {
        Cuartel cuartel = null;
        Huerto huerto = null;
        for (Huerto h : huertos) {
            if (h.getNombre().equals(nomHuerto)) {
                huerto = h;
                cuartel = huerto.getCuartel(idCuartel);
                for (PlanCosecha pc : planesDeCosecha) {
                    if (pc.getId() != idPlan) {
                        PlanCosecha nuevo = new PlanCosecha(idPlan, nom, inicio, finEstim, meta, precioBase, cuartel);
                        planesDeCosecha.add(nuevo);
                        cuartel.addPlanCosecha(nuevo);
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean addCuadrillatoPlan(int idPlan, int idCuad, String nomCuad, String rutSup) {
        PlanCosecha planCosecha = null;
        Supervisor supervisor = null;
        for (PlanCosecha pc : planesDeCosecha) if (pc.getId() == idPlan) { planCosecha = pc; break; }
        if (planCosecha == null) return false;
        for (Supervisor s : supervisores) {
            if (s.getRut().equals(rutSup)) {
                supervisor = s;
                if (supervisor.getCuadrilla() != null) {
                    return false;
                }
            }
        }
        return planCosecha.addCuadrilla(idCuad, nomCuad, supervisor);
    }

    public boolean addCosechadorToCuadrilla(int idPlan, int idCuadrilla, LocalDate fInicio, LocalDate fFin, double meta, String rutCosechador) {
        PlanCosecha planCosecha = null;
        Cosechador cosechador = null;
        for (PlanCosecha pc : planesDeCosecha) {
            if (pc.getId() == idPlan) {
                planCosecha = pc;
                for (Cosechador c : cosechadores) {
                    if (c.getRut().equals(rutCosechador)) {
                        cosechador = c;
                        return planCosecha.addCosechadorToCuadrilla(idCuadrilla, fInicio, fFin, meta, cosechador);
                    }
                }
            }
        }
        return false;
    }

    public String[] listCultivos() {
        if (this.cultivos.isEmpty()) {
            return new String[0];
        }

        List<String> resultados = new ArrayList<>();

        for (Cultivo c : this.cultivos) {
            int nroCuarteles = 0;
            for (Huerto h : this.huertos) {
                for (Cuartel cuartel : h.getCuarteles()) {
                    if (cuartel.getCultivo().getId() == c.getId()) {
                        nroCuarteles++;
                    }
                }
            }

            String lineaCultivo = String.format("%d %s %s %.2f %d",
                    c.getId(),
                    c.getEspecie(),
                    c.getVariedad() != null ? c.getVariedad() : "N/A",
                    c.getRendimiento(),
                    nroCuarteles);

            resultados.add(lineaCultivo);
        }
        return resultados.toArray(new String[0]);
    }

    public String[] listHuertos() {
        if (this.huertos.isEmpty()) {
            return new String[0];
        }
        List<String> resultados = new ArrayList<>();
        for (Huerto h : this.huertos) {
        int nroCuarteles = h.getCuarteles().size();

        String lineaHuertos = String.format("%-15s %-15s %-20s %-20s %15s %15d",
                h.getNombre(),
                h.getSuperficie(),
                h.getUbicacion(),
                h.getPropietario().getRut(),
                h.getPropietario().getNombre(),
                nroCuarteles
                );
                resultados.add(lineaHuertos);
        }
        return resultados.toArray(new String[0]);
    }

    public String[] listPropietarios() {
        if (this.propietarios.isEmpty()) {
            return new String[0];
        }
       List<String> resultados = new ArrayList<>();
        for(Propietario p : this.propietarios) {
            String lineaPropietarios = String.format("%-25s %-20s %-15s %-10s %-15s %-15d",
                    p.getRut(),
                    p.getNombre(),
                    p.getDireccion(),
                    p.getEmail(),
                    p.getDireccionComercial(),
                    p.getHuertos().size()
            );
            resultados.add(lineaPropietarios);
        }
        return resultados.toArray(new String[0]);
    }

    public String[] listSupervisores(){
        if (this.supervisores.isEmpty()) {
            return new String[0];
        }
        List<String> resultados = new ArrayList<>();
        for(Supervisor s : this.supervisores) {
            String nombreCuadrilla = s.getCuadrilla() != null ?  s.getCuadrilla().getNombre() : "N/A";
            String lineaSupervisores = String.format("%-15s %-15s %-20s %-20s %15s %15s",
                    s.getRut(),
                    s.getNombre(),
                    s.getDireccion(),
                    s.getEmail(),
                    s.getProfesion(),
                    nombreCuadrilla);
            resultados.add(lineaSupervisores);
        }
        return resultados.toArray(new String[0]);
    }

    public String[] listCosechadores(){
        if(this.cosechadores.isEmpty()) {
            return new String[0];
        }
        List<String> resultados = new ArrayList<>();
        for(Cosechador c : this.cosechadores) {
            int nroCuadrillas = c.getCuadrillas().length;
            String lineaCosechadores = String.format("%-15s %-15s %-20s %-20s %15s %15d",
                    c.getRut(),
                    c.getNombre(),
                    c.getDireccion(),
                    c.getEmail(),
                    c.getFechaNacimiento(),
                    nroCuadrillas);
            resultados.add(lineaCosechadores);
        }
        return resultados.toArray(new String[0]);
    }

    public String[] listPlanesCosecha(){
        if(this.planesDeCosecha.isEmpty()) {
            return new String[0];
        }
        List<String> resultados = new ArrayList<>();
        for(PlanCosecha pc : this.planesDeCosecha) {
            int nroCuadrillas = pc.getCuadrillas().length;
            String lineaPlanesCosecha = String.format("%-15d %-15s %-20td %-20td %15f %15f %20s %20d %25s %25d",
                    pc.getId(),
                    pc.getNombre(),
                    pc.getInicio(),
                    pc.getFinEstimado(),
                    pc.getMetaKilos(),
                    pc.getPrecioBaseKilo(),
                    pc.getEstado(),
                    pc.getCuartel().getId(),
                    pc.getCuartel().getHuerto().getNombre(),
                    nroCuadrillas
                    );
            resultados.add(lineaPlanesCosecha);
        }
        return resultados.toArray(new String[0]);
    }
}

