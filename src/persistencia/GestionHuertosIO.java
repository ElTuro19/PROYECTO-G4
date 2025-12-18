package persistencia;

import modelo.Cultivo;
import modelo.Persona;
import modelo.PlanCosecha;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionHuertosIO implements Serializable {
    private List<Persona> personasLeidas = new ArrayList<>();
    private List<Cultivo> cultivosLeidos = new ArrayList<>();
    private List<PlanCosecha> planesLeidos = new ArrayList<>();

    public Persona[] readPersonas() throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("Personas.obj"))) {

            personasLeidas = (List<Persona>) in.readObject();
            return personasLeidas.toArray(new Persona[0]);
        }
    }

    public Cultivo[] readCultivos() throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("Cultivos.obj"))) {

            cultivosLeidos = (List<Cultivo>) in.readObject();
            return cultivosLeidos.toArray(new Cultivo[0]);
        }
    }

    public PlanCosecha[] readPlanes() throws Exception {
        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream("Planes.obj"))) {

            planesLeidos = (List<PlanCosecha>) in.readObject();
            return planesLeidos.toArray(new PlanCosecha[0]);
        }
    }

    public void savePersonas(Persona[] personas) throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("Personas.obj"))) {

            out.writeObject(personas);
        }
    }

    public void saveCultivos(Cultivo[] cultivos) throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("Cultivos.obj"))) {

            out.writeObject(cultivos);
        }
    }

    public void savePlanesCosecha(PlanCosecha[] planes) throws Exception {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream("Planes.obj"))) {

            out.writeObject(planes);
        }
    }


}
