package utilidades;

import java.io.Serializable;

public enum EstadoFenologico implements Serializable {
    REPOSO_INVERNAL,
    FLORACION,
    CUAJA,
    FRUCTIFICACION,
    MADURACION,
    COSECHA,
    POSTCOSECHA
}