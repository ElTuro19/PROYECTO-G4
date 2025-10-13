import java.util.Date;
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
        String strOpt = "";
        boolean success = false;
        do {
            System.out.println("Creando una persona . . .");
            System.out.print("Rol persona (1=Propietario, 2=Supervisor, 3=Cosechador): ");
            int opt = sc.nextInt();
            System.out.print("Rut : ");
            Rut rut = sc.next();
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Email: ");
            String email = sc.nextLine();
            System.out.print("Direccion:");
            String direccion = sc.nextLine();
            System.out.print("Fecha de Nacimiento (dd/mm/aaaa): ");
        switch (opt) {
            case 1:
                System.out.print("Direccion Profesional: ");
                String dirProf = sc.nextLine();
                success = cp.createPropietario(rut, nombre, email, direccion, dirProf);
                strOpt = "Propietario";
                break;
            case 2:
                System.out.print("Profesion: ");
                String Prof = sc.nextLine();
                success = cp.createSupervisor(rut, nombre, email, direccion, Prof);
                strOpt = "Supervisor";
                break;
            case 3:
                System.out.print("Fecha Nacimiento: ");
                Date dirProf = sc.next();
                success = cp.createCosechador(rut, nombre, email, direccion, dirProf);
                strOpt = "Cosechador";
                break;
            default:
                System.out.println("no existe esa opcion. . ");
                break;
        }
        if (success==false) {System.out.println("Crear "+strOpt+" a fallado");}
        } while (success!=true);
        System.out.println(+strOpt+" creado exitosamente"

    }
    public void creaCultivo() {
        boolean success = false;
        int id;
        String especie, variedad;
        float perf;
        do {
        System.out.println("Creando un Cultivo . . .");
        System.out.print("Identificacion: ");
        id = sc.nextInt();
        System.out.print("Especie: ");
        especie = sc.nextLine();
        System.out.print("Variedad: ");
        variedad = sc.nextLine();
        System.out.print("Rendimiento: ");
        perf = sc.nextFloat();}
        success = cp.createCultivo(id, especie, variedad, perf);
        if (success == false) {System.out.println("No se puede crear un cultivo con un id de uno que ya existe");}
        while (success!=true);
        System.out.println("Cultivo creado exitosamente");
    }
    public void creaHuerto() {
        boolean success = false;
        String nom, ubi;
        float sup;
        Rut rut;
        do {
        System.out.println("Creando un Huerto . . .");
        System.out.print("Nombre: ");
        nom = sc.nextLine();
        System.out.print("Superficie: ");
        sup = sc.nextFloat();
        System.out.print("Ubicacion: ");
        ubi = sc.nextLine();
        System.out.print("Rut Propietario: ");

        System.out.println("Agregando Cuarteles al Huerto . . .");
        System.out.print("Nro. de Cuarteles: ");
        System.out.print("Id Cuartel: ");
        System.out.print("Superficie Cuartel: ");
        System.out.print("Id cultivo del cuartel: ");}
        success=cp.createHuerto(nom, sup, ubi, rutPr);
        while (success!=true)
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