package vista;

import controlador.ControladorProduccion;
import modelo.*;
import utilidades.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class GestionHuertosUI {

    private final Scanner sc = new Scanner(System.in);
    private final ControladorProduccion control = new ControladorProduccion();
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ===================== MENÚ PRINCIPAL =====================

    public void menu() {
        int op = 0;
        do {
            System.out.println("\n=== GESTIÓN DE HUERTOS ===");
            System.out.println("1) Crear Personas");
            System.out.println("2) Gestión de Huertos");
            System.out.println("3) Gestión de Planes de Cosecha");
            System.out.println("4) Listados");
            System.out.println("5) Salir");
            System.out.print("Opción: ");

            try {
                op = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                sc.nextLine();
                op = 0;
            }

            switch (op) {
                case 1 -> crearPersonas();
                case 2 -> menuHuertos();
                case 3 -> menuPlanes();
                case 4 -> menuListados();
                case 5 -> System.out.println("Saliendo del sistema...");
                default -> System.out.println("Opción inválida");
            }
        } while (op != 5);
    }

    // ===================== PERSONAS =====================

    private void crearPersonas() {
        System.out.println("\n--- CREAR PERSONA ---");
        System.out.println("1) Propietario");
        System.out.println("2) Supervisor");
        System.out.println("3) Cosechador");
        System.out.print("Seleccione tipo: ");

        int op = sc.nextInt();
        sc.nextLine();

        try {
            switch (op) {
                case 1 -> {
                    System.out.print("Rut: ");
                    String rut = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Dirección particular: ");
                    String dirP = sc.nextLine();
                    System.out.print("Dirección comercial: ");
                    String dirC = sc.nextLine();

                    boolean ok = control.createPropietario(rut, nombre, email, dirP, dirC);
                    System.out.println(ok ? "Propietario creado" : "Rut ya existe");
                }
                case 2 -> {
                    System.out.print("Rut: ");
                    String rut = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Dirección: ");
                    String dir = sc.nextLine();
                    System.out.print("Profesión: ");
                    String prof = sc.nextLine();

                    boolean ok = control.createSupervisor(rut, nombre, email, dir, prof);
                    System.out.println(ok ? "Supervisor creado" : "Rut ya existe");
                }
                case 3 -> {
                    System.out.print("Rut: ");
                    String rut = sc.nextLine();
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.print("Dirección: ");
                    String dir = sc.nextLine();
                    System.out.print("Fecha nacimiento (dd/MM/yyyy): ");
                    LocalDate fn = LocalDate.parse(sc.nextLine(), fmt);

                    boolean ok = control.createCosechador(rut, nombre, email, dir, fn);
                    System.out.println(ok ? "Cosechador creado" : "Rut ya existe");
                }
                default -> System.out.println("Opción inválida");
            }
        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===================== HUERTOS =====================

    private void menuHuertos() {
        System.out.println("\n--- MENÚ HUERTOS ---");
        System.out.println("1) Crear Cultivo");
        System.out.println("2) Crear Huerto");
        System.out.println("3) Agregar Cuartel a Huerto");
        System.out.println("4) Cambiar Estado Cuartel");
        System.out.println("5) Volver");
        System.out.print("Opción: ");

        int op = sc.nextInt();
        sc.nextLine();

        try {
            switch (op) {
                case 1 -> crearCultivo();
                case 2 -> crearHuerto();
                case 3 -> agregarCuartel();
                case 4 -> cambiarEstadoCuartel();
            }
        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearCultivo() {
        System.out.print("ID cultivo: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Especie: ");
        String esp = sc.nextLine();
        System.out.print("Variedad: ");
        String var = sc.nextLine();
        System.out.print("Rendimiento: ");
        double rend = sc.nextDouble();
        sc.nextLine();

        boolean ok = control.createCultivo(id, esp, var, rend);
        System.out.println(ok ? "Cultivo creado" : "ID repetido");
    }

    private void crearHuerto() {
        System.out.print("Nombre huerto: ");
        String nom = sc.nextLine();
        System.out.print("Superficie: ");
        float sup = sc.nextFloat();
        sc.nextLine();
        System.out.print("Ubicación: ");
        String ubi = sc.nextLine();
        System.out.print("Rut propietario: ");
        String rut = sc.nextLine();

        boolean ok = control.createHuerto(nom, sup, ubi, rut);
        System.out.println(ok ? "Huerto creado" : "No se pudo crear huerto");
    }

    private void agregarCuartel() {
        System.out.print("Nombre huerto: ");
        String h = sc.nextLine();
        System.out.print("ID cuartel: ");
        int idC = sc.nextInt();
        System.out.print("Superficie: ");
        float sup = sc.nextFloat();
        System.out.print("ID cultivo: ");
        int idCult = sc.nextInt();
        sc.nextLine();

        boolean ok = control.addCuartelToHuerto(h, idC, sup, idCult);
        System.out.println(ok ? "Cuartel agregado" : "No se pudo agregar cuartel");
    }

    private void cambiarEstadoCuartel() {
        System.out.print("Nombre huerto: ");
        String h = sc.nextLine();
        System.out.print("ID cuartel: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Estados:");
        for (int i = 0; i < EstadoFenologico.values().length; i++) {
            System.out.println((i + 1) + ") " + EstadoFenologico.values()[i]);
        }
        System.out.print("Seleccione: ");
        int e = sc.nextInt();
        sc.nextLine();

        control.changeEstadoCuartel(h, id, EstadoFenologico.values()[e - 1]);
        System.out.println("Estado cambiado");
    }

    // ===================== PLANES =====================

    private void menuPlanes() {
        System.out.println("\n--- MENÚ PLANES ---");
        System.out.println("1) Crear Plan");
        System.out.println("2) Cambiar Estado Plan");
        System.out.println("3) Agregar Cuadrilla");
        System.out.println("4) Agregar Cosechador");
        System.out.println("5) Agregar Pesaje");
        System.out.println("6) Pagar Pesajes");
        System.out.println("7) Volver");
        System.out.print("Opción: ");

        int op = sc.nextInt();
        sc.nextLine();

        try {
            switch (op) {
                case 1 -> crearPlan();
                case 2 -> cambiarEstadoPlan();
                case 3 -> agregarCuadrilla();
                case 4 -> agregarCosechador();
                case 5 -> agregarPesaje();
                case 6 -> pagarPesajes();
            }
        } catch (GestionHuertosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void crearPlan() {
        System.out.print("ID plan: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Nombre: ");
        String nom = sc.nextLine();
        System.out.print("Inicio (dd/MM/yyyy): ");
        LocalDate ini = LocalDate.parse(sc.nextLine(), fmt);
        System.out.print("Fin estimado (dd/MM/yyyy): ");
        LocalDate fin = LocalDate.parse(sc.nextLine(), fmt);
        System.out.print("Meta kilos: ");
        double meta = sc.nextDouble();
        System.out.print("Precio base kilo: ");
        float precio = sc.nextFloat();
        sc.nextLine();
        System.out.print("Nombre huerto: ");
        String hu = sc.nextLine();
        System.out.print("ID cuartel: ");
        int idC = sc.nextInt();
        sc.nextLine();

        boolean ok = control.createPlanCosecha(id, nom, ini, fin, meta, precio, hu, idC);
        System.out.println(ok ? "Plan creado" : "ID repetido");
    }

    private void cambiarEstadoPlan() {
        System.out.print("ID plan: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < EstadoPlan.values().length; i++)
            System.out.println((i + 1) + ") " + EstadoPlan.values()[i]);

        int e = sc.nextInt();
        sc.nextLine();
        control.changeEstadoPlan(id, EstadoPlan.values()[e - 1]);
        System.out.println("Estado cambiado");
    }

    private void agregarCuadrilla() {
        System.out.print("ID plan: ");
        int idP = sc.nextInt();
        sc.nextLine();
        System.out.print("ID cuadrilla: ");
        int idC = sc.nextInt();
        sc.nextLine();
        System.out.print("Nombre cuadrilla: ");
        String nom = sc.nextLine();
        System.out.print("Rut supervisor: ");
        Rut r = Rut.of(sc.nextLine());

        control.addCuadrillaToPlan(idP, idC, nom, r);
        System.out.println("Cuadrilla agregada");
    }

    private void agregarCosechador() {
        System.out.print("ID plan: ");
        int idP = sc.nextInt();
        System.out.print("ID cuadrilla: ");
        int idC = sc.nextInt();
        sc.nextLine();
        System.out.print("Rut cosechador: ");
        Rut r = Rut.of(sc.nextLine());
        System.out.print("Inicio (dd/MM/yyyy): ");
        LocalDate ini = LocalDate.parse(sc.nextLine(), fmt);
        System.out.print("Fin (dd/MM/yyyy): ");
        LocalDate fin = LocalDate.parse(sc.nextLine(), fmt);
        System.out.print("Meta kilos: ");
        double meta = sc.nextDouble();
        sc.nextLine();

        control.addCosechadorToCuadrilla(idP, idC, ini, fin, meta, r);
        System.out.println("Cosechador asignado");
    }

    private void agregarPesaje() {
        System.out.print("ID pesaje: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Rut cosechador: ");
        Rut r = Rut.of(sc.nextLine());
        System.out.print("ID plan: ");
        int idP = sc.nextInt();
        System.out.print("ID cuadrilla: ");
        int idC = sc.nextInt();
        System.out.print("Cantidad kilos: ");
        float kg = sc.nextFloat();
        System.out.println("Calidad: 1)EXCELENTE 2)SUFICIENTE 3)DEFICIENTE");
        int c = sc.nextInt();
        sc.nextLine();

        control.addPesaje(id, r, idP, idC, kg, Calidad.values()[c - 1]);
        System.out.println("Pesaje registrado");
    }

    private void pagarPesajes() {
        System.out.print("ID pago: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Rut cosechador: ");
        Rut r = Rut.of(sc.nextLine());

        double monto = control.addPagoPesaje(id, r);
        System.out.println("Pago realizado. Monto total: $" + monto);
    }

    // ===================== LISTADOS =====================

    private void menuListados() {
        System.out.println("\n--- LISTADOS ---");
        System.out.println("1) Cultivos");
        System.out.println("2) Huertos");
        System.out.println("3) Planes");
        System.out.println("4) Volver");
        System.out.print("Opción: ");

        int op = sc.nextInt();
        sc.nextLine();

        switch (op) {
            case 1 -> control.listCultivos();
            case 2 -> control.listHuertos();
            case 3 -> control.listPlanesCosecha();
        }
    }
}
