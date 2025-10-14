import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControlProduccion {

    private List<Propietario> propietarios;
    private List<Supervisor> supervisores;
    private List<Cosechador> cosechadores;
    private List<Cultivo> cultivos;
    private List<Huerto> huertos;
    private List<PlanCosecha> planes;

    public ControlProduccion() {
        propietarios = new ArrayList<>();
        supervisores = new ArrayList<>();
        cosechadores = new ArrayList<>();
        cultivos = new ArrayList<>();
        huertos = new ArrayList<>();
        planes = new ArrayList<>();
        generateTestData();
    }

    public void generateTestData(){
        propietarios.add(new Propietario(new Rut("11.111.111-1"), "Carlos Pérez", "carlos@ejemplo.cl", "Temuco", "Temuco Centro"));
        supervisores.add(new Supervisor(new Rut("22.222.222-2"), "María Soto", "maria@ejemplo.cl", "Ñuble", "Ingeniera Agrónoma"));
        cosechadores.add(new Cosechador(new Rut("33.333.333-3"), "Pedro López", "pedro@ejemplo.cl", "Chillán", LocalDate.of(1990, 5, 12)));
        cultivos.add(new Cultivo(1, "Trigo", "Centeno", 3.5f));
        huertos.add(new Huerto("Los Olivos", 5.2f, "Camino Viejo", propietarios.get(0)));
    }

    public void createPropietario(String rut, String nombre, String email, String dirParticular, String dirComercial) {
        propietarios.add(new Propietario(new Rut(rut), nombre, email, dirParticular, dirComercial));
    }

    public void createSupervisor(String rut, String nombre, String email, String direccion, String profesion) {
        supervisores.add(new Supervisor(new Rut(rut), nombre, email, direccion, profesion));
    }

    public void createCosechador(String rut, String nombre, String email, String direccion, LocalDate fechaNacimiento) {
        cosechadores.add(new Cosechador(new Rut(rut), nombre, email, direccion, fechaNacimiento));
    }

    public void createCultivo(int id, String especie, String variedad, float rendimiento) {
        cultivos.add(new Cultivo(id, especie, variedad, rendimiento));
    }

    public void createHuerto(String nombre, float superficie, String ubicacion, rutPropietario rut) {
        if (rut >= 0 && rut < propietarios.size()) {
            huertos.add(new Huerto(nombre, superficie, ubicacion, propietarios.get(rut)));
        }
    }
    public void listarPropietarios() {
        System.out.println("\n--- Propietarios ---");
        propietarios.forEach(System.out::println);
    }

    public void listarSupervisores() {
        System.out.println("\n--- Supervisores ---");
        supervisores.forEach(System.out::println);
    }

    public void listarCosechadores() {
        System.out.println("\n--- Cosechadores ---");
        cosechadores.forEach(System.out::println);
    }

    public void listarCultivos() {
        System.out.println("\n--- Cultivos ---");
        cultivos.forEach(System.out::println);
    }

    public void listarHuertos() {
        System.out.println("\n--- Huertos ---");
        huertos.forEach(System.out::println);
    }
}
}
