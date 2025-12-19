package utilidades;

import java.io.Serializable;

public enum EstadoPlan implements Serializable {
    PLANIFICADO,
    EJECUTANDO,
    CERRADO,
    CANCELADO
}