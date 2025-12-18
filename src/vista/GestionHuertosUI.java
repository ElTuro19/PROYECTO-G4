package vista;

import controlador.ControladorProduccion;
/// utilidades
import modelo.*;
import utilidades.*;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestionHuertosUI implements Serializable {
    private Scanner sc = new Scanner(System.in);
    private ControladorProduccion control = ControladorProduccion.getInstance();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private GUIcreaPersona gui1 = new GUIcreaPersona();
    private GUIcreaCultivo gui2 = new GUIcreaCultivo();
    private GUIcambioEstadoPlan gui3 = new GUIcambioEstadoPlan();
    public void menu() {
        control.readDataFromTextFile("InputDataGestionHuertos.txt");
        int opcion;
        do {
            System.out.println("..::MENÚ PRINCIPAL::..");
            System.out.println("1. Crear Personas");
            System.out.println("2. Menu Huertos");
            System.out.println("3. Menú Planes De Cosecha");
            System.out.println("4. Menú Listados");
            System.out.println("5.Leer Datos del Sistema");
            System.out.println("6.Guardar Datos del Sistema");
            System.out.println("7.Salir");
            System.out.print("Ingrese opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    gui1.setVisible(true);
                    break;
                case 2:
                    subMenuHuertos();
                    break;
                case 3:
                    subMenuPlanes();
                    break;
                case 4:
                    subMenuListados();
                    break;
                case 5:
                    try {
                        control.readSystemData();
                    }catch(GestionHuertosException ex){
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                    try{
                        control.readDataFromTextFile("InputDataGestionHuertos.txt");
                    }catch(GestionHuertosException ex){
                        System.out.println("ERROR: " + ex.getMessage());
                    }
                    break;
                case 6:
                    System.out.println("IMPLEMENTAR");
                    break;
                case 7:
                    System.out.println("SALIENDO DEL PROGRAMA...");
                default :
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 7);
    }

    private void subMenuHuertos() {
        int opcion;
        System.out.println(">>> SUBMENU HUERTOS <<<");
        System.out.println("1) Crear Cultivo");
        System.out.println("2) Crear Huerto");
        System.out.println("3) Agregar Cuarteles A Huerto");
        System.out.println("4) Cambiar Estado Cuartel");
        System.out.println("5) Volver");
        System.out.print("Ingrese opción: ");
        opcion = sc.nextInt();

        switch (opcion) {
            case 1 -> gui2.setVisible(true);
            case 2 -> creaHuerto();
            case 3 -> agregaCuartelesAHuerto();
            case 4 -> cambiarEstadoDeCuartel();
            case 5 -> System.out.println(" ");
        }
    }

    private void agregaCuartelesAHuerto() {
        System.out.println("Ingrese el nombre del Huerto: ");
        sc.nextLine(); // limpiar posible \n que quedó del menú
        String nombreHuerto = sc.nextLine();

        System.out.print("Ingrese la cantidad de cuarteles: ");
        int cantCuarteles = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < cantCuarteles; i++) {
            System.out.println("Cuartel N. " + (i + 1));
            System.out.print("Ingrese el ID del Cuartel: ");
            int id = sc.nextInt();
            System.out.print("Ingrese la superficie del Cuartel: ");
            float sup = sc.nextFloat();
            System.out.print("Ingrese la id del Cultivo del Cuartel: ");
            int idCult = sc.nextInt();
            sc.nextLine();

            boolean ok = control.addCuartelToHuerto(nombreHuerto, id, sup, idCult);
            if (ok) {
                System.out.println("Cuartel N. " + (i + 1) + " agregado exitosamente");
            } else {
                System.out.println("No se pudo agregar el Cuartel N. " + (i + 1));
            }
        }
    }

    private void cambiarEstadoDeCuartel() {
        sc.nextLine();
        System.out.println("Ingrese el nombre del Huerto:");
        String nombreHuerto = sc.nextLine();

        System.out.println("Ingrese el ID del cuartel:");
        int idCuartel = sc.nextInt();
        sc.nextLine();

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

        int cambio = sc.nextInt();
        sc.nextLine();

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

    private void creaHuerto() {
        String rut, nombre;
        int idCuartel, idCultivo;
        System.out.println("Creando Huerto...");
        String ubi;
        float sup, supCuartel;
        int nCuarteles;
        System.out.println("Ingrese el nombre del Huerto");
        nombre = sc.nextLine();
        System.out.println("Ingrese la superficie del Huerto");
        sup = sc.nextFloat();
        sc.nextLine();
        System.out.println("Ingrese la ubicación del Huerto");
        ubi = sc.nextLine();
        System.out.println("Ingrese rut del Propietario");
        rut = sc.nextLine();
        boolean isOk1 = control.createHuerto(nombre, sup, ubi, rut);
        if (isOk1) {
            System.out.println("Huerto creado exitosamente");
        } else {
            System.out.println("El huerto no se ha podido crear");
            return;
        }
    }

    private void subMenuPlanes(){
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
        int menuPlan = sc.nextInt();
        if(menuPlan < 1 || menuPlan > 7){
            System.out.println("Opción inválida: Debe ingresar un número entre 1 y 7");
            return;
        }
        switch(menuPlan){
            case 1 -> creaPlanDeCosecha();
            case 2 -> gui3.setVisible(true);
            case 3 -> agregarCuadrillaToPlan();
            case 4 -> agregarCosechador();
            case 5 -> agregarPesajeACosechador();
            case 6 -> pagarPesajesImpagosCosechador();
            case 7 -> System.out.println(" ");
        }
    }

    private void agregarCuadrillaToPlan(){
        System.out.println("Agregando Cuadrilla a Plan de Cosecha...");
        System.out.println("Ingrese ID del Plan: ");
        int id = sc.nextInt();
        System.out.println("Ingrese Nro De Cuadrillas: ");
        int nCuadrillas = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < nCuadrillas; i++) {
            System.out.println("ID Cuadrilla: ");
            int idCuad = sc.nextInt();
            sc.nextLine();
            System.out.println("Nombre Cuadrilla: ");
            String nombre = sc.nextLine();
            System.out.println("Rut Supervisor: ");
            String rut = sc.nextLine();

            boolean ok = control.addCuadrillatoPlan(id, idCuad, nombre, rut);
            if (ok) {
                System.out.println("Cuadrilla agregada exitosamente!");
            } else {
                System.out.println("No se pudo agregar la cuadrilla (revise id plan / supervisor / duplicados)");
            }
        }
    }

    private void agregarCosechador(){
        System.out.println("Asignando Cosechadores A Plan...");
        System.out.println("ID del Plan: ");
        int id = sc.nextInt();
        System.out.println("ID de Cuadrilla: ");
        int idCuad = sc.nextInt();
        System.out.println("Nro de Cosechadores a Asignar: ");
        int cantidad = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < cantidad; i++) {
            System.out.println("Fecha de Inicio Asignación (dd/MM/yyyy): ");
            String fechaInicio = sc.nextLine();
            System.out.println("Fecha de Termindo Asignación (dd/MM/yyyy): ");
            String fechaTermino = sc.nextLine();
            System.out.println("Rut cosechador: ");
            String rut = sc.nextLine();
            System.out.println("Meta(Kilos): ");
            double kilos = sc.nextDouble();
            sc.nextLine();

            LocalDate fechaI = LocalDate.parse(fechaInicio, formatter);
            LocalDate fechaT = LocalDate.parse(fechaTermino, formatter);

            try {
                boolean ok = control.addCosechadorToCuadrilla(id, idCuad, fechaI, fechaT, kilos, rut);
                if (ok) {
                    System.out.println("Cosechador agregado exitosamente!");
                } else {
                    System.out.println("No se pudo agregar el cosechador (revise cuadrilla / máximo / duplicado)");
                }
            } catch (GestionHuertosException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void agregarPesajeACosechador(){
        Cuadrilla cuadrilla = null;
        System.out.println("Ingrese ID del Pesaje: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese rut del Cosechador: ");
        String rut = sc.nextLine();
        System.out.println("Ingrese ID del Plan: ");
        int idPlan = sc.nextInt();
        System.out.println("Ingrese ID de Cuadrilla: ");
        int idCuad = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese Cantidad de Kilos: ");
        float cantKilos = sc.nextFloat();
        System.out.println("Calidad (1= Excelente 2=Suficiente 3=Deficiente)");
        int cal = sc.nextInt();
        Calidad calidad = Calidad.values()[cal - 1];
        Rut run = Rut.of(rut);
        control.addPesaje(id, run, idPlan, idCuad, cantKilos, calidad );
    }

    private void pagarPesajesImpagosCosechador(){
        ///System.out.println("Pagando pesajes pendientes de un cosechador: ");
        ///System.out.println("ID del Pago Pesaje: ");
        ///int id = sc.nextInt();
        ///sc.nextLine();
       /// System.out.println("Rut cosechador: ");
       /// String rut = sc.nextLine();
       /// Rut run = Rut.of(rut);
       /// control.addPagoPesaje(id, run);]
        GUIpagoDePesajesPendientes gui4 = new GUIpagoDePesajesPendientes();
        gui4.setLocationRelativeTo(null);
        gui4.pack();
        gui4.setVisible(true);

    }

    private void creaPlanDeCosecha() {
        String rut, nombre, nombreHuerto;
        int id;
        System.out.println("Creando Plan...");
        String fini, ffin;
        double meta;
        String nombreCuadrilla;
        int idCu, idCuadrilla, nCuad;
        float precioBase;
        System.out.println("Ingrese el id del Plan");
        id = sc.nextInt();
        sc.nextLine();
        System.out.println("Ingrese el nombre del Plan");
        nombre = sc.nextLine();
        System.out.println("Ingrese la fecha de inicio del Plan (dd/mmm/aaaa)");
        fini = sc.nextLine();
        LocalDate inicio = LocalDate.parse(fini, formatter);
        System.out.println("Ingrese la fecha de finalizacion del Plan");
        ffin = sc.nextLine();
        LocalDate finalizacion = LocalDate.parse(ffin, formatter);
        System.out.println("Ingrese la meta de kg del Plan");
        meta = sc.nextFloat();
        sc.nextLine();
        System.out.println("Ingrese precio base por kilo");
        precioBase = sc.nextFloat();
        sc.nextLine();
        System.out.println("Ingrese nombre del Huerto en el que usará el plan");
        nombreHuerto = sc.nextLine();
        System.out.println("Ingrese el id del Cuartel");
        idCu = sc.nextInt();
        boolean isOk3 = control.createPlanCosecha(id, nombre, inicio, finalizacion, meta, precioBase, nombreHuerto, idCu);
        if (isOk3) {
            System.out.println("Plan creado exitosamente");
        } else {
            System.out.println("El plan no se ha podido crear");
            return;
        }
        System.out.println("Agregando cuadrillas al plan de cosecha");
        System.out.println("Nro cuadrillas: ");
        nCuad = sc.nextInt();
        for (int i = 0; i < nCuad; i++) {
            System.out.println("Id cuadrilla");
            idCuadrilla = sc.nextInt();
            System.out.println("Nombre cuadrilla");
            nombreCuadrilla = sc.next();
            System.out.println("Rut supervisor");
            rut = sc.next();
            boolean isOk4 = control.addCuadrillatoPlan(id, idCuadrilla, nombreCuadrilla, rut);
            if (isOk4) {
                System.out.println("Cuadrilla agregada exitosamente");
            } else {
                System.out.println("La cuadrilla no se ha podido crear");
            }
        }
    }

    private void listaCultivos() {
        System.out.println("---LISTADO DE CULTIVOS---");
        System.out.printf("%-10s %-20s %-15s %-10s %-5s%n", "ID", "Especie", "Variedad", "Rendimento", "Nro Cuarteles");
        String[] cultivos = control.listCultivos();
        for (int i = 0; i < cultivos.length; i++) {
            System.out.println(cultivos[i]);
        }
    }

    private void listaHuertos() {
        System.out.println("--LISTADO DE HUERTOS--");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s%n", "Nombre", "Superficie", "Ubicación", "Rut propietario", "Nombre Propietario", "Nro Cuarteles");
        String[] huertos = control.listHuertos();
        for (int i = 0; i < huertos.length; i++) {
            System.out.println(huertos[i]);
        }
    }

    private void listaPropietarios() {
        System.out.println("---LISTADO DE PROPIETARIOS---");
        System.out.printf("%-15s %-15s %-20s %-25s %-15s %15s%n", "Rut", "Nombre", "Dirección", "email", "Dirección Comercial", "Nro Huertos");
        String[] propietarios = control.listPropietarios();
        for (int i = 0; i < propietarios.length; i++) {
            System.out.println(propietarios[i]);
        }
    }

    private void listaSupervisores() {
        System.out.println("---LISTADO DE SUPERVISORES---");
        System.out.printf("%-15s %-15s %-20s %-25s %-15s %-5s%n", "Rut", "Nombre", "Dirección", "email", "Profesion", "Nombre cuadrilla");
        String[] supervisores = control.listSupervisores();
        for (int i = 0; i < supervisores.length; i++) {
            System.out.println(supervisores[i]);
        }
    }

    private void listaCosechadores() {


        System.out.println("---LISTADO DE COSECHADORES---");
        System.out.printf("%-15s %-15s %-20s %-25s %-15s %-5s%n", "Rut", "Nombre", "Dirección", "email", "Fecha Nacimiento", "Nro Cuadrillas");
        String[] cosechadores = control.listCosechadores();
        for (int i = 0; i < cosechadores.length; i++) {
           System.out.println(cosechadores[i]);
        }
    }

    private void listaPlanesCosecha() {
        System.out.println("---LISTADO DE PLANES DE COSECHA---");
        System.out.printf("%-10s %-15s %-22s %-25s %-17s %-17s %17s %15s %20s%n", "Id", "Nombre", "Fecha Inicio", "Fecha Termino", "Meta (Kg)", "Precio Base(Kg)", "Estado", "Id Cuartel", "Nombre Huerto");
        String[] planes = control.listPlanesCosecha();
        for (int i = 0; i < planes.length; i++) {
            System.out.println(planes[i]);
        }
    }

    private void subMenuListados() {
        int op;
        do {
            System.out.println(">>> MENÚ LISTADOS <<<");
            System.out.println("""
                1) Listar Personas Propietarios
                2) Listar Personas Supervisores
                3) Listar Personas Cosechadores
                4) Listar Cultivos
                5) Listar Huertos
                6) Listar Planes de Cosecha
                7) Listar Pesajes
                8) Listar Pesajes de un Cosechador
                9) Listar Pagos de Pesajes
                10) Volver
                """);
            System.out.print("Ingrese opción: ");
            op = sc.nextInt();
            sc.nextLine();

            switch (op) {
                case 1 -> listaPropietarios();
                case 2 -> listaSupervisores();
                case 3 -> {
                    listaCosechadores();
                    GUIlistarCosechadores gui3 = new GUIlistarCosechadores();
                    gui3.setLocationRelativeTo(null);
                    gui3.pack();
                    gui3.setVisible(true);
                }
                case 4 -> listaCultivos();
                case 5 -> listaHuertos();
                case 6 -> listaPlanesCosecha();
                case 7 -> listaPesajes();
                case 8 -> listaPesajesCosechador();
                case 9 -> listaPagoPesajes();
                case 10 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (op != 10);
    }

    private void listaPesajes() {
        System.out.println("---LISTADO DE PESAjes---");
        System.out.printf("%-4s %-10s %-13s %-10s %10s %10s %10s %-10s%n",
                "ID", "Fecha", "RutCosech", "Calidad", "Kg", "Precio", "Monto", "Pagado");

        String[] pesajes = control.listPesajes();
        if (pesajes.length == 0) {
            System.out.println("(sin elementos)");
            return;
        }

        for (String linea : pesajes) {
            System.out.println(linea);
        }
    }

    private void listaPesajesCosechador() {
        System.out.println("---LISTADO DE PESAjes POR COSECHADOR---");
        System.out.print("Ingrese rut del cosechador: ");
        String rutStr = sc.nextLine();

        try {
            Rut rut = Rut.of(rutStr);

            System.out.printf("%-4s %-10s %-10s %10s %10s %10s %-10s%n",
                    "ID", "Fecha", "Calidad", "Kg", "Precio", "Monto", "Pagado");

            String[] pesajes = control.listPesajesCosechador(rut);
            if (pesajes.length == 0) {
                System.out.println("(sin elementos)");
                return;
            }

            for (String linea : pesajes) {
                System.out.println(linea);
            }
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

        for (String linea : pagos) {
            System.out.println(linea);
        }
    }
}