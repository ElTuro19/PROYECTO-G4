package persistencia;

import modelo.Cultivo;
import modelo.Persona;
import modelo.PlanCosecha;
import utilidades.GestionHuertosException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionHuertosIO implements Serializable {
    private List<Persona> personasLeidas = new ArrayList<>();
    private List<Cultivo> cultivosLeidos = new ArrayList<>();
    private List<PlanCosecha> planesLeidos = new ArrayList<>();

    public Persona[] readPersonas() throws GestionHuertosException {
        File archivo = new File("Personas.obj");
        if (!archivo.exists() || !archivo.isFile()) {
            throw new GestionHuertosException(
                    "No se encontró el archivo Personas.obj"
            );
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(archivo))) {

            personasLeidas = (List<Persona>) in.readObject();
            return personasLeidas.toArray(new Persona[0]);

        } catch (IOException | ClassNotFoundException e) {
            throw new GestionHuertosException("Error al leer el archivo Personas.obj");
        }
    }

    public Cultivo[] readCultivos() throws GestionHuertosException {
        File archivo = new File("Cultivos.obj");
        if (!archivo.exists() || !archivo.isFile()) {
            throw new GestionHuertosException("No se encontro el archivo Cultivos.obj");
        }
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("Cultivos.obj"))) {

            cultivosLeidos = (List<Cultivo>) in.readObject();
            return cultivosLeidos.toArray(new Cultivo[0]);
        }catch(IOException | ClassNotFoundException e){
            throw new GestionHuertosException("Error al leer el archivo Cultivos.obj");
        }
    }

    public PlanCosecha[] readPlanes() throws GestionHuertosException {
        File archivo = new File("Planes.obj");
        if (!archivo.exists() || !archivo.isFile()) {
            throw new GestionHuertosException("No se encontro el archivo Planes.obj");
        }
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("Planes.obj"))) {

            planesLeidos = (List<PlanCosecha>) in.readObject();
            return planesLeidos.toArray(new PlanCosecha[0]);
        }catch(IOException | ClassNotFoundException e){
            throw new GestionHuertosException("Error al leer el archivo Planes.obj");
        }
    }

    public void savePersonas(Persona[] personas) throws GestionHuertosException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("Personas.obj"))) {

            out.writeObject(personas);
        }catch(IOException e){
            throw new GestionHuertosException("Error al escribir el archivo Personas.obj");
        }
    }

    public void saveCultivos(Cultivo[] cultivos) throws GestionHuertosException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("Cultivos.obj"))) {

            out.writeObject(cultivos);
        }catch(IOException e){
            throw new GestionHuertosException("Error al escribir el archivo Cultivos.obj");
        }
    }

    public void savePlanesCosecha(PlanCosecha[] planes) throws GestionHuertosException {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("Planes.obj"))) {

            out.writeObject(planes);
        } catch (IOException e) {
            throw new GestionHuertosException("Error al escribir el archivo Planes.obj");
        }
    }

}
