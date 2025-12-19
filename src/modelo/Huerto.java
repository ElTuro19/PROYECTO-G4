package modelo;

import utilidades.GestionHuertosException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

public class Huerto implements Serializable {
    private String nombre;
    private float superficie;
    private String ubicacion;
    private Propietario propietario;
    private final ArrayList<Cuartel> cuarteles;

    public Huerto(String nombre, float superficie, String ubicacion, Propietario prop) {
        this.nombre = nombre;
        this.superficie = superficie;
        this.ubicacion = ubicacion;
        this.propietario = prop;
        this.cuarteles = new ArrayList<>();
    }

    public String getNombre() { return nombre; }

    public float getSuperficie() { return superficie; }
    public void setSuperficie(float superficie) { this.superficie = superficie; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public Propietario getPropietario() { return propietario; }
    public void setPropietario(Propietario propietario) { this.propietario = propietario; }

    /**
     * Agrega un cuartel al huerto.
     * - Si el id ya existe, lanza excepción
     * - Si la superficie total excede la superficie del huerto, lanza excepción
     */
    public boolean addCuartel(int id, float sup, Cultivo cult) throws GestionHuertosException {
        if (cult == null) {
            throw new GestionHuertosException("No existe un cultivo con el id indicado");
        }

        for (Cuartel c : cuarteles) {
            if (c.getId() == id) {
                throw new GestionHuertosException("Ya existe un cuartel con el id indicado");
            }
        }

        float superficieUsada = 0f;
        for (Cuartel c : cuarteles) {
            superficieUsada += c.getSuperficie();
        }
        if (superficieUsada + sup > superficie) {
            throw new GestionHuertosException("La superficie del cuartel excede la disponible en el huerto");
        }

        Cuartel nuevo = new Cuartel(id, sup, cult, this);
        cuarteles.add(nuevo);

        // Mantener relación con cultivo
        cult.addCuartel(nuevo);

        return true;
    }

    /**
     * Búsqueda compatible (retorna null si no existe).
     * Para el enunciado, preferir getCuartelOpt().
     */
    public Cuartel getCuartel(int id) {
        return getCuartelOpt(id).orElse(null);
    }

    public Optional<Cuartel> getCuartelOpt(int id) {
        for (Cuartel c : cuarteles) {
            if (c.getId() == id) return Optional.of(c);
        }
        return Optional.empty();
    }

    public ArrayList<Cuartel> getCuarteles() { return cuarteles; }
}
