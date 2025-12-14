package controlador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import modelo.*;
import utilidades.*;

public class ControladorProduccion {

    private final ArrayList<Cultivo> cultivos = new ArrayList<>();
    private final ArrayList<Supervisor> supervisores = new ArrayList<>();
    private final ArrayList<PlanCosecha> planesDeCosecha = new ArrayList<>();
    private final ArrayList<Propietario> propietarios = new ArrayList<>();
    private final ArrayList<Cosechador> cosechadores = new ArrayList<>();
    private final ArrayList<Huerto> huertos = new ArrayList<>();
    private final ArrayList<Pesaje> pesajes = new ArrayList<>();
    private final ArrayList<PagoPesaje> pagosPesaje = new ArrayList<>();

    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ControladorProduccion() {}

    // ------------------ HELPERS VALIDACIÓN ------------------

    private void reqStr(String v, String msg) {
        if (v == null || v.isBlank()) throw new GestionHuertosException(msg);
    }

    private void reqPosFloat(float v, String msg) {
        if (v <= 0) throw new GestionHuertosException(msg);
    }

    private void reqPosDouble(double v, String msg) {
        if (v <= 0) throw new GestionHuertosException(msg);
    }

    private void reqPosInt(int v, String msg) {
        if (v <= 0) throw new GestionHuertosException(msg);
    }

    private void reqNotNull(Object o, String msg) {
        if (o == null) throw new GestionHuertosException(msg);
    }

    private Rut validarRut(String rutStr) {
        reqStr(rutStr, "Rut obligatorio");
        return Rut.of(rutStr); // valida DV
    }

    private boolean rutYaExisteEnSistema(String rutStr) {
        // compara sin formato con Rut.of para evitar temas de puntos/guiones
        Rut r = Rut.of(rutStr);

        for (Propietario p : propietarios) if (Rut.of(p.getRut()).equals(r)) return true;
        for (Supervisor s : supervisores) if (Rut.of(s.getRut()).equals(r)) return true;
        for (Cosechador c : cosechadores) if (Rut.of(c.getRut()).equals(r)) return true;

        return false;
    }

    // ------------------ CREATE PERSONAS ------------------

    public boolean createPropietario(String rut, String nombre, String email, String dirParticular, String dirComercial) {
        Rut r = validarRut(rut);
        reqStr(nombre, "Nombre obligatorio");
        reqStr(email, "Email obligatorio");
        reqStr(dirParticular, "Dirección particular obligatoria");
        reqStr(dirComercial, "Dirección comercial obligatoria");

        if (rutYaExisteEnSistema(r.toString())) return false;

        Propietario nuevo = new Propietario(r.toString(), nombre, email, dirParticular, dirComercial);
        propietarios.add(nuevo);
        return true;
    }

    public boolean createSupervisor(String rut, String nombre, String email, String direccion, String profesion) {
        Rut r = validarRut(rut);
        reqStr(nombre, "Nombre obligatorio");
        reqStr(email, "Email obligatorio");
        reqStr(direccion, "Dirección obligatoria");
        reqStr(profesion, "Profesión obligatoria");

        if (rutYaExisteEnSistema(r.toString())) return false;

        Supervisor nuevo = new Supervisor(r.toString(), nombre, email, direccion, profesion);
        supervisores.add(nuevo);
        return true;
    }

    public boolean createCosechador(String rut, String nombre, String email, String direccion, LocalDate fechaNacimiento) {
        Rut r = validarRut(rut);
        reqStr(nombre, "Nombre obligatorio");
        reqStr(email, "Email obligatorio");
        reqStr(direccion, "Dirección obligatoria");
        reqNotNull(fechaNacimiento, "Fecha de nacimiento obligatoria");

        if (rutYaExisteEnSistema(r.toString())) return false;

        Cosechador nuevo = new Cosechador(r.toString(), nombre, email, direccion, fechaNacimiento);
        cosechadores.add(nuevo);
        return true;
    }

    // ------------------ CREATE CULTIVO / HUERTO / CUARTEL ------------------

    public boolean createCultivo(int id, String especie, String variedad, double rendimiento) {
        reqPosInt(id, "Id de cultivo debe ser > 0");
        reqStr(especie, "Especie obligatoria");
        reqStr(variedad, "Variedad obligatoria");
        reqPosDouble(rendimiento, "Rendimiento debe ser > 0");

        for (Cultivo c : cultivos) if (c.getId() == id) return false;

        Cultivo nuevo = new Cultivo(id, especie, variedad, rendimiento);
        cultivos.add(nuevo);
        return true;
    }

    public boolean createHuerto(String nombre, float superficie, String ubicacion, String rutPropietario) {
        reqStr(nombre, "Nombre de huerto obligatorio");
        reqPosFloat(superficie, "Superficie de huerto debe ser > 0");
        reqStr(ubicacion, "Ubicación obligatoria");

        Rut rutProp = validarRut(rutPropietario);

        for (Huerto h : huertos) {
            if (h.getNombre().equalsIgnoreCase(nombre)) return false;
        }

        Propietario propietario = findPropietarioByRut(rutProp)
                .orElseThrow(() -> new GestionHuertosException("No existe propietario con el rut indicado"));

        Huerto nuevo = new Huerto(nombre, superficie, ubicacion, propietario);
        huertos.add(nuevo);

        // si por cualquier cosa falla, preferimos reventar con excepción
        boolean ok = propietario.addHuerto(nuevo);
        if (!ok) throw new GestionHuertosException("El propietario ya tiene un huerto con ese nombre");

        return true;
    }

    public boolean addCuartelToHuerto(String nombreHuerto, int idCuartel, float superficie, int idCultivo) {
        reqStr(nombreHuerto, "Nombre de huerto obligatorio");
        reqPosInt(idCuartel, "Id de cuartel debe ser > 0");
        reqPosFloat(superficie, "Superficie de cuartel debe ser > 0");
        reqPosInt(idCultivo, "Id de cultivo debe ser > 0");

        Huerto huerto = findHuertoByNombre(nombreHuerto)
                .orElseThrow(() -> new GestionHuertosException("No existe huerto con el nombre indicado"));

        Cultivo cultivo = findCultivoById(idCultivo)
                .orElseThrow(() -> new GestionHuertosException("No existe cultivo con el id indicado"));

        // no repetir id cuartel dentro del huerto
        if (huerto.getCuartel(idCuartel) != null) return false;

        // addCuartel de Huerto ya crea y agrega también al cultivo
        boolean ok = huerto.addCuartel(idCuartel, superficie, cultivo);
        if (!ok) return false;

        return true;
    }

    // ------------------ PLANES / CUADRILLAS / ASIGNACIONES ------------------

    public boolean createPlanCosecha(int idPlan, String nom, LocalDate inicio, LocalDate finEstim,
                                     double meta, float precioBase, String nomHuerto, int idCuartel) {
        reqPosInt(idPlan, "Id de plan debe ser > 0");
        reqStr(nom, "Nombre de plan obligatorio");
        reqNotNull(inicio, "Fecha inicio obligatoria");
        reqNotNull(finEstim, "Fecha fin estimada obligatoria");
        if (finEstim.isBefore(inicio)) throw new GestionHuertosException("Fecha fin estimada no puede ser menor a inicio");
        reqPosDouble(meta, "Meta debe ser > 0");
        reqPosFloat(precioBase, "Precio base debe ser > 0");
        reqStr(nomHuerto, "Nombre de huerto obligatorio");
        reqPosInt(idCuartel, "Id de cuartel debe ser > 0");

        for (PlanCosecha pc : planesDeCosecha) if (pc.getId() == idPlan) return false;

        Huerto huerto = findHuertoByNombre(nomHuerto)
                .orElseThrow(() -> new GestionHuertosException("No existe huerto con el nombre indicado"));

        Cuartel cuartel = huerto.getCuartel(idCuartel);
        if (cuartel == null) throw new GestionHuertosException("No existe cuartel con el id indicado en ese huerto");

        PlanCosecha nuevo = new PlanCosecha(idPlan, nom, inicio, finEstim, meta, precioBase, cuartel);
        cuartel.addPlanCosecha(nuevo);
        planesDeCosecha.add(nuevo);
        return true;
    }

    public void addCuadrillaToPlan(int idPlan, int idCuad, String nomCuad, Rut rutSup) {
        reqPosInt(idPlan, "Id plan debe ser > 0");
        reqPosInt(idCuad, "Id cuadrilla debe ser > 0");
        reqStr(nomCuad, "Nombre de cuadrilla obligatorio");
        reqNotNull(rutSup, "Rut supervisor obligatorio");

        PlanCosecha plan = findPlanCosechaById(idPlan)
                .orElseThrow(() -> new GestionHuertosException("No existe un plan con el id indicado"));

        Supervisor supervisor = findSupervisorByRut(rutSup)
                .orElseThrow(() -> new GestionHuertosException("No existe un supervisor con el rut indicado"));

        boolean ok = plan.addCuadrilla(idCuad, nomCuad, supervisor);
        if (!ok) throw new GestionHuertosException("Ya existe cuadrilla con ese id en el plan");

        // set cuadrilla al supervisor (la recién creada)
        for (Cuadrilla c : plan.getCuadrillas()) {
            if (c.getId() == idCuad) {
                supervisor.setCuadrilla(c);
                break;
            }
        }
    }

    public void addCosechadorToCuadrilla(int idPlan, int idCuadrilla, LocalDate fInicio, LocalDate fFin,
                                         double meta, Rut rutCosechador) {
        reqPosInt(idPlan, "Id plan debe ser > 0");
        reqPosInt(idCuadrilla, "Id cuadrilla debe ser > 0");
        reqNotNull(fInicio, "Fecha inicio obligatoria");
        reqNotNull(fFin, "Fecha fin obligatoria");
        if (fFin.isBefore(fInicio)) throw new GestionHuertosException("Fecha fin no puede ser menor a inicio");
        reqPosDouble(meta, "Meta debe ser > 0");
        reqNotNull(rutCosechador, "Rut cosechador obligatorio");

        PlanCosecha plan = findPlanCosechaById(idPlan)
                .orElseThrow(() -> new GestionHuertosException("No existe un plan con el id indicado"));

        Cuadrilla cuadrilla = null;
        for (Cuadrilla c : plan.getCuadrillas()) {
            if (c.getId() == idCuadrilla) { cuadrilla = c; break; }
        }
        if (cuadrilla == null) throw new GestionHuertosException("No existe cuadrilla con ese id en el plan");

        Cosechador cosechador = findCosechadorByRut(rutCosechador)
                .orElseThrow(() -> new GestionHuertosException("No existe un cosechador con el rut indicado"));

        // crea asignación en el cosechador
        CosechadorAsignado ca = new CosechadorAsignado(fInicio, fFin, meta, cuadrilla, cosechador);
        cosechador.addCuadrilla(ca);

        // guarda cosechador en la cuadrilla (controla máximo)
        boolean ok = cuadrilla.addCosechador(fInicio, fFin, meta, cosechador);
        if (!ok) throw new GestionHuertosException("No se pudo agregar cosechador (máximo alcanzado)");
    }

    // ------------------ PESAJE / PAGO ------------------

    public void addPesaje(int id, Rut rutCosechador, int idPlan, int idCuadrilla,
                          float cantidadKg, Calidad calidad) {
        reqPosInt(id, "Id de pesaje debe ser > 0");
        reqNotNull(rutCosechador, "Rut cosechador obligatorio");
        reqPosInt(idPlan, "Id plan debe ser > 0");
        reqPosInt(idCuadrilla, "Id cuadrilla debe ser > 0");
        if (cantidadKg <= 0) throw new GestionHuertosException("Cantidad de kilos debe ser > 0");
        reqNotNull(calidad, "Calidad obligatoria");

        for (Pesaje p : pesajes) {
            if (p.getId() == id) throw new GestionHuertosException("Ya existe un pesaje con id indicado");
        }

        Cosechador cos = findCosechadorByRut(rutCosechador)
                .orElseThrow(() -> new GestionHuertosException("No existe un cosechador con el rut indicado"));

        PlanCosecha plan = findPlanCosechaById(idPlan)
                .orElseThrow(() -> new GestionHuertosException("No existe un plan con el id indicado"));

        if (plan.getEstado() != EstadoPlan.EJECUTANDO) {
            throw new GestionHuertosException("El plan no se encuentra en estado “en ejecución”");
        }

        // asignación válida del cosechador para ese plan y cuadrilla
        CosechadorAsignado asig = cos.getAsignacion(idPlan, idCuadrilla)
                .orElseThrow(() -> new GestionHuertosException(
                        "El cosechador no tiene asignación a la cuadrilla indicada en el plan señalado"
                ));

        LocalDate hoy = LocalDate.now();
        if (hoy.isBefore(asig.getDesde()) || hoy.isAfter(asig.getHasta())) {
            throw new GestionHuertosException("La fecha no está en el rango de la asignación del cosechador a la cuadrilla");
        }

        Cuartel cuart = plan.getCuartel();
        if (cuart.getEstado() != EstadoFenologico.COSECHA) {
            throw new GestionHuertosException("El cuartel no se encuentra en estado fenológico COSECHA");
        }

        Pesaje nuevo = new Pesaje(id, cantidadKg, calidad, LocalDateTime.now(), asig);
        nuevo.setPrecioKg(plan.getPrecioBaseKilo());
        pesajes.add(nuevo);
    }

    public double addPagoPesaje(int id, Rut rutCosechador) {
        reqPosInt(id, "Id de pago debe ser > 0");
        reqNotNull(rutCosechador, "Rut cosechador obligatorio");

        for (PagoPesaje p : pagosPesaje) {
            if (p.getId() == id) throw new GestionHuertosException("Ya existe un pago de pesaje con el id indicado");
        }

        Cosechador cosechador = findCosechadorByRut(rutCosechador)
                .orElseThrow(() -> new GestionHuertosException("No existe un cosechador con el rut indicado"));

        List<Pesaje> impagos = new ArrayList<>();
        for (Pesaje pe : pesajes) {
            if (!pe.getCosechadorAsignado().getCosechador().equals(cosechador)) continue;
            if (!pe.isPagado()) impagos.add(pe);
        }

        if (impagos.isEmpty()) {
            throw new GestionHuertosException("El cosechador no tiene pesajes impagos");
        }

        PagoPesaje pago = new PagoPesaje(id, new Date(), impagos);
        for (Pesaje pe : impagos) pe.setPago(pago);

        pagosPesaje.add(pago);
        return pago.getMonto();
    }

    // ------------------ CAMBIO DE ESTADOS ------------------

    public void changeEstadoPlan(int idPlan, EstadoPlan estado) {
        reqPosInt(idPlan, "Id plan debe ser > 0");
        reqNotNull(estado, "Estado plan obligatorio");

        PlanCosecha plan = findPlanCosechaById(idPlan)
                .orElseThrow(() -> new GestionHuertosException("No existe un plan con el id indicado"));

        plan.setEstado(estado);
    }

    public void changeEstadoCuartel(String nombreHuerto, int idCuartel, EstadoFenologico estado) {
        reqStr(nombreHuerto, "Nombre huerto obligatorio");
        reqPosInt(idCuartel, "Id cuartel debe ser > 0");
        reqNotNull(estado, "Estado fenológico obligatorio");

        Huerto huerto = findHuertoByNombre(nombreHuerto)
                .orElseThrow(() -> new GestionHuertosException("No existe huerto con el nombre indicado"));

        Cuartel cuartel = huerto.getCuartel(idCuartel);
        if (cuartel == null) throw new GestionHuertosException("No existe cuartel con el id indicado en el huerto");

        cuartel.setEstado(estado);
    }

    // ------------------ FINDERS ------------------

    private Optional<Propietario> findPropietarioByRut(Rut rut) {
        for (Propietario p : propietarios) {
            if (Rut.of(p.getRut()).equals(rut)) return Optional.of(p);
        }
        return Optional.empty();
    }

    public Optional<Supervisor> findSupervisorByRut(Rut rut) {
        for (Supervisor s : supervisores) {
            if (Rut.of(s.getRut()).equals(rut)) return Optional.of(s);
        }
        return Optional.empty();
    }

    public Optional<Cosechador> findCosechadorByRut(Rut rut) {
        for (Cosechador c : cosechadores) {
            if (Rut.of(c.getRut()).equals(rut)) return Optional.of(c);
        }
        return Optional.empty();
    }

    private Optional<Cultivo> findCultivoById(int id) {
        for (Cultivo c : cultivos) if (c.getId() == id) return Optional.of(c);
        return Optional.empty();
    }

    public Optional<Huerto> findHuertoByNombre(String nombre) {
        for (Huerto h : huertos) {
            if (h.getNombre().equalsIgnoreCase(nombre)) return Optional.of(h);
        }
        return Optional.empty();
    }

    public Optional<PlanCosecha> findPlanCosechaById(long id) {
        for (PlanCosecha p : planesDeCosecha) if (p.getId() == id) return Optional.of(p);
        return Optional.empty();
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
    public void readDataFromTextFile(String filename) {
        reqStr(filename, "Nombre de archivo obligatorio");

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(";");
                if (parts.length < 2) throw new GestionHuertosException("Formato inválido en cabecera: " + line);

                String operacion = parts[0].trim();
                int n = Integer.parseInt(parts[1].trim());

                for (int i = 0; i < n; i++) {
                    String dataLine = br.readLine();
                    if (dataLine == null) break;
                    dataLine = dataLine.trim();
                    if (dataLine.isEmpty()) { i--; continue; }

                    String[] data = dataLine.split(";");

                    try {
                        switch (operacion) {
                            case "createPropietario" -> createPropietario(data[0], data[1], data[2], data[3], data[4]);
                            case "createSupervisor"  -> createSupervisor(data[0], data[1], data[2], data[3], data[4]);
                            case "createCosechador"  -> createCosechador(data[0], data[1], data[2], data[3], LocalDate.parse(data[4], fmt));
                            case "createCultivo"     -> createCultivo(Integer.parseInt(data[0]), data[1], data[2], Double.parseDouble(data[3]));
                            case "createHuerto"      -> createHuerto(data[0], Float.parseFloat(data[1]), data[2], data[3]);
                            case "addCuartelToHuerto"-> addCuartelToHuerto(data[0], Integer.parseInt(data[1]), Float.parseFloat(data[2]), Integer.parseInt(data[3]));
                            case "createPlanCosecha" -> createPlanCosecha(Integer.parseInt(data[0]), data[1],
                                    LocalDate.parse(data[2], fmt), LocalDate.parse(data[3], fmt),
                                    Double.parseDouble(data[4]), Float.parseFloat(data[5]), data[6], Integer.parseInt(data[7]));
                            case "addCuadrillaToPlan"-> addCuadrillaToPlan(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2], Rut.of(data[3]));
                            case "addCosechadorToCuadrilla" -> addCosechadorToCuadrilla(Integer.parseInt(data[0]), Integer.parseInt(data[1]),
                                    LocalDate.parse(data[2], fmt), LocalDate.parse(data[3], fmt),
                                    Double.parseDouble(data[4]), Rut.of(data[5]));
                            case "changeEstadoPlan"  -> changeEstadoPlan(Integer.parseInt(data[0]), EstadoPlan.valueOf(data[1].trim().toUpperCase()));
                            case "changeEstadoCuartel" -> changeEstadoCuartel(data[0], Integer.parseInt(data[1]),
                                    EstadoFenologico.valueOf(data[2].trim().toUpperCase()));
                            case "addPesaje" -> addPesaje(Integer.parseInt(data[0]), Rut.of(data[1]),
                                    Integer.parseInt(data[2]), Integer.parseInt(data[3]),
                                    Float.parseFloat(data[4]), Calidad.valueOf(data[5].trim().toUpperCase()));
                            default -> throw new GestionHuertosException("Operación desconocida: " + operacion);
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
}
