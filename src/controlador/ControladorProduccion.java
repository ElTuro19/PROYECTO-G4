package controlador;

import java.io.*;
import java.text.SimpleDateFormat;
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
import persistencia.GestionHuertosIO;
import utilidades.*;
import vista.GestionHuertosUI;

public class ControladorProduccion{
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
    private GestionHuertosIO IO = new GestionHuertosIO();
    private static ControladorProduccion instance = null;

    private ControladorProduccion() {
        IO = new GestionHuertosIO();
    }

    public static ControladorProduccion getInstance() {
        if (instance == null)
            instance = new ControladorProduccion();
        return instance;
    }

    ///preguntar sobre excepciones
    public boolean createPropietario(String rut, String nombre, String email, String dirParticular, String dirComercial) throws GestionHuertosException {
        Optional<Persona> verif = findPropietarioByRut(Rut.of(rut));
        if (verif.isPresent()) {throw new GestionHuertosException("Ya existe un propietario con el rut indicado");}

        if (rut == null || nombre == null || email == null ||
                dirParticular == null || dirComercial == null) {
            return false;
        }

        if (rut.isEmpty() || nombre.isEmpty() || email.isEmpty() ||
                dirParticular.isEmpty() || dirComercial.isEmpty()) {
            return false;
        }

        for (Propietario p : propietarios) {
            if (p.getRut().equals(rut)) {
                return false;
            }
        }
        Propietario nuevo = new Propietario(rut, nombre, email, dirParticular, dirComercial);
        propietarios.add(nuevo);
        return true;

    }

    public boolean createSupervisor(String rut, String nombre, String email, String direccion, String profesion) throws GestionHuertosException {
        Optional<Supervisor> verif = findSupervisorByRut(Rut.of(rut));
        if (verif.isPresent()) {throw new GestionHuertosException("Ya existe un supervisor con el rut indicado");}
        for (Supervisor s : supervisores) {
            if (s.getRut().equals(rut)) {
                return false;
            }
        }
        Supervisor nuevo = new Supervisor(rut, nombre, email, direccion, profesion);
        supervisores.add(nuevo);
        return true;

    }

    public boolean createCosechador(String rut, String nombre, String email, String direccion, LocalDate fechaNacimiento) throws GestionHuertosException {
        Optional<Cosechador> verif = findCosechadorByRut(Rut.of(rut));
        if (!verif.isEmpty()) {throw new GestionHuertosException("Ya existe un cosechador con el rut indicado");}
        for (Cosechador c : cosechadores) {
            if (c.getRut().equals(rut)) {
                return false;
            }
        }
        Cosechador nuevo = new Cosechador(rut, nombre, email, direccion, fechaNacimiento);
        cosechadores.add(nuevo);
        return true;
    }

    public boolean createCultivo(int id, String especie, String variedad, double rendimiento) throws GestionHuertosException{
        Optional<Cultivo> verif = findCultivoById(id);
        if (verif.isPresent()) {throw new GestionHuertosException("Ya existe un cultivo con el id indicado");}
        for (Cultivo c : cultivos) {
            if (c.getId() == id) {
                return false;
            }
        }
        Cultivo nuevo = new Cultivo(id, especie, variedad, rendimiento);
        cultivos.add(nuevo);
        return true;
    }

    public boolean createHuerto(String nombre, float superficie, String ubicacion, String rutPropietario) throws GestionHuertosException {
        Optional<Huerto> verif = findHuertoByNombre(nombre);
        Optional<Persona> verif2 = findPropietarioByRut(Rut.of(rutPropietario));
        if (verif2.isPresent()) {throw new GestionHuertosException("No existe un propietario con el rut indicado");}
        if (verif.isPresent()) {throw new GestionHuertosException("Ya existe un huerto con el nombre indicado ");}
        Propietario propietario = null;
        for (Huerto h : huertos) {
            if (h.getNombre().equals(nombre)) {
                return false;
            }
        }

        for (Propietario p : propietarios) {
            if (p.getRut().equals(rutPropietario)) {
                propietario = p;
            }
        }

        if (propietario == null) {
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
        Optional<Huerto> verif = findHuertoByNombre(nombreHuerto);
        Optional<Cultivo> verif2 = findCultivoById(idCultivo);
        if (verif.isEmpty()) {throw new GestionHuertosException("No existe un huerto con el nombre indicado");}
        if (verif2.isEmpty()) {throw new GestionHuertosException("No existe un cultivo con el id indicado");}
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

    public boolean createPlanCosecha(int idPlan, String nom, LocalDate inicio, LocalDate finEstim, double meta, float precioBase, String nomHuerto, int idCuartel) throws GestionHuertosException {
        Huerto huerto = null;
        Optional<PlanCosecha> verif = findPlanCosechaById(idPlan);
        if (verif.isPresent()) {throw new GestionHuertosException("Ya existe un plan con el id indicado");}
        Optional<Huerto> verif2 = findHuertoByNombre(nomHuerto);
        if (verif2.isEmpty()) {throw new GestionHuertosException("No existe un huerto con el nombre indicado");}
        Huerto HH = verif2.get();
        Cuartel verif3 = HH.getCuartel(idCuartel);
        if (verif3 == null) {throw new GestionHuertosException("No existe en el huerto un cuartel con el id indicado");}
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
        Optional<PlanCosecha> verif = findPlanCosechaById(idPlan);
        if (verif.isEmpty()) {throw new GestionHuertosException("No existe un plan con el id indicado");}
        Optional<Supervisor> verif2 = findSupervisorByRut(Rut.of(rutSup));
        if(verif2.isEmpty()) {throw new GestionHuertosException("No existe Supervisor con el rut Indicado");}
        Supervisor supp = verif2.get();
        Cuadrilla Cuadss = supp.getCuadrilla();
        if (Cuadss!=null) {throw new GestionHuertosException("El supervisor ya tiene asignada una cuadrilla a su cargo ");}
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
    public boolean addCosechadorToCuadrilla(int idPlan, int idCuadrilla, LocalDate fInicio, LocalDate fFin, double meta, String rutCosechador) throws GestionHuertosException {
        PlanCosecha plan = null;
        Optional<PlanCosecha> verif = findPlanCosechaById(idPlan);
        if (verif.isEmpty()) {throw new GestionHuertosException("No existe un plan con el id indicado");}
        Optional<Cosechador> verif2 = findCosechadorByRut(Rut.of(rutCosechador));
        if (verif2.isEmpty()) {throw new GestionHuertosException("No existe un cosechador con el rut indicado");}
        ///añadir excepcion faltante

        PlanCosecha plan2 = verif.get();
        Cuadrilla[] cuads;
        cuads = plan2.getCuadrillas();
        Cuadrilla thecuad = null;
        for (Cuadrilla cuad : cuads) {
            if (cuad.getId() == idCuadrilla) {
                thecuad = cuad;
            }
        }
        if (thecuad.getMaximoCosechadores()==thecuad.getCosechadores().length) {
            throw new GestionHuertosException("El número de cosechadores ya alcanzó el máximo permitido");
        }

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

        if (cosechadores.isEmpty()) {
            return new String[0];
        }

        String[] resultados = cosechadores.stream()
                .map(c -> {
                    double montoPagado = pesajes.stream()
                            .filter(p -> p.getCosechadorAsignado().getCosechador().equals(c))
                            .filter(Pesaje::isPagado)
                            .mapToDouble(Pesaje::getMonto)
                            .sum();

                    double montoImpago = pesajes.stream()
                            .filter(p -> p.getCosechadorAsignado().getCosechador().equals(c))
                            .filter(p -> !p.isPagado())
                            .mapToDouble(Pesaje::getMonto)
                            .sum();

                    return String.join(";",
                            c.getRut(),
                            c.getNombre(),
                            c.getDireccion(),
                            c.getEmail(),
                            c.getFechaNacimiento().toString(),
                            String.valueOf(c.getCuadrillas().length),
                            String.valueOf(montoImpago),
                            String.valueOf(montoPagado)
                    );
                })
                .toArray(String[]::new);

        return resultados;
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
    public String[] listPesajesCosechador(Rut rut) throws GestionHuertosException {

        Optional<Cosechador> optC = findCosechadorByRut(rut);
        if (optC.isEmpty()) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }

        Cosechador cosechador = optC.get();
        Cuadrilla[] cuadri = cosechador.getCuadrillas();
        if (cuadri.length == 0) {
            throw new GestionHuertosException("El cosechador no ha sido asignado a una cuadrilla");
        }

        if (this.pesajes.isEmpty()) {
            return new String[0];
        }

        DateTimeFormatter fmtFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<String> resultados = new ArrayList<>();

        for (Pesaje p : this.pesajes) {

            if (!p.getCosechadorAsignado().getCosechador().equals(cosechador)) {
                continue;
            }

            int id = p.getId();
            String fecha = p.getFechaHora().format(fmtFecha);
            String calidad    = p.getCalidad().toString();
            String cantidadKg = String.valueOf(p.getCantidadKg());
            String precio     = String.valueOf(p.getPrecioKg());
            String monto      = String.valueOf(p.getMonto());

            PagoPesaje pago = p.getPagoPesaje();
            String pagadoEl;

            if (pago != null && pago.getFecha() != null) {
                Date fechaPago = pago.getFecha();   // Date
                pagadoEl = sdf.format(fechaPago);   // "dd/MM/yyyy"
            } else {
                pagadoEl = "Impago";
            }

            String linea = String.format(
                    "%-4d %-10s %-10s %10s %10s %10s %-10s",
                    id,
                    fecha,
                    calidad,
                    cantidadKg,
                    precio,
                    monto,
                    pagadoEl
            );

            resultados.add(linea);
        }

        return resultados.toArray(new String[0]);
    }
    public String[] listPesajes() {

        if (this.pesajes.isEmpty()) {
            return new String[0];
        }

        DateTimeFormatter fmtFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<String> resultados = new ArrayList<>();

        for (Pesaje p : this.pesajes) {

            int id = p.getId();
            String fecha = p.getFechaHora().format(fmtFecha);
            String rutCosechador = p.getCosechadorAsignado()
                    .getCosechador()
                    .getRut()
                    .toString();
            String calidad    = p.getCalidad().toString();
            String cantidadKg = String.valueOf(p.getCantidadKg());
            String precio     = String.valueOf(p.getPrecioKg());
            String monto      = String.valueOf(p.getMonto());

            PagoPesaje pago = p.getPagoPesaje();
            String pagadoEl;

            if (pago != null && pago.getFecha() != null) {
                Date fechaPago = pago.getFecha();   // Date
                pagadoEl = sdf.format(fechaPago);   // "dd/MM/yyyy"
            } else {
                pagadoEl = "Impago";
            }

            String linea = String.format(
                    "%-4d %-10s %-13s %-10s %10s %10s %10s %-10s",
                    id,
                    fecha,
                    rutCosechador,
                    calidad,
                    cantidadKg,
                    precio,
                    monto,
                    pagadoEl
            );

            resultados.add(linea);
        }

        return resultados.toArray(new String[0]);
    }

    public String[] listPagoPesajes() {

        if (this.Ppesajes.isEmpty()) {
            return new String[0];
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        List<String> resultados = new ArrayList<>();

        for (PagoPesaje pago : this.Ppesajes) {

            int id = pago.getId();

            Date fechaDate = pago.getFecha();          // Date
            String fecha = (fechaDate != null)
                    ? sdf.format(fechaDate)
                    : "N/A";

            String monto       = String.valueOf(pago.getMonto());
            Pesaje[] pesajes1  = pago.getPesajes();
            int nroPesajes     = pesajes1.length;

            Pesaje pesajeEjemplo = pesajes1[0];
            String rutCosechador = pesajeEjemplo
                    .getCosechadorAsignado()
                    .getCosechador()
                    .getRut()
                    .toString();

            String linea = String.format(
                    "%-4d %-10s %10s %10d %-13s",
                    id,
                    fecha,
                    monto,
                    nroPesajes,
                    rutCosechador
            );

            resultados.add(linea);
        }

        return resultados.toArray(new String[0]);
    }

    public double addPagoPesaje(int id, Rut rutCosechador) throws GestionHuertosException {
        // validar que no exista el pago
        for (PagoPesaje p : Ppesajes) {
            if (p.getId() == id) {
                throw new GestionHuertosException("Ya existe un pago de pesaje con el id indicado");
            }
        }

        Optional<Cosechador> cos = findCosechadorByRut(rutCosechador);
        if (cos.isEmpty()) {throw new GestionHuertosException("No existe un cosechador con el rut indicado");}
        Pesaje pesaje = null;
        for (Pesaje p : pesajes) {
            if (p.getId() == id) {
                pesaje = p;
                break;
            }
        }

        if (pesaje == null) {
            throw new GestionHuertosException("No existe un pesaje con el id indicado");
        }

        if (pesaje.isPagado()) {
            throw new GestionHuertosException("El pesaje ya se encuentra pagado");
        }

        List<Pesaje> lista = new ArrayList<>();
        lista.add(pesaje);

        Date hoy = new Date();
        PagoPesaje pago = new PagoPesaje(id, hoy, lista);
        Ppesajes.add(pago);

        pesaje.setPago(pago);

        return pago.getMonto();
    }

    public void addPesaje(int id, Rut rutCosechador, int idPlan, int idCuadrilla, float cantidadKg, Calidad calidad) throws GestionHuertosException {

        for (Pesaje p : pesajes) {
            if (p.getId() == id) {
                throw new GestionHuertosException("Ya existe un pesaje con id indicado");
            }
        }

        Optional<Cosechador> optC = findCosechadorByRut(rutCosechador);
        if (optC.isEmpty()) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }
        Cosechador cos = optC.get();

        Optional<PlanCosecha> optPlan = findPlanCosechaById(idPlan);
        if (optPlan.isEmpty()) {
            throw new GestionHuertosException("No existe un plan con el id indicado");
        }
        PlanCosecha pC = optPlan.get();

        if (pC.getEstado() != EstadoPlan.EJECUTANDO) {
            throw new GestionHuertosException("El plan no se encuentra en estado \"en ejecución\"");
        }

        Cuadrilla[] cuad = cos.getCuadrillas();
        Cuadrilla cuadsel = null;
        for (Cuadrilla c : cuad) {
            if (c.getId() == idCuadrilla) {
                cuadsel = c;
                break;
            }
        }

        if (cuadsel == null || cuadsel.getPlanCosecha() != pC) {
            throw new GestionHuertosException(
                    "El cosechador no tiene una asignación a una cuadrilla con el id indicado en el plan con el id señalado"
            );
        }

        java.time.LocalDate hoy = java.time.LocalDate.now();

        Optional<CosechadorAsignado> optAsig = cos.getAsignacion(idPlan, idCuadrilla);
        if (optAsig.isEmpty()) {
            throw new GestionHuertosException(
                    "El cosechador no tiene una asignación a una cuadrilla con el id indicado en el plan con el id señalado"
            );
        }

        CosechadorAsignado asig = optAsig.get();
        java.time.LocalDate inicio  = asig.getDesde();
        java.time.LocalDate termino = asig.getHasta();

        if (hoy.isBefore(inicio) || hoy.isAfter(termino)) {
            throw new GestionHuertosException(
                    "La fecha no está en el rango de la asignación del cosechador a la cuadrilla"
            );
        }
        Cuartel cuart = pC.getCuartel();
        if (cuart.getEstado() != EstadoFenologico.COSECHA) {
            throw new GestionHuertosException("El cuartel no se encuentra en estado fenológico cosecha");
        }
        Pesaje nuevo = new Pesaje(
                id,
                cantidadKg,
                calidad,
                java.time.LocalDateTime.now(),
                asig
        );
        pesajes.add(nuevo);
    }

    public void changeEstadoPlan (int idPlan, EstadoPlan estado) {
        PlanCosecha pp = null;
        for (PlanCosecha p : planesDeCosecha) {if (p.getId()==idPlan) {pp=p; break;}}
        if (pp==null) {throw new GestionHuertosException("No existe un plan con el id indicado");}

        ///  preguntar x excepcion faltanteNo está permitido el cambio de estado solicitado
        pp.setEstado(estado);
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



    private Optional<Persona> findPropietarioByRut (Rut rut) {
        for (Propietario p : propietarios) {
            if (p.getRut().equals(rut)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
    private Optional<Supervisor> findSupervisorByRut(Rut rut) {
        if (rut == null) {
            return Optional.empty();
        }
        for (Supervisor s : supervisores) {
            Rut rutSup = Rut.of(s.getRut());
            if (rutSup.equals(rut)) {
                return Optional.of(s);
            }
        }
        return Optional.empty();
    }
    private Optional<Cosechador> findCosechadorByRut(Rut rut) {
        if (rut == null) {
            return Optional.empty();
        }
        for (Cosechador p : cosechadores) {
            Rut rutCosechador = Rut.of(p.getRut());
            if (rutCosechador.equals(rut)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
    private Optional<Cultivo> findCultivoById (int id) {
        for (Cultivo p : cultivos) {
            if (p.getId()==id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
    private Optional<Huerto> findHuertoByNombre(String nombre) {
        for (Huerto p : huertos) {
            if (p.getNombre().equals(nombre)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
    private Optional<PlanCosecha> findPlanCosechaById(long id) {
        for (PlanCosecha p : planesDeCosecha) {
            if (p.getId()==id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    private Optional<Pesaje> findPesajeById(int idPesaje) {
        for (Pesaje p : pesajes) {
            if (p.getId() == idPesaje) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }


    private Optional<PagoPesaje> findPagoPesajeById (int id) {
        for (PagoPesaje p : Ppesajes) {
            if (p.getId()==id) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }



    public void readDataFromTextFile(String filename) throws GestionHuertosException {
        if (!propietarios.isEmpty() || !supervisores.isEmpty() || !cosechadores.isEmpty()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                String[] parts = line.split(";");
                String operacion = parts[0].trim();
                int n = Integer.parseInt(parts[1].trim());

                for (int i = 0; i < n; i++) {
                    String dataLine = br.readLine().trim();
                    if (dataLine == null) continue;

                    String[] data = dataLine.split(";");

                    try {
                        switch (operacion) {
                            case "createPropietario":
                                createPropietario(
                                        data[0].trim(),
                                        data[1].trim(),
                                        data[2].trim(),
                                        data[3].trim(),
                                        data[4].trim()
                                );
                                break;

                            case "createSupervisor":
                                createSupervisor(
                                        data[0].trim(),
                                        data[1].trim(),
                                        data[2].trim(),
                                        data[3].trim(),
                                        data[4].trim()
                                );
                                break;

                            case "createCosechador":
                                createCosechador(
                                        data[0].trim(),
                                        data[1].trim(),
                                        data[2].trim(),
                                        data[3].trim(),
                                        LocalDate.parse(data[4].trim(), dateFormatter)
                                );
                                break;

                            case "createCultivo":
                                createCultivo(
                                        Integer.parseInt(data[0].trim()),
                                        data[1].trim(),
                                        data[2].trim(),
                                        Double.parseDouble(data[3].trim())
                                );
                                break;

                            case "createHuerto":
                                createHuerto(
                                        data[0].trim(),
                                        Float.parseFloat(data[1].trim()),
                                        data[2].trim(),
                                        data[3].trim()
                                );
                                break;

                            case "addCuartelToHuerto":
                                addCuartelToHuerto(
                                        data[0].trim(),
                                        Integer.parseInt(data[1].trim()),
                                        Float.parseFloat(data[2].trim()),
                                        Integer.parseInt(data[3].trim())
                                );
                                break;

                            case "createPlanCosecha":
                                createPlanCosecha(
                                        Integer.parseInt(data[0].trim()),
                                        data[1].trim(),
                                        LocalDate.parse(data[2].trim(), dateFormatter),
                                        LocalDate.parse(data[3].trim(), dateFormatter),
                                        Double.parseDouble(data[4].trim()),
                                        Float.parseFloat(data[5].trim()),
                                        data[6].trim(),
                                        Integer.parseInt(data[7].trim())
                                );
                                break;

                            case "addCuadrillaToPlan":
                                addCuadrillatoPlan(
                                        Integer.parseInt(data[0].trim()),
                                        Integer.parseInt(data[1].trim()),
                                        data[2].trim(),
                                        data[3].trim()
                                );
                                break;

                            case "addCosechadorToCuadrilla":
                                addCosechadorToCuadrilla(
                                        Integer.parseInt(data[0].trim()),
                                        Integer.parseInt(data[1].trim()),
                                        LocalDate.parse(data[2].trim(), dateFormatter),
                                        LocalDate.parse(data[3].trim(), dateFormatter),
                                        Double.parseDouble(data[4].trim()),
                                        data[5].trim()
                                );
                                break;

                            case "changeEstadoPlan":
                                changeEstadoPlan(
                                        Integer.parseInt(data[0].trim()),
                                        EstadoPlan.valueOf(data[1].trim().toUpperCase())
                                );
                                break;

                            case "changeEstadoCuartel":
                                int idCuartel = Integer.parseInt(data[0].trim());
                                String nombreHuerto = data[1].trim();
                                EstadoFenologico estado = EstadoFenologico.valueOf(data[2].trim().toUpperCase());

                                changeEstadoCuartel(nombreHuerto, idCuartel, estado);
                                break;

                            case "addPesaje":

                                String rutString = data[1].trim();

                                String rutSinPuntos = rutString.replace(".", "");
                                String[] partesRut = rutSinPuntos.split("-");

                                if (partesRut.length != 2) {
                                    throw new GestionHuertosException("Formato de RUT inválido: " + rutString);
                                }

                                long numeroRut = Long.parseLong(partesRut[0]);
                                char dvRut = partesRut[1].charAt(0);

                                Rut rutObj = new Rut(numeroRut, dvRut);

                                addPesaje(
                                        Integer.parseInt(data[0].trim()),
                                        rutObj,
                                        Integer.parseInt(data[2].trim()),
                                        Integer.parseInt(data[3].trim()),
                                        Float.parseFloat(data[4].trim()),
                                        Calidad.valueOf(data[5].trim().toUpperCase())
                                );
                                break;

                            default:
                                System.err.println("Operación desconocida: " + operacion);
                                break;
                        }
                    } catch (Exception e) {
                        throw new GestionHuertosException("Error procesando línea: " + dataLine + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw new GestionHuertosException("Error al abrir o leer el archivo: " + e.getMessage());
        }
    }

    public void readSystemData() throws GestionHuertosException {

        personas.clear();
        propietarios.clear();
        supervisores.clear();
        cosechadores.clear();
        cultivos.clear();
        planesDeCosecha.clear();

        Persona[] gente = IO.readPersonas();

        Arrays.stream(gente)
                .filter(p -> p.getClass() == Propietario.class)
                .map(p -> (Propietario) p)
                .forEach(propietarios::add);

        Arrays.stream(gente)
                .filter(p -> p.getClass() == Supervisor.class)
                .map(p -> (Supervisor) p)
                .forEach(supervisores::add);

        Arrays.stream(gente)
                .filter(p -> p.getClass() == Cosechador.class)
                .map(p -> (Cosechador) p)
                .forEach(cosechadores::add);

        for (Propietario p : propietarios) {personas.add(p);}
        for (Supervisor s : supervisores) {personas.add(s);}
        for (Cosechador c : cosechadores) {personas.add(c);}

        Cultivo[] cultivitos = IO.readCultivos();
        Arrays.stream(cultivitos).forEach(cultivos::add);

        PlanCosecha[] plancitos = IO.readPlanes();
        Arrays.stream(plancitos).forEach(planesDeCosecha::add);
    }

    public void saveSystemData() throws GestionHuertosException {

        Persona[] personasAGuardar = new Persona[
                propietarios.size() + supervisores.size() + cosechadores.size()
                ];

        int i = 0;

        for (Propietario p : propietarios) {
            personasAGuardar[i++] = p;
        }
        for (Supervisor s : supervisores) {
            personasAGuardar[i++] = s;
        }
        for (Cosechador c : cosechadores) {
            personasAGuardar[i++] = c;
        }

        IO.savePersonas(personasAGuardar);
        IO.saveCultivos(cultivos.toArray(new Cultivo[0]));
        IO.savePlanesCosecha(planesDeCosecha.toArray(new PlanCosecha[0]));
    }



}





