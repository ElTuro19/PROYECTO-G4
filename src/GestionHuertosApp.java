import java.util.Scanner;
import java.util.ArrayList;
public class GestionHuertosApp {
    Scanner sc = new Scanner(System.in);
    ControlProduccion cp = new ControlProduccion();
    ArrayList<ControlProduccion> cplist = new ArrayList<>();
    public static void main(String[] args) {




    }
    public void menu() {
        System.out.println("*** Sistema de Gestion de Huertos ***");
        System.out.println("MENU DE OPCIONES");
        System.out.println("1. Crear Persona");
        System.out.println("2. Crear Cultivo");
        System.out.println("3. Crear Huerto");
        System.out.println("4. Crear Plan de Cosecha");
        System.out.println("5. Asignar Cosechadores al Plan");
        System.out.println("6. Listar Cultivos");
        System.out.println("7. Listar Huertos");
        System.out.println("8. Listar Personas");
        System.out.println("9. Listar Planes de Cosecha");
        System.out.println("10. Salir");}
    public void creaPersona() {
        boolean success = false;
        System.out.println("Creando una persona . . .");
        System.out.print("Rol persona (1=Propietario, 2=Supervisor, 3=Cosechador): ");
        do {
            int opt = sc.nextInt();
        switch (opt) {
            case 1:
            cp.createPropietario()
            case 2:
            case 3:
            default:
        }} while (success=false)
        System.out.print("Rut : ");
        Rut rut
        System.out.print("Nombre: ");
        System.out.print("Email: ");
        System.out.print("Direccion:");
        System.out.print("Fecha de Nacimiento (dd/mm/aaaa): ");
    }
    public void creaCultivo() {
        System.out.println("Creando un Cultivo . . .");
        System.out.print("Identificacion: ");
        System.out.print("Especie: ");
        System.out.print("Variedad: ");
        System.out.print("Rendimiento: ");

    }
    public void creaHuerto() {
        System.out.println("Creando un Huerto . . .");
        System.out.print("Nombre: ");
        System.out.print("Superficie: ");
        System.out.print("Ubicacion: ");
        System.out.print("Rut Propietario: ");
        System.out.println("Agregando Cuarteles al Huerto . . .");
        System.out.print("Nro. de Cuarteles: ");
        System.out.print("Id Cuartel: ");
        System.out.print("Superficie Cuartel: ");
        System.out.print("Id cultivo del cuartel: ");
    }
    public void creaPlanDeCosecha() {
        System.out.println("Creando un plan de cosecha...");
        System.out.print("Id del plan: ");
        System.out.print("Nombre del plan: ");
        System.out.print("Fecha de inicio (dd/mm/aaaa): ");
        System.out.print("Fecha de término (dd/mm/aaaa): ");
        System.out.print("Meta (kilos): ");
        System.out.print("Precio base por kilo: ");
        System.out.print("Nombre Huerto: ");
        System.out.print("Id cuartel: ");
        System.out.println();
        System.out.println("Plan de cosecha creado exitosamente");
        System.out.println();
        System.out.println("Agregando cuadrillas al plan de cosecha");
        System.out.print("Nro. de cuadrillas: ");
        System.out.println();
        System.out.print("Id cuadrilla: ");
        System.out.print("Nombre cuadrilla: ");
        System.out.print("Rut supervisor: ");
        System.out.print("Cuadrilla agregada exitosamente al plan de cosecha");

    }
    public void asignaCosechadoresAPlan() {}
    public void listaCultivos() {}
    public void listaHuertos() {}
    public void listaPersonas() {}
    public void listaPlanesCosecha() {}
}