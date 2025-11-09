import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class GestionHuertosApp {
    private Scanner sc = new Scanner(System.in);
    private ControlProduccion control = new ControlProduccion();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static void main(String[] args){
        GestionHuertosApp menu =  new GestionHuertosApp();
        menu.menu();
    }
    private void menu(){
        int opcion;
        do{
            System.out.println("..::MENÚ DE OPCIONES::..");
            System.out.println("1. Crear Persona");
            System.out.println("2. Crear Cultivo");
            System.out.println("3. Crear Huerto");
            System.out.println("4. Crear Plan de Cosecha");
            System.out.println("5. Asignar Cosechadores a Plan");
            System.out.println("6. Listar Cultivos");
            System.out.println("7. Listar Huertos");
            System.out.println("8. Listar Personas");
            System.out.println("9. Listar Planes de Cosecha");
            System.out.println("10. Salir");
            opcion = sc.nextInt();
            sc.nextLine();
            switch(opcion){
                case 1:
                    creaPersona();
                    break;
                case 2:
                    creaCultivo();
                    break;
                case 3:
                    creaHuerto();
                    break;
                case 4:
                    creaPlanDeCosecha();
                    break;
                case 5:
                    asignaCosechadoresAPlan();
                    break;
                case 6:
                    listaCultivos();
                    break;
                case 7:
                    listaHuertos();
                    break;
                case 8:
                    listaPersonas();
                    break;
                case 9:
                    listaPlanesCosecha();
                    break;
                case 10:
                    System.out.println("Adios...");
                    break;
            }
        }while(opcion!=10);
        }

        private void creaPersona(){
            String rut, nombre, email, dirp, dirc, profesion, fechaNa;
            int op;
            System.out.println("Creando Persona...");
            System.out.println("Rol persona (1=Propietario, 2=Supervisor, 3=Cosechador)");
            op = sc.nextInt();
            sc.nextLine();
            switch(op) {
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

        private void creaCultivo(){
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
            }else{
                System.out.println("El cultivo no se ha podido crear");
            }
        }

        private void creaHuerto(){
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
            }else{
                System.out.println("El huerto no se ha podido crear");
                return;
            }
            System.out.println("Agregando cuarteles al huerto");
            System.out.println("Ingrese cantidad de cuarteles: ");
            nCuarteles = sc.nextInt();
            for (int i = 0; i < nCuarteles; i++) {
                System.out.println("Ingrese id del cuartel");
                idCuartel = sc.nextInt();
                System.out.println("Ingrese superfice del cuartel");
                supCuartel = sc.nextFloat();
                System.out.println("Ingrese id del cultivo del cuartel");
                idCultivo = sc.nextInt();
                boolean isOk5 = control.addCuartelToHuerto(nombre, idCuartel, supCuartel, idCultivo);
                if (isOk5) {
                    System.out.println("Cuartel creado exitosamente");
                }else{
                    System.out.println("El cuartel no se ha podido crear");
                }
            }
        }

        private void creaPlanDeCosecha(){
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
            }else{
                System.out.println("El plan no se ha podido crear");
                return;
            }
            System.out.println("Agregando cuadrillas al plan de cosecha");
            System.out.println("Nro cuadrillas: ");
            nCuad =  sc.nextInt();
            for(int i=0; i<nCuad; i++){
                System.out.println("Id cuadrilla");
                idCuadrilla = sc.nextInt();
                System.out.println("Nombre cuadrilla");
                nombreCuadrilla = sc.next();
                System.out.println("Rut supervisor");
                rut = sc.next();
                boolean isOk4 = control.addCuadrillatoPlan(id, idCuadrilla, nombreCuadrilla, rut);
                if (isOk4) {
                    System.out.println("Cuadrilla agregada exitosamente");
                }else{
                    System.out.println("La cuadrilla no se ha podido crear");
                }
            }
        }

        private void asignaCosechadoresAPlan(){
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
            for(int i=0; i<cantCosechadores; i++){
                System.out.println("Fecha inicio asignación dd/mm/aaaa");
                fini = sc.next();
                LocalDate fecha = LocalDate.parse(fini, formatter);
                System.out.println("Fecha fin asignación dd/mm/aaaa");
                ffin = sc.next();
                LocalDate fecha2 = LocalDate.parse(ffin, formatter);
                System.out.println("Meta kilos (kg)");
                meta = sc.nextDouble();
                System.out.println("Rut del cosechador: ");
                rut =  sc.next();
                boolean isOk6 = control.addCosechadorToCuadrilla(id, idCuadrilla, fecha, fecha2, meta, rut);
                if (isOk6) {
                    System.out.println("Cosechador agregado con éxito a una cuadrilla del plan de cosecha");
                }else{
                    System.out.println("No se ha podido agregar cosechador al plan...");
                }
            }
        }

        private void listaCultivos(){
            System.out.println("---LISTADO DE CULTIVOS---");
            System.out.printf("%-10s %-20s %-15s %-10s %-5s%n", "ID", "Especie", "Variedad", "Rendimento", "Nro Cuarteles");
            String[] cultivos = control.listCultivos();
            for(int i=0; i<cultivos.length; i++){
                System.out.println(cultivos[i]);
            }
        }

        private void listaHuertos(){
            System.out.println("--LISTADO DE HUERTOS--");
            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s%n", "Nombre", "Superficie", "Ubicación", "Rut propietario", "Nombre Propietario", "Nro Cuarteles");
            String [] huertos = control.listHuertos();
            for(int i=0; i<huertos.length; i++){
                System.out.println(huertos[i]);
            }
        }

        private void listaPersonas(){
            System.out.println("---LISTADO DE PROPIETARIOS---");
            System.out.printf("%-15s %-15s %-20s %-25s %-15s %15s%n","Rut", "Nombre", "Dirección", "email", "Dirección Comercial",  "Nro Huertos");
            String[] propietarios = control.listPropietarios();
            for(int i=0; i<propietarios.length; i++){
                System.out.println(propietarios[i]);
            }
            System.out.println("---LISTADO DE SUPERVISORES---");
            System.out.printf("%-15s %-15s %-20s %-25s %-15s %-5s%n", "Rut", "Nombre", "Dirección", "email", "Profesion", "Nombre cuadrilla");
            String [] supervisores = control.listSupervisores();
            for(int i=0; i<supervisores.length; i++){
                System.out.println(supervisores[i]);
            }
            System.out.println("---LISTADO DE COSECHADORES---");
            System.out.printf("%-15s %-15s %-20s %-25s %-15s %-5s%n", "Rut", "Nombre", "Dirección", "email", "Fecha Nacimiento", "Nro Cuadrillas");
            String [] cosechadores = control.listCosechadores();
            for(int i=0; i<cosechadores.length; i++){
                System.out.println(cosechadores[i]);
            }
        }

        private void listaPlanesCosecha(){
            System.out.println("---LISTADO DE PLANES DE COSECHA---");
            System.out.printf("%-10s %-15s %-22s %-25s %-17s %-17s %17s %15s %20s%n", "Id", "Nombre", "Fecha Inicio", "Fecha Termino", "Meta (Kg)", "Precio Base(Kg)", "Estado", "Id Cuartel", "Nombre Huerto");
            String [] planes = control.listPlanesCosecha();
            for(int i=0; i<planes.length; i++){
                System.out.println(planes[i]);
            }
        }
    }