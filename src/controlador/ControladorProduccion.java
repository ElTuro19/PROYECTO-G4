package controlador;

import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeFormatter;
/// modelo
import modelo.Cosechador;
import modelo.CosechadorAsignado;
import modelo.Cuadrilla;
import modelo.Cuartel;
import modelo.Cultivo;
import modelo.Huerto;
import modelo.PagoPesaje;
import modelo.Persona;
import modelo.Pesaje;
import modelo.PlanCosecha;
import modelo.Propietario;
import modelo.Supervisor;
/// utilidades
import utilidades.*;

public class ControladorProduccion {
    private ArrayList<Cultivo> cultivos = new ArrayList<>();
    private ArrayList<Supervisor> supervisores = new ArrayList<>();
    private ArrayList<EstadoFenologico> estados = new ArrayList<>();
    private ArrayList<PlanCosecha> planesDeCosecha = new ArrayList<>();
    private ArrayList<Propietario> propietarios = new ArrayList<>();
    private ArrayList<Persona> personas = new ArrayList<>();
    private ArrayList<Cosechador> cosechadores = new ArrayList<>();
    private ArrayList<Huerto> huertos = new ArrayList<>();
    private ArrayList<Pesaje> pesajes = new ArrayList<>();
    private ArrayList<PagoPesaje> Ppesajes = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static ControladorProduccion instance = null;

    private void ControlProduccion() {}

    public static ControladorProduccion getInstance() {
        if (instance == null)
            instance = new ControladorProduccion();
        return instance;
    }

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
        propietario.addHuerto(nuevo);
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
                        Cuartel nuevo = new Cuartel(idCuartel, superficie, cultivo, huerto);
                        huerto.addCuartel(idCuartel, superficie, cultivo);
                        cultivo.addCuartel(nuevo);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean createPlanCosecha(int idPlan, String nom, LocalDate inicio, LocalDate finEstim, double meta, float precioBase, String nomHuerto, int idCuartel) {
        Huerto huerto = null;
        for (Huerto h : huertos) {
            if (h.getNombre().equalsIgnoreCase(nomHuerto)) {
                huerto = h;
                break;
            }
        }
        if (huerto == null) {
            return false;
        }


        Cuartel cuartel = huerto.getCuartel(idCuartel);
        if (cuartel == null) {
            return false;
        }


        for (PlanCosecha pc : planesDeCosecha) {
            if (pc.getId() == idPlan) {
                return false;
            }
        }
        PlanCosecha nuevo = new PlanCosecha(idPlan, nom, inicio, finEstim, meta, precioBase, cuartel);
        cuartel.addPlanCosecha(nuevo);
        planesDeCosecha.add(nuevo);
        return true;
    }

    public boolean addCuadrillatoPlan(int idPlan, int idCuad, String nomCuad, String rutSup) {

        PlanCosecha plan = null;
        for (PlanCosecha pc : planesDeCosecha) {
            if (pc.getId() == idPlan) {
                plan = pc;
                break;
            }
        }
        if (plan == null) return false;


        Supervisor supervisor = null;
        for (Supervisor sup : supervisores) {
            if (sup.getRut().equals(rutSup)) {
                supervisor = sup;
                break;
            }
        }
        if (supervisor == null) return false;

        boolean ok = plan.addCuadrilla(idCuad, nomCuad, supervisor);
        if (!ok) return false;


        for (Cuadrilla c : plan.getCuadrillas()) {
            if (c.getId() == idCuad) {
                supervisor.setCuadrilla(c);
                break;
            }
        }

        return true;
    }
    public boolean addCosechadorToCuadrilla(int idPlan, int idCuadrilla, LocalDate fInicio, LocalDate fFin, double meta, String rutCosechador) {
        PlanCosecha plan = null;
        for (PlanCosecha pc : planesDeCosecha) {
            if (pc.getId() == idPlan) {
                plan = pc;
                break;
            }
        }
        if (plan == null) return false;

        Cuadrilla cuadrilla = null;
        for (Cuadrilla c : plan.getCuadrillas()) {
            if (c.getId() == idCuadrilla) {
                cuadrilla = c;
                break;
            }
        }
        if (cuadrilla == null) return false;

        Cosechador cosechador = null;
        for (Cosechador c : cosechadores) {
            if (c.getRut().equals(rutCosechador)) {
                cosechador = c;
                break;
            }
        }
        if (cosechador == null) return false;


        CosechadorAsignado ca = new CosechadorAsignado(fInicio, fFin, meta, cuadrilla, cosechador);
        cosechador.addCuadrilla(ca);

        boolean ok = cuadrilla.addCosechador(fInicio, fFin, meta, cosechador);
        return ok;
    }

    public String[] listCultivos() {
        if (this.cultivos.isEmpty()) {
            return new String[0];
        }

        List<String> resultados = new ArrayList<>();

        for (Cultivo c : this.cultivos) {
            String lineaCultivo = String.format("%-10d %-20s %-15s %-10.2f %-5d",
                    c.getId(),
                    c.getEspecie(),
                    c.getVariedad() != null ? c.getVariedad() : "N/A",
                    c.getRendimiento(),
                    c.getCuarteles().length
                    );

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
        String lineaHuertos = String.format("%-20s %-20s %-20s %-20s %-20s %-20d",
                h.getNombre(),
                h.getSuperficie(),
                h.getUbicacion(),
                h.getPropietario().getRut(),
                h.getPropietario().getNombre(),
                h.getCuarteles().size()
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
            String lineaPropietarios = String.format("%-15s %-15s %-20s %-25s %-15s %15d",
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
    //// codigo avanze 2

    public void addPesaje (int id, Rut rutCosechador, int idPlan, int idCuadrilla, float cantidadKg, Calidad calidad) throws GestionHuertosException {
        for (Pesaje p : pesajes) {
            if (p.getId() == id) {
                throw new GestionHuertosException("Ya existe un pesaje con id indicado");
            }
        }
        Optional<Cosechador> C = findCosechadorByRut(rutCosechador);
        if (C.isEmpty()) {throw new GestionHuertosException("No existe un cosechador con el rut indicado");}
        Optional<PlanCosecha> PC = findPlanCosechaById(idPlan);
        if (PC.isEmpty()) {throw new GestionHuertosException("No existe un plan con el id indicado");}
        PlanCosecha pC = PC.get();
        if (pC.getEstado()!=EstadoPlan.EJECUTANDO) {throw new GestionHuertosException("El plan no se encuentra en estado “en ejecución”");}
        Cosechador cos = C.get();
        Cuadrilla[] cuad = cos.getCuadrillas();
        Cuadrilla cuadsel = null;
        boolean verif = false;
        for (Cuadrilla c : cuad) {if (c.getId() == idCuadrilla) {cuadsel=c; verif = true;}}
        if (cuadsel.getPlanCosecha()!=pC || verif==false) {throw new GestionHuertosException("El cosechador no tiene una asignación a una cuadrilla con el id indicado en el plan con el id señalado”");}
        /// añadir aqui penultima excepcion faltante


        ///
        Cuartel cuart = pC.getCuartel();
        if (cuart.getEstado()!=EstadoFenologico.COSECHA) {throw new GestionHuertosException("El cuartel no se encuentra en estado fenológico cosecha");}
    }

    public void changeEstadoCuartel (String nombreHuerto, int idCuartel, EstadoFenologico estado) throws GestionHuertosException {
        /// preguntar al profesor mañana si esta bien
        Optional<Huerto> H = findHuertoByNombre(nombreHuerto);
        if (H.isEmpty()) {
            throw new GestionHuertosException("No existe huerto con el nombre indicado");
        }

        Huerto HH = H.get();

        if (HH.getCuartel(idCuartel) == null) {
            throw new GestionHuertosException("No existe en el huerto un cuartel con el id indicado" + nombreHuerto);
        }

        Cuartel C = HH.getCuartel(idCuartel);
        C.setEstado(estado);
    }



    public Optional<Persona> findPropietarioByRut (Rut rut) {
        for (Propietario p : propietarios) {
            if (p.getRut().equals(rut)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
    public Optional<Supervisor> findSupervisorByRut (Rut rut) {
        for (Supervisor p : supervisores) {
            if (p.getRut().equals(rut)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
    public Optional<Cosechador> findCosechadorByRut (Rut rut) {
        for (Cosechador p : cosechadores) {
            if (p.getRut().equals(rut)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
    public Optional<Cultivo> findCultivoById (int id) {
        for (Cultivo p : cultivos) {
            if (p.getId()==id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
    public Optional<Huerto> findHuertoByNombre(String nombre) {
        for (Huerto p : huertos) {
            if (p.getNombre().equals(nombre)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
    public Optional<PlanCosecha> findPlanCosechaById (long id) {
        for (PlanCosecha p : planesDeCosecha) {
            if (p.getId()==id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    public Optional<PlanCosecha> findPesajeById(int idPesaje) {
        for (PlanCosecha plan : planesDeCosecha) {
            for (Cuadrilla cua : plan.getCuadrillas()) {
                for (Pesaje pesaje : cua.getPesajes()) {
                    if (pesaje.getId() == idPesaje) {
                        return Optional.of(plan);
                    }
                }
            }
        }
        return Optional.empty();
    }


    public Optional<PagoPesaje> findPagoPesajeById (int id) {
        for (PagoPesaje p : Ppesajes) {
            if (p.getId()==id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

}

