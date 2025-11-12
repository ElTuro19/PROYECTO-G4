package modelo;

import utilidades.Rut;
import java.util.*;
public class Huerto {
    private final String nombre;
    private final String superficie;
    private final String ubicacion;
    private final Rut rutPropietario;
    private final Map<Integer, Cuartel> cuarteles = new LinkedHashMap<>();

    public Huerto(String nombre, String superficie, String ubicacion, Rut rutPropietario){
        this.nombre = nombre; this.superficie = superficie; this.ubicacion = ubicacion; this.rutPropietario = rutPropietario;
    }
    public String getNombre(){ return nombre; }
    public String getSuperficie(){ return superficie; }
    public String getUbicacion(){ return ubicacion; }
    public Rut getRutPropietario(){ return rutPropietario; }

    public Collection<Cuartel> getCuarteles(){ return cuarteles.values(); }
    public Cuartel getCuartel(int id){ return cuarteles.get(id); }
    public void addCuartel(Cuartel c){ cuarteles.put(c.getIdCuartel(), c); }
}
