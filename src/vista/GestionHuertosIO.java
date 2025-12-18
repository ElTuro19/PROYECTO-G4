package utilidades;

import java.io.*;
import java.util.ArrayList;

import modelo.Cultivo;
import modelo.Persona;
import modelo.PlanCosecha;

public final class GestionHuertosIO {

    private GestionHuertosIO() { }

    public static void guardarPersonas(String archivo, ArrayList<Persona> personas) throws IOException {
        guardar(archivo, personas);
    }

    public static ArrayList<Persona> cargarPersonas(String archivo) throws IOException, ClassNotFoundException {
        return cargar(archivo);
    }

    public static void guardarCultivos(String archivo, ArrayList<Cultivo> cultivos) throws IOException {
        guardar(archivo, cultivos);
    }

    public static ArrayList<Cultivo> cargarCultivos(String archivo) throws IOException, ClassNotFoundException {
        return cargar(archivo);
    }

    public static void guardarPlanes(String archivo, ArrayList<PlanCosecha> planes) throws IOException {
        guardar(archivo, planes);
    }

    public static ArrayList<PlanCosecha> cargarPlanes(String archivo) throws IOException, ClassNotFoundException {
        return cargar(archivo);
    }

    @SuppressWarnings("unchecked")
    private static <T> ArrayList<T> cargar(String archivo) throws IOException, ClassNotFoundException {
        File f = new File(archivo);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            return (ArrayList<T>) obj;
        }
    }

    private static void guardar(String archivo, Object data) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(data);
        }
    }
}
