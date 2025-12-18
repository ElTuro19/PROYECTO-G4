package vista;

import controlador.ControladorProduccion;
import modelo.*;
import utilidades.*;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestionHuertosUI {

    private final Scanner sc = new Scanner(System.in);
    private final ControladorProduccion control = ControladorProduccion.getInstance();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void menu() {
        try {
            control.readDataFromTextFile("InputDataGestionHuertos.txt");
        } catch (GestionHuertosException e) {
            System.out.println("Error cargando archivo de datos: " + e.getMessage());
        }

        int opcion;
        do {
            System.out.println("..::MENÚ DE OPCIONES::..");
            System.out.println("1. Crear Personas");
            System.out.println("2. Menu Huertos");
            System.out.println("3. Menú Planes De Cosecha");
            System.out.println("4. Menú Listados");
            System.out.println("5. Salir");
            System.out.print("Ingrese opción: ");

            opcion = leerEnteroSeguro();

            switch (opcion) {
                case 1 -> creaPersona();
                case 2 -> subMenuHuertos();
                case 3 -> subMenuPlanes();
                case 4 -> subMenuListados();
                case 5 -> System.out.println("Adios...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 5);
    }


    // PERSONAS

    private void creaPersona() {
        System.out.println("Creando Persona...");
        System.out.println("Rol persona (1=Propietario, 2=Supervisor, 3=Cosechador)");
        int op = leerEnteroSeguro();

        String rut, nombre, email, dirp, dirc, profesion, fechaNa;

        switch (op) {
            case 1 -> {
                System.out.print("Ingrese el rut del propietario: ");
                rut = sc.nextLine();
                System.out.print("Ingrese el nombre del propietario: ");
                nombre = sc.nextLine();
                System.out.print("Ingrese el email del propietario: ");
                email = sc.nextLine();
                System.out.print("Ingrese la dirección del propietario: ");
                dirp = sc.nextLine();
                System.out.print("Ingrese la dirección comercial del propietario: ");
                dirc = sc.nextLine();

                boolean ok = control.createPropietario(rut, nombre, email, dirp, dirc);
                System.out.println(ok ? "El propietario se ha creado correctamente" : "El propietario no se ha podido crear");
            }
            case 2 -> {
                System.out.print("Ingrese el rut del supervisor: ");
                rut = sc.nextLine();
                System.out.print("Ingrese el nombre del supervisor: ");
                nombre = sc.nextLine();
                System.out.print("Ingrese el email del supervisor: ");
                email = sc.nextLine();
                System.out.print("Ingrese la dirección del supervisor: ");
                dirp = sc.nextLine();
                System.out.print("Ingrese la profesión del supervisor: ");
                profesion = sc.nextLine();

                boolean ok = control.createSupervisor(rut, nombre, email, dirp, profesion);
                System.out.println(ok ? "Supervisor creado exitosamente" : "El supervisor no se ha podido crear");
            }
            case 3 -> {
                System.out.print("Ingrese el rut de cosechador: ");
                rut = sc.nextLine();
                System.out.print("Ingrese el nombre del cosechador: ");
                nombre = sc.nextLine();
                System.out.print("Ingrese la dirección del cosechador: ");
                dirp = sc.nextLine();
                System.out.print("Ingrese el email del cosechador: ");
                email = sc.nextLine();
                System.out.print("Ingrese la fecha de nacimiento del cosechador (dd/MM/yyyy): ");
                fechaNa = sc.nextLine();

                try {
                    LocalDate fecha = LocalDate.parse(fechaNa, formatter);
                    boolean ok = control.createCosechador(rut, nombre, email, dirp, fecha);
                    System.out.println(ok ? "Cosechador creado exitosamente" : "El cosechador no se ha podido crear");
                } catch (Exception e) {
                    System.out.println("Error: fecha inválida. Use formato dd/MM/yyyy");
                }
            }
            default -> System.out.println("Opción inválida.");
        }
    }

    private void subMenuHuertos() {
        System.out.println(">>> SUBMENU HUERTOS <<<");
        System.out.println("1) Crear Cultivo");
        System.out.println("2) Crear Huerto");
        System.out.println("3) Agregar Cuarteles A Huerto");
        System.out.println("4) Cambiar Estado Cuartel");
        System.out.println("5) Volver");
        System.out.print("Ingrese opción: ");

        int opcion = leerEnteroSeguro();

        switch (opcion) {
            case 1 -> creaCultivo();
            case 2 -> creaHuerto();
            case 3 -> agregaCuartelesAHuerto();
            case 4 -> cambiarEstadoDeCuartel();
            case 5 -> { /* volver */ }
            default -> System.out.println("Opción inválida.");
        }
    }

    private void creaCultivo() {
        System.out.println("Creando Cultivo...");
        System.out.print("Ingrese el id del cultivo: ");
        int id = leerEnteroSeguro();

        System.out.print("Ingrese la especie del cultivo: ");
        String especie = sc.nextLine();

        System.out.print("Ingrese la variedad del cultivo: ");
        String variedad = sc.nextLine();

        System.out.print("Ingrese el rendimiento del cultivo: ");
        double rendimiento = leerDoubleSeguro();

        boolean ok = control.createCultivo(id, especie, variedad, rendimiento);
        System.out.println(ok ? "Cultivo creado exitosamente" : "El cultivo no se ha podido crear");
    }

    private void creaHuerto() {
        System.out.println("Creando Huerto...");

        System.out.print("Ingrese el nombre del Huerto: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese la superficie del Huerto: ");
        float sup = (float) leerDoubleSeguro();

        System.out.print("Ingrese la ubicación del Huerto: ");
        String ubi = sc.nextLine();

        System.out.print("Ingrese rut del Propietario: ");
        String rut = sc.nextLine();

        boolean ok = control.createHuerto(nombre, sup, ubi, rut);
        System.out.println(ok ? "Huerto creado exitosamente" : "El huerto no se ha podido crear");
    }

    private void agregaCuartelesAHuerto() {
        System.out.println("Agregando cuarteles a huerto...");
        System.out.print("Ingrese el nombre del Huerto: ");
        String nombreHuerto = sc.nextLine();

        System.out.print("Ingrese la cantidad de cuarteles: ");
        int cantCuarteles = leerEnteroSeguro();

        for (int i = 1; i <= cantCuarteles; i++) {
            System.out.println("Cuartel #" + i);

            System.out.print("Ingrese el ID del Cuartel: ");
            int idCuartel = leerEnteroSeguro();

            System.out.print("Ingrese la superficie del Cuartel: ");
            float sup = (float) leerDoubleSeguro();

            System.out.print("Ingrese la id del Cultivo del Cuartel: ");
            int idCult = leerEnteroSeguro();

            boolean ok = control.addCuartelToHuerto(nombreHuerto, idCuartel, sup, idCult);
            System.out.println(ok ? "Cuartel agregado exitosamente" : "No se pudo agregar cuartel (huerto/cultivo/id repetido o no existe)");
        }
    }

    private void cambiarEstadoDeCuartel() {
        System.out.println("Cambiando estado de cuartel...");
        System.out.print("Ingrese el nombre del Huerto: ");
        String nombreHuerto = sc.nextLine();

        System.out.print("Ingrese el ID del cuartel: ");
        int idCuartel = leerEnteroSeguro();

        System.out.println("Ingrese el estado al que quiere cambiar:");
        System.out.println("""
                1) Reposo Invernal
                2) Floracion
                3) Cuaja
                4) Fructificacion
                5) Maduracion
                6) Cosecha
                7) Post Cosecha
                """);

        int cambio = leerEnteroSeguro();
        if (cambio < 1 || cambio > EstadoFenologico.values().length) {
            System.out.println("Error: opción inválida. Debe ser un número entre 1 y 7.");
            return;
        }

        EstadoFenologico nuevoEstado = EstadoFenologico.values()[cambio - 1];

        try {

            control.changeEstadoCuartel(nombreHuerto, idCuartel, nuevoEstado);
            System.out.println("El estado ha sido cambiado exitosamente a " + nuevoEstado);
        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


    private void subMenuPlanes() {
        System.out.println(">>> SUB MENU PLANES DE COSECHA <<<");
        System.out.println("""
                1) Crear Plan de Cosecha
                2) Cambiar Estado de Plan
                3) Agregar Cuadrillas a Plan
                4) Agregar Cosechadores a Cuadrilla
                5) Agregar Pesaje a Cosechador
                6) Pagar Pesajes Impagos del Cosechador
                7) Volver
                """);

        int menuPlan = leerEnteroSeguro();
        if (menuPlan < 1 || menuPlan > 7) {
            System.out.println("Opción inválida: Debe ingresar un número entre 1 y 7");
            return;
        }

        switch (menuPlan) {
            case 1 -> creaPlanDeCosecha();
            case 2 -> cambiarEstadoDePlan();
            case 3 -> agregarCuadrillaToPlan();
            case 4 -> asignaCosechadoresAPlan();
            case 5 -> agregarPesajeACosechador();
            case 6 -> pagarPesajesImpagosCosechador();
            case 7 -> { /* volver */ }
        }
    }

    private void creaPlanDeCosecha() {
        System.out.println("Creando Plan...");

        System.out.print("Ingrese el id del Plan: ");
        int id = leerEnteroSeguro();

        System.out.print("Ingrese el nombre del Plan: ");
        String nombre = sc.nextLine();

        System.out.print("Ingrese la fecha de inicio (dd/MM/yyyy): ");
        String fini = sc.nextLine();

        System.out.print("Ingrese la fecha de finalización (dd/MM/yyyy): ");
        String ffin = sc.nextLine();

        LocalDate inicio, fin;
        try {
            inicio = LocalDate.parse(fini, formatter);
            fin = LocalDate.parse(ffin, formatter);
        } catch (Exception e) {
            System.out.println("Error: formato de fecha inválido. Use dd/MM/yyyy");
            return;
        }

        System.out.print("Ingrese la meta de kg del Plan: ");
        double meta = leerDoubleSeguro();

        System.out.print("Ingrese precio base por kilo: ");
        float precioBase = (float) leerDoubleSeguro();

        System.out.print("Ingrese nombre del Huerto: ");
        String nombreHuerto = sc.nextLine();

        System.out.print("Ingrese el id del Cuartel: ");
        int idCu = leerEnteroSeguro();

        boolean ok = control.createPlanCosecha(id, nombre, inicio, fin, meta, precioBase, nombreHuerto, idCu);
        if (!ok) {
            System.out.println("El plan no se ha podido crear (huerto/cuartel no existe o id repetido).");
            return;
        }
        System.out.println("Plan creado exitosamente");
    }

    private void cambiarEstadoDePlan() {
        System.out.println("Cambiando estado de Plan...");
        System.out.print("Ingrese ID del Plan: ");
        int id = leerEnteroSeguro();

        System.out.println("Ingrese el estado (1=Planificado 2=Ejecutando 3=Cerrado 4=Cancelado): ");
        int op = leerEnteroSeguro();

        if (op < 1 || op > EstadoPlan.values().length) {
            System.out.println("Error: Opción no válida, debe ser un valor entre 1 y 4");
            return;
        }

        EstadoPlan nuevoEstado = EstadoPlan.values()[op - 1];

        try {

            control.changeEstadoPlan(id, nuevoEstado);
            System.out.println("Estado del plan cambiado exitosamente!");
        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarCuadrillaToPlan() {
        System.out.println("Agregando Cuadrilla a Plan de Cosecha...");
        System.out.print("Ingrese ID del Plan: ");
        int idPlan = leerEnteroSeguro();

        System.out.print("Ingrese Nro De Cuadrillas: ");
        int nCuadrillas = leerEnteroSeguro();

        for (int i = 1; i <= nCuadrillas; i++) {
            System.out.println("Cuadrilla #" + i);

            System.out.print("ID Cuadrilla: ");
            int idCuad = leerEnteroSeguro();

            System.out.print("Nombre Cuadrilla: ");
            String nombre = sc.nextLine();

            System.out.print("Rut Supervisor: ");
            String rutSup = sc.nextLine();

            boolean ok = control.addCuadrillatoPlan(idPlan, idCuad, nombre, rutSup);
            System.out.println(ok ? "Cuadrilla agregada exitosamente!" : "No se pudo agregar cuadrilla (plan/supervisor no existe o id repetido)");
        }
    }

    private void asignaCosechadoresAPlan() {
        System.out.println("Asignando cosechadores a un plan de cosecha ...");

        System.out.print("Ingrese id del plan: ");
        int idPlan = leerEnteroSeguro();

        System.out.print("Ingrese id de la cuadrilla: ");
        int idCuadrilla = leerEnteroSeguro();

        System.out.print("Ingrese la cantidad de cosechadores a asignar: ");
        int cant = leerEnteroSeguro();

        for (int i = 1; i <= cant; i++) {
            System.out.println("Cosechador #" + i);

            System.out.print("Fecha inicio asignación (dd/MM/yyyy): ");
            String fini = sc.nextLine();

            System.out.print("Fecha fin asignación (dd/MM/yyyy): ");
            String ffin = sc.nextLine();

            LocalDate desde, hasta;
            try {
                desde = LocalDate.parse(fini, formatter);
                hasta = LocalDate.parse(ffin, formatter);
            } catch (Exception e) {
                System.out.println("Error: fecha inválida. Use dd/MM/yyyy");
                continue;
            }

            System.out.print("Meta kilos (kg): ");
            double meta = leerDoubleSeguro();

            System.out.print("Rut del cosechador: ");
            String rut = sc.nextLine();

            boolean ok = control.addCosechadorToCuadrilla(idPlan, idCuadrilla, desde, hasta, meta, rut);
            System.out.println(ok ? "Cosechador agregado con éxito!" : "No se ha podido agregar cosechador (plan/cuadrilla/cosechador no existe)");
        }
    }

    private void agregarPesajeACosechador() {
        System.out.print("Ingrese ID del Pesaje: ");
        int id = leerEnteroSeguro();

        System.out.print("Ingrese rut del Cosechador: ");
        String rut = sc.nextLine();

        System.out.print("Ingrese ID del Plan: ");
        int idPlan = leerEnteroSeguro();

        System.out.print("Ingrese ID de Cuadrilla: ");
        int idCuad = leerEnteroSeguro();

        System.out.print("Ingrese Cantidad de Kilos: ");
        float cantKilos = (float) leerDoubleSeguro();

        System.out.println("Calidad (1=Excelente 2=Suficiente 3=Deficiente): ");
        int cal = leerEnteroSeguro();
        if (cal < 1 || cal > Calidad.values().length) {
            System.out.println("Error: calidad inválida.");
            return;
        }
        Calidad calidad = Calidad.values()[cal - 1];

        try {
            Rut run = Rut.of(rut);
            control.addPesaje(id, run, idPlan, idCuad, cantKilos, calidad);
            System.out.println("Pesaje agregado exitosamente!");
        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: RUT inválido");
        }
    }

    private void pagarPesajesImpagosCosechador() {
        System.out.print("ID del Pago Pesaje: ");
        int id = leerEnteroSeguro();

        System.out.print("Rut cosechador: ");
        String rut = sc.nextLine();

        try {
            Rut run = Rut.of(rut);
            double monto = control.addPagoPesaje(id, run);
            System.out.println("Pago realizado exitosamente. Monto total: " + monto);
        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: RUT inválido");
        }
    }


    private void subMenuListados() {
        int op;
        do {
            System.out.println(">>> MENÚ LISTADOS <<<");
            System.out.println("""
                1) Listar Cultivos
                2) Listar Huertos
                3) Listar Personas (Propietarios / Supervisores / Cosechadores)
                4) Listar Planes de Cosecha
                5) Listar Pesajes
                6) Listar Pesajes de un Cosechador
                7) Listar Pagos de Pesajes
                8) Volver
                """);
            System.out.print("Ingrese opción: ");
            op = leerEnteroSeguro();

            switch (op) {
                case 1 -> listaCultivos();
                case 2 -> listaHuertos();
                case 3 -> listaPersonas();
                case 4 -> listaPlanesCosecha();
                case 5 -> listaPesajes();
                case 6 -> listaPesajesCosechador();
                case 7 -> listaPagoPesajes();
                case 8 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (op != 8);
    }

    private void listaCultivos() {
        System.out.println("---LISTADO DE CULTIVOS---");
        System.out.printf("%-10s %-20s %-15s %-10s %-5s%n", "ID", "Especie", "Variedad", "Rendimento", "Nro Cuarteles");
        String[] arr = control.listCultivos();
        if (arr.length == 0) {
            System.out.println("(sin elementos)");
            return;
        }
        for (String s : arr) System.out.println(s);
    }

    private void listaHuertos() {
        System.out.println("--LISTADO DE HUERTOS--");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s%n",
                "Nombre", "Superficie", "Ubicación", "Rut propietario", "Nombre Propietario", "Nro Cuarteles");

        String[] arr = control.listHuertos();
        if (arr.length == 0) {
            System.out.println("(sin elementos)");
            return;
        }
        for (String s : arr) System.out.println(s);
    }

    private void listaPersonas() {
        System.out.println("---LISTADO DE PROPIETARIOS---");
        System.out.printf("%-15s %-15s %-20s %-25s %-15s %15s%n", "Rut", "Nombre", "Dirección", "email", "Dirección Comercial", "Nro Huertos");
        String[] propietarios = control.listPropietarios();
        if (propietarios.length == 0) System.out.println("(sin elementos)");
        for (String s : propietarios) System.out.println(s);

        System.out.println("---LISTADO DE SUPERVISORES---");
        System.out.printf("%-15s %-15s %-20s %-25s %-15s %-5s%n", "Rut", "Nombre", "Dirección", "email", "Profesion", "Nombre cuadrilla");
        String[] supervisores = control.listSupervisores();
        if (supervisores.length == 0) System.out.println("(sin elementos)");
        for (String s : supervisores) System.out.println(s);

        System.out.println("---LISTADO DE COSECHADORES---");
        System.out.printf("%-15s %-15s %-20s %-25s %-15s %-5s%n", "Rut", "Nombre", "Dirección", "email", "Fecha Nacimiento", "Nro Cuadrillas");
        String[] cosechadores = control.listCosechadores();
        if (cosechadores.length == 0) System.out.println("(sin elementos)");
        for (String s : cosechadores) System.out.println(s);
    }

    private void listaPlanesCosecha() {
        System.out.println("---LISTADO DE PLANES DE COSECHA---");
        System.out.printf("%-10s %-15s %-22s %-25s %-17s %-17s %17s %15s %20s%n",
                "Id", "Nombre", "Fecha Inicio", "Fecha Termino", "Meta (Kg)", "Precio Base(Kg)", "Estado", "Id Cuartel", "Nombre Huerto");
        String[] planes = control.listPlanesCosecha();
        if (planes.length == 0) {
            System.out.println("(sin elementos)");
            return;
        }
        for (String s : planes) System.out.println(s);
    }

    private void listaPesajes() {
        System.out.println("---LISTADO DE PESAjes---");
        System.out.printf("%-4s %-10s %-13s %-10s %10s %10s %10s %-10s%n",
                "ID", "Fecha", "RutCosech", "Calidad", "Kg", "Precio", "Monto", "Pagado");

        String[] arr = control.listPesajes();
        if (arr.length == 0) {
            System.out.println("(sin elementos)");
            return;
        }
        for (String s : arr) System.out.println(s);
    }

    private void listaPesajesCosechador() {
        System.out.println("---LISTADO DE PESAjes POR COSECHADOR---");
        System.out.print("Ingrese rut del cosechador: ");
        String rutStr = sc.nextLine();

        try {
            Rut rut = Rut.of(rutStr);

            System.out.printf("%-4s %-10s %-10s %10s %10s %10s %-10s%n",
                    "ID", "Fecha", "Calidad", "Kg", "Precio", "Monto", "Pagado");

            String[] arr = control.listPesajesCosechador(rut);
            if (arr.length == 0) {
                System.out.println("(sin elementos)");
                return;
            }
            for (String s : arr) System.out.println(s);

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: RUT inválido");
        }
    }

    private void listaPagoPesajes() {
        System.out.println("---LISTADO DE PAGOS DE PESAJE---");
        System.out.printf("%-4s %-10s %10s %10s %-13s%n",
                "ID", "Fecha", "Monto", "NroPesajes", "RutCosech");

        String[] pagos = control.listPagoPesajes();
        if (pagos.length == 0) {
            System.out.println("(sin elementos)");
            return;
        }
        for (String s : pagos) System.out.println(s);
    }


    private int leerEnteroSeguro() {
        while (true) {
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                return n;
            } catch (Exception e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    private double leerDoubleSeguro() {
        while (true) {
            try {
                double n = Double.parseDouble(sc.nextLine().trim().replace(",", "."));
                return n;
            } catch (Exception e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }
}
