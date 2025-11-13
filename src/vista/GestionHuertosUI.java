package vista;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import controlador.ControladorProduccion;
import enumeraciones.Calidad;
import enumeraciones.EstadoFenologico;
import enumeraciones.EstadoPlan;
import excepciones.GestionHuertosException;
import modelo.Rut;

public class GestionHuertosUI {
    private ControladorProduccion controlador;
    private Scanner scanner;
    private DateTimeFormatter dateFormatter;

    public GestionHuertosUI() {
        this.controlador = new ControladorProduccion();
        this.scanner = new Scanner(System.in);
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    public void iniciar() {
        // Cargar datos iniciales
        try {
            controlador.readDataFromTextFile("InputDataGestionHuertos.txt");
            System.out.println("Datos iniciales cargados exitosamente.");
        } catch (GestionHuertosException e) {
            System.out.println("Advertencia: " + e.getMessage());
        }

        // Menú principal
        mostrarMenuPrincipal();
    }

    private void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n::: MENU PRINCIPAL :::");
            System.out.println("1. Crear Personas");
            System.out.println("2. Menú Huertos");
            System.out.println("3. Menú Planes de Cosecha");
            System.out.println("4. Menú Listados");
            System.out.println("5. Salir");
            System.out.print("Opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1: menuCrearPersonas(); break;
                case 2: menuHuertos(); break;
                case 3: menuPlanesCosecha(); break;
                case 4: menuListados(); break;
                case 5: System.out.println("¡Hasta luego!"); break;
                default: System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }

    private void menuCrearPersonas() {
        int opcion;
        do {
            System.out.println("\n>>> CREAR PERSONAS <<<");
            System.out.println("1. Crear Propietario");
            System.out.println("2. Crear Supervisor");
            System.out.println("3. Crear Cosechador");
            System.out.println("4. Volver");
            System.out.print("Opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1: crearPropietario(); break;
                case 2: crearSupervisor(); break;
                case 3: crearCosechador(); break;
                case 4: break;
                default: System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 4);
    }

    private void crearPropietario() {
        System.out.println("\nCreando un propietario...");

        try {
            System.out.print("RUT: ");
            Rut rut = leerRut();

            System.out.print("Nombre: ");
            String nombre = leerTextoNoVacio();

            System.out.print("Email: ");
            String email = leerTextoNoVacio();

            System.out.print("Dirección: ");
            String direccion = leerTextoNoVacio();

            System.out.print("Dirección comercial: ");
            String direccionComercial = leerTextoNoVacio();

            controlador.createPropietario(rut, nombre, email, direccion, direccionComercial);
            System.out.println("Propietario creado exitosamente");

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearSupervisor() {
        System.out.println("\nCreando un supervisor...");

        try {
            System.out.print("RUT: ");
            Rut rut = leerRut();

            System.out.print("Nombre: ");
            String nombre = leerTextoNoVacio();

            System.out.print("Email: ");
            String email = leerTextoNoVacio();

            System.out.print("Dirección: ");
            String direccion = leerTextoNoVacio();

            System.out.print("Profesión: ");
            String profesion = leerTextoNoVacio();

            controlador.createSupervisor(rut, nombre, email, direccion, profesion);
            System.out.println("Supervisor creado exitosamente");

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearCosechador() {
        System.out.println("\nCreando un cosechador...");

        try {
            System.out.print("RUT: ");
            Rut rut = leerRut();

            System.out.print("Nombre: ");
            String nombre = leerTextoNoVacio();

            System.out.print("Email: ");
            String email = leerTextoNoVacio();

            System.out.print("Dirección: ");
            String direccion = leerTextoNoVacio();

            System.out.print("Fecha nacimiento (dd/mm/aaaa): ");
            LocalDate fechaNacimiento = leerFecha();

            controlador.createCosechador(rut, nombre, email, direccion, fechaNacimiento);
            System.out.println("Cosechador creado exitosamente");

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void menuHuertos() {
        int opcion;
        do {
            System.out.println("\n>>> SUBMENU HUERTOS <<<");
            System.out.println("1. Crear Cultivo");
            System.out.println("2. Crear Huerto");
            System.out.println("3. Agregar Cuarteles a Huerto");
            System.out.println("4. Cambiar Estado Cuartel");
            System.out.println("5. Volver");
            System.out.print("Opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1: crearCultivo(); break;
                case 2: crearHuerto(); break;
                case 3: agregarCuartelesHuerto(); break;
                case 4: cambiarEstadoCuartel(); break;
                case 5: break;
                default: System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }

    private void crearCultivo() {
        System.out.println("\nCreando un cultivo...");

        try {
            System.out.print("Identificación: ");
            int id = leerEnteroPositivo();

            System.out.print("Especie: ");
            String especie = leerTextoNoVacio();

            System.out.print("Variedad: ");
            String variedad = leerTextoNoVacio();

            System.out.print("Rendimiento: ");
            float rendimiento = leerFloatPositivo();

            controlador.createCultivo(id, especie, variedad, rendimiento);
            System.out.println("Cultivo creado exitosamente");

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearHuerto() {
        System.out.println("\nCreando un huerto...");

        try {
            System.out.print("Nombre: ");
            String nombre = leerTextoNoVacio();

            System.out.print("Superficie: ");
            float superficie = leerFloatPositivo();

            System.out.print("Ubicación: ");
            String ubicacion = leerTextoNoVacio();

            System.out.print("RUT propietario: ");
            Rut rutPropietario = leerRut();

            controlador.createHuerto(nombre, superficie, ubicacion, rutPropietario);
            System.out.println("Huerto creado exitosamente");

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarCuartelesHuerto() {
        System.out.println("\nAgregando cuarteles a un huerto...");

        try {
            System.out.print("Nombre del huerto: ");
            String nombreHuerto = leerTextoNoVacio();

            System.out.print("Número de cuarteles: ");
            int nroCuarteles = leerEnteroPositivo();

            for (int i = 0; i < nroCuarteles; i++) {
                System.out.println("\nCuartel " + (i + 1) + ":");

                System.out.print("Id cuartel: ");
                int idCuartel = leerEnteroPositivo();

                System.out.print("Superficie cuartel: ");
                float superficie = leerFloatPositivo();

                System.out.print("Id cultivo del cuartel: ");
                int idCultivo = leerEnteroPositivo();

                controlador.addCuartelToHuerto(nombreHuerto, idCuartel, superficie, idCultivo);
                System.out.println("Cuartel agregado exitosamente al huerto");
            }

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cambiarEstadoCuartel() {
        System.out.println("\nCambiando estado de un cuartel...");

        try {
            System.out.print("Id cuartel: ");
            int idCuartel = leerEnteroPositivo();

            System.out.print("Nombre del huerto: ");
            String nombreHuerto = leerTextoNoVacio();

            System.out.println("Nuevo Estado de Cuartel: [1=Reposo invernal, 2=Floracion, 3=Cuaja, 4=Fructificacion, 5=Maduracion, 6=Cosecha, 7=Postcosecha]");
            int opcionEstado = leerEnteroEnRango(1, 7);

            EstadoFenologico estado = EstadoFenologico.values()[opcionEstado - 1];

            controlador.changeEstadoCuartel(nombreHuerto, idCuartel, estado);
            System.out.println("Estado del cuartel cambiado exitosamente");

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void menuPlanesCosecha() {
        int opcion;
        do {
            System.out.println("\n>>> SUBMENU PLANES DE COSECHA <<<");
            System.out.println("1. Crear Plan de Cosecha");
            System.out.println("2. Cambiar Estado de Plan");
            System.out.println("3. Agregar Cuadrillas a Plan");
            System.out.println("4. Agregar Cosechadores a Cuadrilla");
            System.out.println("5. Agregar Pesaje a Cosechador");
            System.out.println("6. Pagar Pesajes Impagos de Cosechador");
            System.out.println("7. Volver");
            System.out.print("Opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1: crearPlanCosecha(); break;
                case 2: cambiarEstadoPlan(); break;
                case 3: agregarCuadrillasPlan(); break;
                case 4: agregarCosechadoresCuadrilla(); break;
                case 5: agregarPesaje(); break;
                case 6: pagarPesajesImpagos(); break;
                case 7: break;
                default: System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 7);
    }

    private void crearPlanCosecha() {
        System.out.println("\nCreando un plan de cosecha...");

        try {
            System.out.print("Id del plan: ");
            int id = leerEnteroPositivo();

            System.out.print("Nombre del plan: ");
            String nombrePlan = leerTextoNoVacio();

            System.out.print("Fecha de inicio (dd/mm/aaaa): ");
            LocalDate fechaInicio = leerFecha();

            System.out.print("Fecha de término (dd/mm/aaaa): ");
            LocalDate fechaTermino = leerFecha();

            if (fechaTermino.isBefore(fechaInicio)) {
                System.out.println("Error: La fecha de término debe ser posterior a la fecha de inicio");
                return;
            }

            System.out.print("Meta (kilos): ");
            double metaKilos = leerDoublePositivo();

            System.out.print("Precio base por kilo: ");
            double precioBase = leerDoublePositivo();

            System.out.print("Nombre Huerto: ");
            String nombreHuerto = leerTextoNoVacio();

            System.out.print("Id cuartel: ");
            int idCuartel = leerEnteroPositivo();

            controlador.createPlanCosecha(id, nombrePlan, fechaInicio, fechaTermino, metaKilos, precioBase, nombreHuerto, idCuartel);
            System.out.println("Plan de cosecha creado exitosamente");

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cambiarEstadoPlan() {
        System.out.println("\nCambiando estado de un plan...");

        try {
            System.out.print("Id Plan: ");
            int idPlan = leerEnteroPositivo();

            System.out.println("Nuevo Estado del plan: [1=Planificado, 2=Ejecutando, 3=Cerrado, 4=Cancelado]");
            int opcionEstado = leerEnteroEnRango(1, 4);

            EstadoPlan estado = EstadoPlan.values()[opcionEstado - 1];

            controlador.changeEstadoPlan(idPlan, estado);
            System.out.println("Estado del plan cambiado exitosamente");

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarCuadrillasPlan() {
        System.out.println("\nAgregando cuadrillas a un plan de cosecha...");

        try {
            System.out.print("Id del plan: ");
            int idPlan = leerEnteroPositivo();

            System.out.print("Número de cuadrillas: ");
            int nroCuadrillas = leerEnteroPositivo();

            for (int i = 0; i < nroCuadrillas; i++) {
                System.out.println("\nCuadrilla " + (i + 1) + ":");

                System.out.print("Id cuadrilla: ");
                int idCuadrilla = leerEnteroPositivo();

                System.out.print("Nombre cuadrilla: ");
                String nombreCuadrilla = leerTextoNoVacio();

                System.out.print("Rut supervisor: ");
                Rut rutSupervisor = leerRut();

                controlador.addCuadrillaToPlan(idPlan, idCuadrilla, nombreCuadrilla, rutSupervisor);
                System.out.println("Cuadrilla agregada exitosamente al plan de cosecha");
            }

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarCosechadoresCuadrilla() {
        System.out.println("\nAsignando cosechadores a un plan de cosecha...");

        try {
            System.out.print("Id del plan: ");
            int idPlan = leerEnteroPositivo();

            System.out.print("Id cuadrilla: ");
            int idCuadrilla = leerEnteroPositivo();

            System.out.print("Número cosechadores a asignar: ");
            int nroCosechadores = leerEnteroPositivo();

            for (int i = 0; i < nroCosechadores; i++) {
                System.out.println("\nCosechador " + (i + 1) + ":");

                System.out.print("Fecha de inicio asignación (dd/mm/aaaa): ");
                LocalDate fechaInicio = leerFecha();

                System.out.print("Fecha de término asignación (dd/mm/aaaa): ");
                LocalDate fechaTermino = leerFecha();

                if (fechaTermino.isBefore(fechaInicio)) {
                    System.out.println("Error: La fecha de término debe ser posterior a la fecha de inicio");
                    continue;
                }

                System.out.print("Meta (kilos): ");
                double metaKilos = leerDoublePositivo();

                System.out.print("Rut cosechador: ");
                Rut rutCosechador = leerRut();

                controlador.addCosechadorToCuadrilla(idPlan, idCuadrilla, fechaInicio, fechaTermino, metaKilos, rutCosechador);
                System.out.println("Cosechador asignado exitosamente a una cuadrilla del plan de cosecha");
            }

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarPesaje() {
        System.out.println("\nAgregando pesaje a un cosechador...");

        try {
            System.out.print("Id pesaje: ");
            int id = leerEnteroPositivo();

            System.out.print("Rut Cosechador: ");
            Rut rutCosechador = leerRut();

            System.out.print("Id plan: ");
            int idPlan = leerEnteroPositivo();

            System.out.print("Id cuadrilla: ");
            int idCuadrilla = leerEnteroPositivo();

            System.out.print("Cantidad de kilos: ");
            float cantidadKg = leerFloatPositivo();

            System.out.println("Calidad: [1=Excelente, 2=Suficiente, 3=Deficiente]");
            int opcionCalidad = leerEnteroEnRango(1, 3);

            Calidad calidad = Calidad.values()[opcionCalidad - 1];

            controlador.addPesaje(id, rutCosechador, idPlan, idCuadrilla, cantidadKg, calidad);
            System.out.println("Pesaje agregado exitosamente al cosechador");

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void pagarPesajesImpagos() {
        System.out.println("\nPagando pesajes pendientes de un cosechador...");

        try {
            System.out.print("Id pago pesaje: ");
            int id = leerEnteroPositivo();

            System.out.print("Rut cosechador: ");
            Rut rutCosechador = leerRut();

            controlador.addPagoPesaje(id, rutCosechador);

            // Calcular monto total pagado
            double montoTotal = 0;
            String[] pagos = controlador.listPagosPesajes();
            for (String pago : pagos) {
                if (pago.startsWith(id + ";")) {
                    String[] partes = pago.split(";");
                    montoTotal = Double.parseDouble(partes[5].trim());
                    break;
                }
            }

            System.out.printf("Monto pagado al cosechador: $%,.1f\n", montoTotal);

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void menuListados() {
        int opcion;
        do {
            System.out.println("\n>>> SUBMENU LISTADOS <<<");
            System.out.println("1. Listado de Propietarios");
            System.out.println("2. Listado de Supervisores");
            System.out.println("3. Listado de Cosechadores");
            System.out.println("4. Listado de Cultivos");
            System.out.println("5. Listado de Huertos");
            System.out.println("6. Listado de Planes de Cosecha");
            System.out.println("7. Listado Pesajes");
            System.out.println("8. Listado Pesajes de un Cosechador");
            System.out.println("9. Listado de Pagos");
            System.out.println("10. Volver");
            System.out.print("Opción: ");

            opcion = leerEntero();

            switch (opcion) {
                case 1: listarPropietarios(); break;
                case 2: listarSupervisores(); break;
                case 3: listarCosechadores(); break;
                case 4: listarCultivos(); break;
                case 5: listarHuertos(); break;
                case 6: listarPlanes(); break;
                case 7: listarPesajes(); break;
                case 8: listarPesajesCosechador(); break;
                case 9: listarPagos(); break;
                case 10: break;
                default: System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 10);
    }

    // Métodos de listado
    private void listarPropietarios() {
        System.out.println("\nLISTADO DE PROPIETARIOS");
        System.out.println("---");
        String[] lista = controlador.listPropietarios();
        if (lista.length == 0) {
            System.out.println("No existen propietarios registrados.");
        } else {
            System.out.println("Rut\tNombre\tDirección\temail\tDirección comercial\tNro. huertos");
            for (String item : lista) {
                System.out.println(item);
            }
        }
    }

    private void listarSupervisores() {
        System.out.println("\nLISTADO DE SUPERVISORES");
        System.out.println("---");
        String[] lista = controlador.listSupervisores();
        if (lista.length == 0) {
            System.out.println("No existen supervisores registrados.");
        } else {
            System.out.println("Rut\tNombre\tDirección\temail\tProfesión\tNomenclatura\tKgs pesados\t#Pjes.impagos");
            for (String item : lista) {
                System.out.println(item);
            }
        }
    }

    private void listarCosechadores() {
        System.out.println("\nLISTADO DE COSECHADORES");
        System.out.println("---");
        String[] lista = controlador.listCosechadores();
        if (lista.length == 0) {
            System.out.println("No existen cosechadores registrados.");
        } else {
            System.out.println("Rut\tNombre\tDirección\temail\tFecha Nac.\tNro.Cuadrillas\tMonto impago $\tMonto pagado $");
            for (String item : lista) {
                System.out.println(item);
            }
        }
    }

    private void listarCultivos() {
        System.out.println("\nLISTADO DE CULTIVOS");
        System.out.println("---");
        String[] lista = controlador.listCultivos();
        if (lista.length == 0) {
            System.out.println("No existen cultivos registrados.");
        } else {
            System.out.println("Id\tEspecie\tVariedad\tRendimiento %\tNro. cuarteles");
            for (String item : lista) {
                System.out.println(item);
            }
        }
    }

    private void listarHuertos() {
        System.out.println("\nLISTADO DE HUERTOS");
        System.out.println("---");
        String[] lista = controlador.listHuertos();
        if (lista.length == 0) {
            System.out.println("No existen huertos registrados.");
        } else {
            System.out.println("Nombre\tSuperficie\tUbicación\tRut propietario\tNombre propietario\tNro. cuarteles");
            for (String item : lista) {
                System.out.println(item);
            }
        }
    }

    private void listarPlanes() {
        System.out.println("\nLISTADO DE PLANES DE COSECHA");
        System.out.println("---");
        String[] lista = controlador.listPlanes();
        if (lista.length == 0) {
            System.out.println("No existen planes de cosecha registrados.");
        } else {
            System.out.println("Id\tNombre\tFecha inicio\tFecha término\tMeta kg\tPrecio base Kg\tEstado\tId cuartel\tNombre huerto\tNro. cuadrillas\tMeta %");
            for (String item : lista) {
                System.out.println(item);
            }
        }
    }

    private void listarPesajes() {
        System.out.println("\nLISTADO DE PESAJES");
        System.out.println("---");
        String[] lista = controlador.listPesajes();
        if (lista.length == 0) {
            System.out.println("No existen pesajes registrados.");
        } else {
            System.out.println("Id\tRut Cosechador\tNombre Cosechador\tPlan\tCuadrilla\tKilos\tCalidad\tFecha\tMonto\tFecha Pago");
            for (String item : lista) {
                System.out.println(item);
            }
        }
    }

    private void listarPesajesCosechador() {
        System.out.println("\nLISTADO DE PESAJES DE UN COSECHADOR");
        System.out.println("---");
        System.out.print("Ingrese RUT del cosechador: ");
        String rutStr = scanner.nextLine().trim();

        try {
            Rut rut = new Rut(rutStr);
            String[] lista = controlador.listPesajes(rut);
            if (lista.length == 0) {
                System.out.println("No existen pesajes registrados para el cosechador indicado.");
            } else {
                System.out.println("Id\tPlan\tCuadrilla\tFecha\tKilos\tCalidad\tCultivo\tMonto\tFecha Pago");
                for (String item : lista) {
                    System.out.println(item);
                }
            }
        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarPagos() {
        System.out.println("\nLISTADO DE PAGOS");
        System.out.println("---");
        String[] lista = controlador.listPagosPesajes();
        if (lista.length == 0) {
            System.out.println("No existen pagos registrados.");
        } else {
            System.out.println("Id\tRut Cosechador\tNombre Cosechador\tFecha\tPesajes Pagados\tMonto Total");
            for (String item : lista) {
                System.out.println(item);
            }
        }
    }

    // Métodos auxiliares para lectura y validación
    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Por favor ingrese un número válido: ");
            }
        }
    }

    private int leerEnteroPositivo() {
        while (true) {
            int valor = leerEntero();
            if (valor > 0) {
                return valor;
            }
            System.out.print("Por favor ingrese un número positivo: ");
        }
    }

    private int leerEnteroEnRango(int min, int max) {
        while (true) {
            int valor = leerEntero();
            if (valor >= min && valor <= max) {
                return valor;
            }
            System.out.printf("Por favor ingrese un número entre %d y %d: ", min, max);
        }
    }

    private double leerDouble() {
        while (true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Por favor ingrese un número válido: ");
            }
        }
    }

    private double leerDoublePositivo() {
        while (true) {
            double valor = leerDouble();
            if (valor > 0) {
                return valor;
            }
            System.out.print("Por favor ingrese un número positivo: ");
        }
    }

    private float leerFloat() {
        while (true) {
            try {
                return Float.parseFloat(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Por favor ingrese un número válido: ");
            }
        }
    }

    private float leerFloatPositivo() {
        while (true) {
            float valor = leerFloat();
            if (valor > 0) {
                return valor;
            }
            System.out.print("Por favor ingrese un número positivo: ");
        }
    }

    private String leerTextoNoVacio() {
        while (true) {
            String texto = scanner.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.print("Este campo no puede estar vacío. Ingrese un valor: ");
        }
    }

    private LocalDate leerFecha() {
        while (true) {
            try {
                String fechaStr = scanner.nextLine().trim();
                return LocalDate.parse(fechaStr, dateFormatter);
            } catch (DateTimeParseException e) {
                System.out.print("Formato de fecha inválido. Use dd/mm/aaaa: ");
            }
        }
    }

    private Rut leerRut() {
        while (true) {
            String rutStr = scanner.nextLine().trim();
            // Validación básica de RUT (puedes mejorar esta validación)
            if (rutStr.matches("\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]")) {
                return new Rut(rutStr);
            }
            System.out.print("RUT inválido. Formato: 12.345.678-9: ");
        }
    }

    public static void main(String[] args) {
        GestionHuertosUI ui = new GestionHuertosUI();
        ui.iniciar();
    }
}