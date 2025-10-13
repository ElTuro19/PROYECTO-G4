import java.util.ArrayList;
import java.util.Date;

public class ControlProduccion {
    private ArrayList<Cultivo> cultivos = new ArrayList<>();
    private ArrayList<Supervisor> supervisores = new ArrayList<>();
    private ArrayList<EstadoFenologico> estados = new  ArrayList<>();
    private ArrayList<PlanCosecha> planesDeCosecha = new ArrayList<>();
    private ArrayList<Propietario> propietarios = new ArrayList<>();
    private ArrayList<Persona> personas = new ArrayList<>();
    private ArrayList<Cosechador> cosechadores = new ArrayList<>();
    private ArrayList<Huerto> huertos = new ArrayList<>();

    public boolean createPropietario(String rut, String nombre, String email, String dirParticular, String dirComercial){
        for(Propietario p : propietarios){
            if(p.getRut().equals(rut)){
                return false;
            }
        }
        Propietario nuevo = new Propietario(rut, nombre, email, dirParticular, dirComercial);
        propietarios.add(nuevo);
        return true;
    }

    public boolean createSupervisor(String rut, String nombre, String email, String direccion, String profesion){
        for(Supervisor s : supervisores){
            if(s.getRut().equals(rut)){
                return false;
            }
        }
        Supervisor nuevo = new Supervisor(rut, nombre, email, direccion, profesion);
        supervisores.add(nuevo);
        return true;
    }

    public boolean createCosechador(String rut, String nombre, String email, String direccion, Date fechaNacimiento){
        for(Cosechador c : cosechadores){
            if(c.getRut().equals(rut)){
                return false;
            }
        }
        Cosechador nuevo = new Cosechador(rut, nombre, email, direccion, fechaNacimiento);
        cosechadores.add(nuevo);
        return true;
    }

    public boolean createCultivo(int id, String especie, String variedad, float rendimiento){
        for(Cultivo c : cultivos){
            if(c.getId() == id){
                return false;
            }
        }
        Cultivo nuevo = new Cultivo(id, especie, variedad, rendimiento);
        cultivos.add(nuevo);
        return true;
    }

    public boolean createHuerto(String nombre, float superficie, String ubicacion, String rutPropietario){
        Propietario propietario = null;
        for(Huerto h : huertos){
            if(h.getNombre().equals(nombre)){
                return false;
            }
        }
        for(Propietario p : propietarios){
            if(p.getRut().equals(rutPropietario)){
                propietario = p;
            }
        }
        Huerto nuevo = new Huerto(nombre, superficie, ubicacion, propietario);
        huertos.add(nuevo);
        return true;
    }

    public boolean addCuartelToHuerto(String nombreHuerto, int idCuartel, float superficie, int idCultivo){
        Huerto huerto = null;
        Cultivo cultivo = null;
        for(Huerto h : huertos){
            if(h.getNombre().equals(nombreHuerto)){
                huerto = h;
            }else{
                return false;
            }
        }
        for(Cultivo c : cultivos){
            if(c.getId() == idCultivo){
                cultivo = c;
            }else{
                return false;
            }
        }
        return huerto.addCuartel(idCuartel, superficie, cultivo);
    }

    public boolean createPlanCosecha(int idPlan, String nom, Date inicio, Date finEstim, double meta, float precioBase, String nomHuerto, int idCuartel){
        Cuartel cuartel = null;
        Huerto huerto = null;
        for(Huerto h : huertos){
            if(h.getNombre().equals(nomHuerto)){
                huerto = h;
                cuartel = huerto.getCuartel(idCuartel);
            }
            return false;
        }

        for(PlanCosecha pc : planesDeCosecha){
            if(pc.getId() == idPlan){
                return false;
            }
        }
        PlanCosecha nuevo = new PlanCosecha(idPlan, nom, inicio, finEstim, meta, precioBase, cuartel);
        planesDeCosecha.add(nuevo);
        cuartel.addPlanCosecha(nuevo);
        return true;
    }

    public boolean addCuadrillatoPlan(int idPlan, int idCuad,String nomCuad,String rutSup){
        PlanCosecha planCosecha = null;
        Cuadrilla cuadrilla = null;
        Supervisor supervisor = null;
        for(PlanCosecha pc : planesDeCosecha){
            if(pc.getId() == idPlan){
                planCosecha = pc;
            }
            return false;
        }
        for(Supervisor s : supervisores){
            if(s.getRut().equals(rutSup)){
                supervisor = s;
                if(supervisor.getCuadrilla() != null){
                    return false;
                }
            }
        }
        return planCosecha.addCuadrilla(idCuad,nomCuad,supervisor);
    }

    public boolean addCosechadorToCuadrilla(int idPlan, int idCuadrilla, Date fInicio, Date fFin, double meta, String rutCosechador){
        PlanCosecha planCosecha = null;
        Cosechador cosechador = null;
        for(PlanCosecha pc : planesDeCosecha){
            if(pc.getId() == idPlan) {
                planCosecha = pc;
            }else{
                return false;
            }
        }
        for(Cosechador c : cosechadores){
            if(c.getRut().equals(rutCosechador)){
                cosechador = c;
            }else{
                return false;
            }
        }
        return planCosecha.addCosechadorToCuadrilla(idCuadrilla, fInicio, fFin, meta, cosechador);
    }

    public String[] listCultivos(){
        if(cultivos == null){
            return false;
        }
    }









}
