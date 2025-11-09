import java.util.ArrayList;
import java.util.Optional; 

// import x.GestionHuertosException;
// import x.EstadoFenologico;
// import x.Cultivo;

public class Huerto{
    private String nombre;
    private float superficie;
    private String ubicacion;
    private Propietario propietario; 
    private ArrayList<Cuartel> cuarteles;
    
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

    // --- Métodos de Lógica (Aquí están los cambios) ---

    /**
     * MÉTODO CAMBIADO (Punto 1): Coincide con el UML y lanza excepciones.
     */
    public void addCuartel(int id, float sup, Cultivo cult) throws GestionHuertosException {
        // 1. Validar si ya existe el ID
        if (this.getCuartel(id).isPresent()) { // .isPresent() es de Optional
            throw new GestionHuertosException("Ya existe un cuartel con id indicado");
        }
        
        // 2. Validar superficie
        float superficieActualCuarteles = 0;
        for (Cuartel c : cuarteles) {
            superficieActualCuarteles += c.getSuperficie(); // Asumo que Cuartel tiene getSuperficie()
        }
        
        if ((superficieActualCuarteles + sup) > this.superficie) {
            throw new GestionHuertosException("La superficie del cuartel sobrepasará la superficie del huerto");
        }
        
        // 3. Crear y agregar el cuartel (asumiendo el constructor de Cuartel)
        Cuartel nuevoCuartel = new Cuartel(id, sup, cult); // El UML implica esto
        cuarteles.add(nuevoCuartel);
    }
    
    /**
     * MÉTODO CAMBIADO (Punto 2): Devuelve Optional<Cuartel>
     */
    public Optional<Cuartel> getCuartel(int id) { 
        for (Cuartel c : cuarteles) {
            if (c.getId() == id) { 
                return Optional.of(c); // Devuelve el Cuartel "envuelto" en un Optional
            }
        }
        return Optional.empty(); // Devuelve un Optional vacío si no lo encuentra
    }

    /**
     * MÉTODO CAMBIADO (Punto 3): Devuelve Cuartel[] (Array)
     */
    public Cuartel[] getCuarteles() { 
        return cuarteles.toArray(new Cuartel[0]); // Convierte el ArrayList a Array
    }
    
    /**
     * MÉTODO FALTANTE (Punto 4): Agregado según el UML.
     */
    public void setEstadoCuartel(int id, EstadoFenologico estado) throws GestionHuertosException {
        Optional<Cuartel> opCuartel = this.getCuartel(id);
        
        // 1. Validar si existe
        if (!opCuartel.isPresent()) {
            throw new GestionHuertosException("No existe un cuartel con ese id");
        }
        
        // 2. Obtener el cuartel y cambiar estado
        Cuartel cuartelBuscado = opCuartel.get();
        
        // Asumo que Cuartel.setEstadoFenologico(estado) devuelve boolean
        boolean cambioExitoso = cuartelBuscado.setEstadoFenologico(estado); 
        
        if (!cambioExitoso) {
            throw new GestionHuertosException("No se puede cambiar el estaddo");
        }
    }
}
}