package utilidades;


import java.io.Serializable;
import java.util.Objects;

public final class Rut implements Comparable<Rut> {

    private final long numero;
    private final char dv;

    public Rut(long numero, char dv) {
        this.numero = numero;
        this.dv = Character.toUpperCase(dv);
        if (!validar(this.numero, this.dv)) {
            throw new IllegalArgumentException("RUT inválido: " + numero + "-" + dv);
        }
    }

    public static Rut of(String rutStr) {
        if (rutStr == null) throw new IllegalArgumentException("Rut nulo");
        String s = rutStr.replace(".", "").replace("-", "").trim();
        char dv = s.charAt(s.length() - 1);
        long num = Long.parseLong(s.substring(0, s.length() - 1));
        return new Rut(num, dv);
    }

    public long getNumero() { return numero; }
    public char getDv() { return dv; }

    public static boolean validar(long numero, char dv) {
        int m = 0, s = 1;
        while (numero != 0) {
            s = (s + (int) (numero % 10) * (9 - m++ % 6)) % 11;
            numero /= 10;
        }
        char esperado = (char) (s != 0 ? s + 47 : 'K');
        return Character.toUpperCase(dv) == esperado;
    }

    @Override
    public int compareTo(Rut o) {
        int c = Long.compare(numero, o.numero);
        return (c != 0) ? c : Character.compare(dv, o.dv);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Rut r)) return false;
        return numero == r.numero && dv == r.dv;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, dv);
    }

    @Override
    public String toString() {
        return numero + "-" + dv;
    }
}
