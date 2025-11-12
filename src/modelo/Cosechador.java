package modelo;

import utilidades.Rut;
import java.time.LocalDate;

public class Cosechador extends Persona {
    public Cosechador(Rut rut, String nombre, String email, String direccion, String telefono, LocalDate fechaNacimiento, String profesion) {
        super(rut, nombre, email, direccion, telefono, fechaNacimiento, profesion);
    }
}
