package controlador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.*;
import enumeraciones.*;
import excepciones.GestionHuertosException;

public class ControladorProduccion {
    private List<Propietario> propietarios;
    private List<Supervisor> supervisores;
    private List<Cosechador> cosechadores;
    private List<Cultivo> cultivos;
    private List<Huerto> huertos;
    private List<PlanCosecha> planes;
    private List<Pesaje> pesajes;
    private List<PagoPesaje> pagos;

    public ControladorProduccion() {
        this.propietarios = new ArrayList<>();
        this.supervisores = new ArrayList<>();
        this.cosechadores = new ArrayList<>();
        this.cultivos = new ArrayList<>();
        this.huertos = new ArrayList<>();
        this.planes = new ArrayList<>();
        this.pesajes = new ArrayList<>();
        this.pagos = new ArrayList<>();
    }

    // Métodos CREATE
    public void createPropietario(Rut rut, String nombre, String email, String direccion, String direccionComercial)
            throws GestionHuertosException {
        if (findPropietarioByRut(rut).isPresent()) {
            throw new GestionHuertosException("Ya existe un propietario con el rut indicado");
        }
        propietarios.add(new Propietario(rut, nombre, email, direccion, direccionComercial));
    }

    public void createSupervisor(Rut rut, String nombre, String email, String direccion, String profesion)
            throws GestionHuertosException {
        if (findSupervisorByRut(rut).isPresent()) {
            throw new GestionHuertosException("Ya existe un supervisor con el rut indicado");
        }
        supervisores.add(new Supervisor(rut, nombre, email, direccion, profesion));
    }

    public void createCosechador(Rut rut, String nombre, String email, String direccion, LocalDate fechaNacimiento)
            throws GestionHuertosException {
        if (findCosechadorByRut(rut).isPresent()) {
            throw new GestionHuertosException("Ya existe un cosechador con el rut indicado");
        }
        cosechadores.add(new Cosechador(rut, nombre, email, direccion, fechaNacimiento));
    }

    public void createCultivo(int id, String especie, String variedad, float rendimiento)
            throws GestionHuertosException {
        if (findCultivoById(id).isPresent()) {
            throw new GestionHuertosException("Ya existe un cultivo con el id indicado");
        }
        cultivos.add(new Cultivo(id, especie, variedad, rendimiento));
    }

    public void createHuerto(String nombre, float superficie, String ubicacion, Rut rutPropietario)
            throws GestionHuertosException {
        if (findHuertoByNombre(nombre).isPresent()) {
            throw new GestionHuertosException("Ya existe un huerto con el nombre indicado");
        }

        Optional<Propietario> propietarioOpt = findPropietarioByRut(rutPropietario);
        if (propietarioOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un propietario con el rut indicado");
        }

        huertos.add(new Huerto(nombre, superficie, ubicacion, propietarioOpt.get()));
    }

    public void addCuartelToHuerto(String nombreHuerto, int idCuartel, float superficie, int idCultivo)
            throws GestionHuertosException {
        Optional<Huerto> huertoOpt = findHuertoByNombre(nombreHuerto);
        if (huertoOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un huerto con el nombre indicado");
        }

        Optional<Cultivo> cultivoOpt = findCultivoById(idCultivo);
        if (cultivoOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un cultivo con el id indicado");
        }

        Huerto huerto = huertoOpt.get();
        huerto.addCuartel(idCuartel, superficie, cultivoOpt.get());
    }

    public void changeEstadoCuartel(String nombreHuerto, int idCuartel, EstadoFenologico estado)
            throws GestionHuertosException {
        Optional<Huerto> huertoOpt = findHuertoByNombre(nombreHuerto);
        if (huertoOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un huerto con el nombre indicado");
        }

        Huerto huerto = huertoOpt.get();
        huerto.setEstadoCuartel(idCuartel, estado);
    }

    public void createPlanCosecha(int id, String nombrePlan, LocalDate fechaInicio, LocalDate fechaTermino,
                                  double metaKilos, double precioBaseKilo, String nomHuerto, int idCuartel)
            throws GestionHuertosException {
        if (findPlanById(id).isPresent()) {
            throw new GestionHuertosException("Ya existe un plan con el id indicado");
        }

        Optional<Huerto> huertoOpt = findHuertoByNombre(nomHuerto);
        if (huertoOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un huerto con el nombre indicado");
        }

        Huerto huerto = huertoOpt.get();
        Optional<Cuartel> cuartelOpt = huerto.getCuartelById(idCuartel);
        if (cuartelOpt.isEmpty()) {
            throw new GestionHuertosException("No existe en el huerto un cuartel con el id indicado");
        }

        planes.add(new PlanCosecha(id, nombrePlan, fechaInicio, fechaTermino, metaKilos, precioBaseKilo, cuartelOpt.get()));
    }

    public void changeEstadoPlan(int idPlan, EstadoPlan nuevoEstado) throws GestionHuertosException {
        Optional<PlanCosecha> planOpt = findPlanById(idPlan);
        if (planOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un plan con el id indicado");
        }

        PlanCosecha plan = planOpt.get();
        if (!plan.setEstado(nuevoEstado)) {
            throw new GestionHuertosException("No está permitido el cambio de estado solicitado");
        }
    }

    public void addCuadrillaToPlan(int idPlan, int idCuadrilla, String nombreCuadrilla, Rut rutSupervisor)
            throws GestionHuertosException {
        Optional<PlanCosecha> planOpt = findPlanById(idPlan);
        if (planOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un plan con el id indicado");
        }

        Optional<Supervisor> supervisorOpt = findSupervisorByRut(rutSupervisor);
        if (supervisorOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un supervisor con el rut indicado");
        }

        Supervisor supervisor = supervisorOpt.get();
        if (supervisor.getCuadrilla() != null) {
            throw new GestionHuertosException("El supervisor ya tiene asignada una cuadrilla a su cargo");
        }

        PlanCosecha plan = planOpt.get();
        plan.addCuadrilla(idCuadrilla, nombreCuadrilla, supervisor);
    }

    public void addCosechadorToCuadrilla(int idPlanCosecha, int idCuadrilla, LocalDate fechaIniCosechador,
                                         LocalDate fechaFinCosechador, double metaKilosCosechador, Rut rutCosechador)
            throws GestionHuertosException {
        Optional<PlanCosecha> planOpt = findPlanById(idPlanCosecha);
        if (planOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un plan con el id indicado");
        }

        Optional<Cosechador> cosechadorOpt = findCosechadorByRut(rutCosechador);
        if (cosechadorOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }

        PlanCosecha plan = planOpt.get();
        if (fechaIniCosechador.isBefore(plan.getFechaInicio()) || fechaFinCosechador.isAfter(plan.getFechaTermino())) {
            throw new GestionHuertosException("El rango de fechas de asignación del cosechador a la cuadrilla está fuera del rango de fechas del plan");
        }

        plan.addCosechadorToCuadrilla(idCuadrilla, fechaIniCosechador, fechaFinCosechador, metaKilosCosechador, cosechadorOpt.get());
    }

    public void addPesaje(int id, Rut rutCosechador, int idPlan, int idCuadrilla, float cantidadKg, Calidad calidad)
            throws GestionHuertosException {
        if (findPesajeById(id).isPresent()) {
            throw new GestionHuertosException("Ya existe un pesaje con id indicado");
        }

        Optional<Cosechador> cosechadorOpt = findCosechadorByRut(rutCosechador);
        if (cosechadorOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }

        Optional<PlanCosecha> planOpt = findPlanById(idPlan);
        if (planOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un plan con el id indicado");
        }

        PlanCosecha plan = planOpt.get();
        if (plan.getEstado() != EstadoPlan.EJECUTANDO) {
            throw new GestionHuertosException("El plan no se encuentra en estado \"en ejecución\"");
        }

        Cosechador cosechador = cosechadorOpt.get();
        Optional<CosechadorAsignado> asignacionOpt = cosechador.getAsignacion(idPlan, idCuadrilla);
        if (asignacionOpt.isEmpty()) {
            throw new GestionHuertosException("El cosechador no tiene una asignación a una cuadrilla con el id indicado en el plan con el id señalado");
        }

        CosechadorAsignado asignacion = asignacionOpt.get();
        LocalDate fechaActual = LocalDate.now();
        if (fechaActual.isBefore(asignacion.getFechaInicio()) || fechaActual.isAfter(asignacion.getFechaTermino())) {
            throw new GestionHuertosException("La fecha no está en el rango de la asignación del cosechador a la cuadrilla");
        }

        if (plan.getCuartel().getEstado() != EstadoFenologico.COSECHA) {
            throw new GestionHuertosException("El cuartel no se encuentra en estado fenológico cosecha");
        }

        Pesaje pesaje = new Pesaje(id, fechaActual, cantidadKg, calidad, asignacion);
        pesajes.add(pesaje);
        asignacion.addPesaje(pesaje);
    }

    public void addPagoPesaje(int id, Rut rutCosechador) throws GestionHuertosException {
        if (findPagoPesajeById(id).isPresent()) {
            throw new GestionHuertosException("Ya existe un pago de pesaje con el id indicado");
        }

        Optional<Cosechador> cosechadorOpt = findCosechadorByRut(rutCosechador);
        if (cosechadorOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }

        Cosechador cosechador = cosechadorOpt.get();
        if (cosechador.getAsignaciones().length == 0) {
            throw new GestionHuertosException("El cosechador no ha sido asignado a una cuadrilla");
        }

        // Verificar si hay pesajes impagos
        boolean tienePesajesImpagos = false;
        for (CosechadorAsignado asignacion : cosechador.getAsignaciones()) {
            if (!asignacion.getPesajesImpagos().isEmpty()) {
                tienePesajesImpagos = true;
                break;
            }
        }

        if (!tienePesajesImpagos) {
            throw new GestionHuertosException("El cosechador no tiene pesajes impagos");
        }

        LocalDate fechaActual = LocalDate.now();
        PagoPesaje pago = new PagoPesaje(id, fechaActual, cosechador);

        // Agregar todos los pesajes impagos al pago
        for (CosechadorAsignado asignacion : cosechador.getAsignaciones()) {
            for (Pesaje pesaje : asignacion.getPesajesImpagos()) {
                pago.addPesaje(pesaje);
            }
        }

        pagos.add(pago);
    }

    // Métodos de listado
    public String[] listPropietarios() {
        return propietarios.stream()
                .map(p -> String.format("%s; %s; %s; %s; %s; %d",
                        p.getRut(), p.getNombre(), p.getDireccion(), p.getEmail(),
                        p.getDireccionComercial(),
                        huertos.stream().filter(h -> h.getPropietario().equals(p)).count()))
                .toArray(String[]::new);
    }

    public String[] listSupervisores() {
        return supervisores.stream()
                .map(s -> {
                    String nomenclatura = "S/A";
                    double kilosPesados = 0;
                    int pesajesImpagos = 0;

                    if (s.getCuadrilla() != null) {
                        nomenclatura = s.getCuadrilla().getNombre();
                        kilosPesados = s.getCuadrilla().getKilosPesados();
                        pesajesImpagos = (int) s.getCuadrilla().getAsignaciones().stream()
                                .flatMap(a -> a.getPesajes().stream())
                                .filter(p -> !p.isPagado())
                                .count();
                    }

                    return String.format("%s; %s; %s; %s; %s; %s; %.1f; %d",
                            s.getRut(), s.getNombre(), s.getDireccion(), s.getEmail(),
                            s.getProfesion(), nomenclatura, kilosPesados, pesajesImpagos);
                })
                .toArray(String[]::new);
    }

    public String[] listCosechadores() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return cosechadores.stream()
                .map(c -> {
                    double montoImpago = 0;
                    double montoPagado = 0;
                    int numCuadrillas = c.getAsignaciones().length;

                    for (CosechadorAsignado asignacion : c.getAsignaciones()) {
                        montoImpago += asignacion.getMontoImpago();
                        montoPagado += asignacion.getMontoPagado();
                    }

                    return String.format("%s; %s; %s; %s; %s; %d; %,.1f; %,.1f",
                            c.getRut(), c.getNombre(), c.getDireccion(), c.getEmail(),
                            c.getFechaNacimiento().format(formatter), numCuadrillas,
                            montoImpago, montoPagado);
                })
                .toArray(String[]::new);
    }

    public String[] listCultivos() {
        return cultivos.stream()
                .map(c -> String.format("%d; %s; %s; %.1f; %d",
                        c.getId(), c.getEspecie(), c.getVariedad(), c.getRendimiento() * 100,
                        huertos.stream()
                                .flatMap(h -> h.getCuarteles().stream())
                                .filter(cuartel -> cuartel.getCultivo().equals(c))
                                .count()))
                .toArray(String[]::new);
    }

    public String[] listHuertos() {
        return huertos.stream()
                .map(h -> String.format("%s; %,.1f; %s; %s; %s; %d",
                        h.getNombre(), h.getSuperficie(), h.getUbicacion(),
                        h.getPropietario().getRut(), h.getPropietario().getNombre(),
                        h.getCuarteles().size()))
                .toArray(String[]::new);
    }

    public String[] listPlanes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return planes.stream()
                .map(p -> String.format("%d; %s; %s; %s; %,.1f; %,.1f; %s; %d; %s; %d; %.1f",
                        p.getId(), p.getNombre(), p.getFechaInicio().format(formatter),
                        p.getFechaTermino().format(formatter), p.getMetaKilos(), p.getPrecioBaseKilo(),
                        p.getEstado(), p.getCuartel().getId(), p.getCuartel().getCultivo().getEspecie(),
                        p.getCuadrillas().size(), p.getCumplimientoMeta()))
                .toArray(String[]::new);
    }

    public String[] listPesajes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return pesajes.stream()
                .map(p -> {
                    String fechaPago = "Impago";
                    if (p.isPagado()) {
                        fechaPago = p.getPago().getFecha().format(formatter);
                    }

                    return String.format("%d; %s; %s; %s; %s; %.1f; %s; %s; %,.1f; %s",
                            p.getId(), p.getAsignacion().getCosechador().getRut(),
                            p.getAsignacion().getCosechador().getNombre(),
                            p.getAsignacion().getCuadrilla().getPlan().getNombre(),
                            p.getAsignacion().getCuadrilla().getNombre(),
                            p.getCantidadKg(), p.getCalidad(),
                            p.getFecha().format(formatter), p.getMonto(), fechaPago);
                })
                .toArray(String[]::new);
    }

    public String[] listPesajes(Rut rutCosechador) throws GestionHuertosException {
        Optional<Cosechador> cosechadorOpt = findCosechadorByRut(rutCosechador);
        if (cosechadorOpt.isEmpty()) {
            throw new GestionHuertosException("No existe un cosechador con el rut indicado");
        }

        Cosechador cosechador = cosechadorOpt.get();
        if (cosechador.getAsignaciones().length == 0) {
            throw new GestionHuertosException("El cosechador no ha sido asignado a una cuadrilla");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<String> resultado = new ArrayList<>();

        for (CosechadorAsignado asignacion : cosechador.getAsignaciones()) {
            for (Pesaje pesaje : asignacion.getPesajes()) {
                String fechaPago = "Impago";
                if (pesaje.isPagado()) {
                    fechaPago = pesaje.getPago().getFecha().format(formatter);
                }

                resultado.add(String.format("%d; %s; %s; %s; %.1f; %s; %s; %,.1f; %s",
                        pesaje.getId(),
                        asignacion.getCuadrilla().getPlan().getNombre(),
                        asignacion.getCuadrilla().getNombre(),
                        pesaje.getFecha().format(formatter),
                        pesaje.getCantidadKg(), pesaje.getCalidad(),
                        pesaje.getAsignacion().getCuadrilla().getPlan().getCuartel().getCultivo().getEspecie(),
                        pesaje.getMonto(), fechaPago));
            }
        }

        return resultado.toArray(new String[0]);
    }

    public String[] listPagosPesajes() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return pagos.stream()
                .map(p -> String.format("%d; %s; %s; %s; %s; %,.1f",
                        p.getId(), p.getCosechador().getRut(), p.getCosechador().getNombre(),
                        p.getFecha().format(formatter),
                        p.getPesajes().stream().map(ps -> String.valueOf(ps.getId())).reduce("", (a, b) -> a + (a.isEmpty() ? "" : ", ") + b),
                        p.getMonto()))
                .toArray(String[]::new);
    }

    // Métodos de búsqueda privados
    private Optional<Propietario> findPropietarioByRut(Rut rut) {
        return propietarios.stream().filter(p -> p.getRut().equals(rut)).findFirst();
    }

    private Optional<Supervisor> findSupervisorByRut(Rut rut) {
        return supervisores.stream().filter(s -> s.getRut().equals(rut)).findFirst();
    }

    private Optional<Cosechador> findCosechadorByRut(Rut rut) {
        return cosechadores.stream().filter(c -> c.getRut().equals(rut)).findFirst();
    }

    private Optional<Cultivo> findCultivoById(int id) {
        return cultivos.stream().filter(c -> c.getId() == id).findFirst();
    }

    private Optional<Huerto> findHuertoByNombre(String nombre) {
        return huertos.stream().filter(h -> h.getNombre().equals(nombre)).findFirst();
    }

    private Optional<PlanCosecha> findPlanById(int id) {
        return planes.stream().filter(p -> p.getId() == id).findFirst();
    }

    private Optional<Pesaje> findPesajeById(int id) {
        return pesajes.stream().filter(p -> p.getId() == id).findFirst();
    }

    private Optional<PagoPesaje> findPagoPesajeById(int id) {
        return pagos.stream().filter(p -> p.getId() == id).findFirst();
    }

    // Método para leer datos desde archivo
    public void readDataFromTextFile(String filename) throws GestionHuertosException {
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
                    String[] data = dataLine.split(";");

                    switch (operacion) {
                        case "createPropietario":
                            createPropietario(
                                    new Rut(data[0].trim()),
                                    data[1].trim(),
                                    data[2].trim(),
                                    data[3].trim(),
                                    data[4].trim()
                            );
                            break;

                        case "createSupervisor":
                            createSupervisor(
                                    new Rut(data[0].trim()),
                                    data[1].trim(),
                                    data[2].trim(),
                                    data[3].trim(),
                                    data[4].trim()
                            );
                            break;

                        case "createCosechador":
                            createCosechador(
                                    new Rut(data[0].trim()),
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
                                    Float.parseFloat(data[3].trim())
                            );
                            break;

                        case "createHuerto":
                            createHuerto(
                                    data[0].trim(),
                                    Float.parseFloat(data[1].trim()),
                                    data[2].trim(),
                                    new Rut(data[3].trim())
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
                                    Double.parseDouble(data[5].trim()),
                                    data[6].trim(),
                                    Integer.parseInt(data[7].trim())
                            );
                            break;

                        case "addCuadrillaToPlan":
                            addCuadrillaToPlan(
                                    Integer.parseInt(data[0].trim()),
                                    Integer.parseInt(data[1].trim()),
                                    data[2].trim(),
                                    new Rut(data[3].trim())
                            );
                            break;

                        case "addCosechadorToCuadrilla":
                            addCosechadorToCuadrilla(
                                    Integer.parseInt(data[0].trim()),
                                    Integer.parseInt(data[1].trim()),
                                    LocalDate.parse(data[2].trim(), dateFormatter),
                                    LocalDate.parse(data[3].trim(), dateFormatter),
                                    Double.parseDouble(data[4].trim()),
                                    new Rut(data[5].trim())
                            );
                            break;

                        case "changeEstadoPlan":
                            changeEstadoPlan(
                                    Integer.parseInt(data[0].trim()),
                                    EstadoPlan.valueOf(data[1].trim())
                            );
                            break;

                        case "changeEstadoCuartel":
                            changeEstadoCuartel(
                                    data[1].trim(),
                                    Integer.parseInt(data[0].trim()),
                                    EstadoFenologico.valueOf(data[2].trim())
                            );
                            break;

                        case "addPesaje":
                            addPesaje(
                                    Integer.parseInt(data[0].trim()),
                                    new Rut(data[1].trim()),
                                    Integer.parseInt(data[2].trim()),
                                    Integer.parseInt(data[3].trim()),
                                    Float.parseFloat(data[4].trim()),
                                    Calidad.valueOf(data[5].trim())
                            );
                            break;
                    }
                }
            }
        } catch (IOException e) {
            throw new GestionHuertosException("Error al abrir o leer el archivo: " + e.getMessage());
        }
    }
}