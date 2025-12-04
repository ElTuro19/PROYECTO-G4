package vista;

import controlador.ControladorProduccion;
/// utilidades
import modelo.*;
import utilidades.*;

import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GestionHuertosUI {
    private Scanner sc = new Scanner(System.in);
    private ControladorProduccion control = ControladorProduccion.getInstance();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void menu() {
        try {
            control.readDataFromTextFile("InputDataGestionHuertos.txt");
        } catch (GestionHuertosException e) {
            System.out.println("Error al leer datos del archivo: " + e.getMessage());
        }
        int opcion;
        do {
            System.out.println("..::MENÚ DE OPCIONES::..");
            System.out.println("1. Crear Personas");
            System.out.println("2. Menu Huertos");
            System.out.println("3. Menú Planes De Cosecha");
            System.out.println("4. Menú Listados");
            System.out.println("5.Salir");
            System.out.print("Ingrese opción: ");
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    creaPersona();
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
                    System.out.println("Adios...");
                default :
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 5);
    }

    private void creaPersona() {
        String rut, nombre, email, dirp, dirc, profesion, fechaNa;
        int op;
        System.out.println("Creando Persona...");
        System.out.println("Rol persona (1=Propietario, 2=Supervisor, 3=Cosechador)");
        op = sc.nextInt();
        sc.nextLine();
        switch (op) {
            case 1:
                System.out.println("Ingrese el rut del propietario");
                rut = sc.nextLine();
                System.out.println("Ingrese el nombre del propietario");
                nombre = sc.nextLine();
                System.out.println("Ingrese el email del propietario");
                email = sc.nextLine();
                System.out.println("Ingrese la dirección  del propietario");
                dirp = sc.nextLine();
                System.out.println("Ingrese la dirección comercial del propietario");
                dirc = sc.nextLine();
                boolean isOk = control.createPropietario(rut, nombre, email, dirp, dirc);
                if (isOk) {
                    System.out.println("El propietario se ha creado correctamente");
                } else {
                    System.out.println("El propietario no se ha podido crear");
                }
                break;
            case 2:
                System.out.println("Ingrese el rut del supervisor");
                rut = sc.nextLine();
                System.out.println("Ingrese el nombre del supervisor");
                nombre = sc.nextLine();
                System.out.println("Ingrese el email del supervisor");
                email = sc.nextLine();
                System.out.println("Ingrese la dirección del supervisor");
                dirp = sc.nextLine();
                System.out.println("Ingese la profesión del supervisor");
                profesion = sc.nextLine();

                boolean isOk1 = control.createSupervisor(rut, nombre, email, dirp, profesion);
                if (isOk1) {
                    System.out.println("Supervisor creado exitosamente");
                } else {
                    System.out.println("El supervisor no se ha podido crear");
                }
                break;
            case 3:
                System.out.println("Ingrese el rut de cosechador");
                rut = sc.next();
                System.out.println("Ingrese el nombre del cosechador");
                nombre = sc.nextLine();
                nombre = sc.nextLine();
                System.out.println("Ingrese la dirección del cosechador");
                dirp = sc.nextLine();
                System.out.println("Ingrese el email del cosechador");
                email = sc.nextLine();
                System.out.println("Ingrese la fecha de nacimiento del cosechador");
                fechaNa = sc.nextLine();
                LocalDate localDate = LocalDate.parse(fechaNa, formatter);
                boolean isOk2 = control.createCosechador(rut, nombre, email, dirp, localDate);
                if (isOk2) {
                    System.out.println("Cosechador creado exitosamente");
                } else {
                    System.out.println("El cosechador no se ha podido crear");
                }
                break;
        }
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
            case 1 -> creaCultivo();
            case 2 -> creaHuerto();
            case 3 -> agregaCuartelesAHuerto();
            case 4 -> cambiarEstadoDeCuartel();
            case 5 -> System.out.println(" ");
        }
    }

    private void agregaCuartelesAHuerto() {
        System.out.println("Ingrese el nombre del Huerto: ");
        String nombreHuerto = sc.nextLine();
            System.out.print("Ingrese la cantidad de cuarteles: ");
            int cantCuarteles = sc.nextInt();
            sc.nextLine();
            for(int i=0; i<cantCuarteles;i++) {
                System.out.println("Cuartel #" + i);
                System.out.print("Ingrese el ID del Cuartel: ");
                int id = sc.nextInt();
                System.out.print("Ingrese la superficie del Cuartel: ");
                float sup = sc.nextFloat();
                System.out.print("Ingrese la id del Cultivo del Cuartel: ");
                int idCult = sc.nextInt();
                sc.nextLine();
                boolean c = control.addCuartelToHuerto(nombreHuerto, id, sup, idCult);
                System.out.println("Cuartel #" + i + " agregado exitosamente");
            }
    }

    private void cambiarEstadoDeCuartel() {
        System.out.println("Ingrese el nombre del Huerto:");
        String nombreHuerto = sc.nextLine();

        try {

            System.out.println("Ingrese el ID del cuartel:");
            int idCuartel = sc.nextInt();
            sc.nextLine(); //Diossss odio limpiar el buffer, se me olvida ):(
            System.out.println("Ingrese el estado al que quiere cambiar");
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
            control.changeEstadoCuartel(nombreHuerto, idCuartel, nuevoEstado);

            System.out.println("El estado ha sido cambiado exitosamente a " + nuevoEstado);

        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Error: debe ingresar un número válido.");
            sc.nextLine();
        }
    }

    private void creaCultivo() {
        String especie, variedad;
        int id;
        System.out.println("Creando Cultivo...");
        double rendimiento;
        System.out.println("Ingrese el id del cultivo");
        id = sc.nextInt();
        System.out.println("Ingrese la especie del cultivo");
        especie = sc.next();
        System.out.println("Ingrese la variedad del cultivo");
        variedad = sc.next();
        System.out.println("Ingrese el rendimiento del cultivo");
        rendimiento = sc.nextDouble();
        boolean isOk = control.createCultivo(id, especie, variedad, rendimiento);
        if (isOk) {
            System.out.println("Cultivo creado exitosamente");
        } else {
            System.out.println("El cultivo no se ha podido crear");
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
            case 2 -> cambiarEstadoDePlan();
            case 3 -> agregarCuadrillaToPlan();
            case 4 -> agregarCosechador();
            case 5 -> agregarPesajeACosechador();
            case 6 -> pagarPesajesImpagosCosechador();
            case 7 -> System.out.println(" ");
        }
    }

    private void cambiarEstadoDePlan(){
        System.out.println("Cambiando estado de Plan...");
        System.out.println("Ingrese ID del Plan");
        int id = sc.nextInt();

        try{
            System.out.println("Ingrese el estado que desea " +
                    "(1=Planificado 2=Ejecutando 3=Cerrado 4=Cancelado)");
            int op = sc.nextInt();

            if(op < 1 || op > EstadoFenologico.values().length){
                System.out.println("Error: Opción no válida, debe ser un valor entre 1 y 4");
                return;
            }

            EstadoPlan nuevoEstado = EstadoPlan.values()[op-1];
            control.changeEstadoPlan(id, nuevoEstado);

            System.out.println("Estado del plan cambiado exitosamente!");
        }catch(GestionHuertosException e){
            System.out.println("Error: " + e.getMessage());
        }catch(InputMismatchException e){
            System.out.println("Error: Debe ser un número válido");
        }
    }

    private void agregarCuadrillaToPlan(){
        System.out.println("Agregando Cuadrilla a Plan de Cosecha...");
        System.out.println("Ingrese ID del Plan: ");
        int id = sc.nextInt();
        System.out.println("Ingrese Nro De Cuadrillas: ");
        int nCuadrillas = sc.nextInt();

        try{
            for(int i=0; i<nCuadrillas; i++){
                System.out.println("ID Cuadrilla: ");
                int idCuad = sc.nextInt();
                sc.nextLine();
                System.out.println("Nombre Cuadrilla: ");
                String nombre = sc.nextLine();
                System.out.println("Rut Supervisor: ");
                String rut = sc.nextLine();
                Rut run = Rut.of(rut);

                try{
                    control.addCuadrillatoPlan(id, idCuad, nombre, run);
                    System.out.println("Cuadrilla agregada exitosamente!");
                }catch (GestionHuertosException e){
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }catch(GestionHuertosException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarCosechador(){
        System.out.print("ID plan: ");
        int idP = sc.nextInt();
        System.out.print("ID cuadrilla: ");
        int idC = sc.nextInt(); sc.nextLine();

        System.out.print("Fecha inicio (dd/MM/yyyy): ");
        LocalDate fi = LocalDate.parse(sc.nextLine(), formatter);
        System.out.print("Fecha fin (dd/MM/yyyy): ");
        LocalDate ff = LocalDate.parse(sc.nextLine(), formatter);

        System.out.print("Meta kg: ");
        double meta = sc.nextDouble(); sc.nextLine();

        System.out.print("RUT cosechador: ");
        String rut = sc.nextLine();

        boolean ok = control.addCosechadorToCuadrilla(idP, idC, fi, ff, meta, rut);
        if (ok == true) {
            System.out.println("Cosechador agregado exitosamente!");
        } else {System.out.println("Fallo en agregar cosechador");}


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
        System.out.println("Pagando pesajes pendientes de un cosechador: ");
        System.out.println("ID del Pago Pesaje: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("Rut cosechador: ");
        String rut = sc.nextLine();
        Rut run = Rut.of(rut);
        control.addPagoPesaje(id, run);
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
            Rut run = Rut.of(rut);
            boolean isOk4 = control.addCuadrillatoPlan(id, idCuadrilla, nombreCuadrilla, run);
            if (isOk4) {
                System.out.println("Cuadrilla agregada exitosamente");
            } else {
                System.out.println("La cuadrilla no se ha podido crear");
            }
        }
    }

    private void asignaCosechadoresAPlan() {
        String rut, fini, ffin;
        int id, idCuadrilla;
        double meta;
        int cantCosechadores;
        System.out.println("Asignando cosechadores a un plan de cosecha ...");
        System.out.println("Ingrese id del plan");
        id = sc.nextInt();
        System.out.println("Ingrese id de la cuadrilla");
        idCuadrilla = sc.nextInt();
        System.out.println("Ingrese la cantidad de cosechadores a asignar");
        cantCosechadores = sc.nextInt();
        for (int i = 0; i < cantCosechadores; i++) {
            System.out.println("Fecha inicio asignación dd/mm/aaaa");
            fini = sc.next();
            LocalDate fecha = LocalDate.parse(fini, formatter);
            System.out.println("Fecha fin asignación dd/mm/aaaa");
            ffin = sc.next();
            LocalDate fecha2 = LocalDate.parse(ffin, formatter);
            System.out.println("Meta kilos (kg)");
            meta = sc.nextDouble();
            System.out.println("Rut del cosechador: ");
            rut = sc.next();
            boolean isOk6 = control.addCosechadorToCuadrilla(id, idCuadrilla, fecha, fecha2, meta, rut);
            if (isOk6) {
                System.out.println("Cosechador agregado con éxito a una cuadrilla del plan de cosecha");
            } else {
                System.out.println("No se ha podido agregar cosechador al plan...");
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

    private void listaPersonas() {
        System.out.println("---LISTADO DE PROPIETARIOS---");
        System.out.printf("%-15s %-15s %-20s %-25s %-15s %15s%n", "Rut", "Nombre", "Dirección", "email", "Dirección Comercial", "Nro Huertos");
        String[] propietarios = control.listPropietarios();
        for (int i = 0; i < propietarios.length; i++) {
            System.out.println(propietarios[i]);
        }
        System.out.println("---LISTADO DE SUPERVISORES---");
        System.out.printf("%-15s %-15s %-20s %-25s %-15s %-5s%n", "Rut", "Nombre", "Dirección", "email", "Profesion", "Nombre cuadrilla");
        String[] supervisores = control.listSupervisores();
        for (int i = 0; i < supervisores.length; i++) {
            System.out.println(supervisores[i]);
        }
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
            op = sc.nextInt();
            sc.nextLine();

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