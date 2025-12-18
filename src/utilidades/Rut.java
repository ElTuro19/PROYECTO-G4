package utilidades;
import java.io.Serializable;
import java.util.Objects;

public final class Rut implements Comparable<Rut>, Serializable {
    private final long numero;
    private final char dv;

    public Rut(long numero, char dv) {
        this.numero = numero;
        this.dv = Character.toUpperCase(dv);
        if(!validar(this.numero, this.dv)){
            throw new IllegalArgumentException("RUT inválido: " + numero + "-" + this.dv);
        }
    }
    public static Rut of(String rutStr) {
        if (rutStr == null) throw new IllegalArgumentException("rut null");
        String s = rutStr.replace(".", "").replace("-", "").trim();
        if (s.length() < 2) throw new IllegalArgumentException("rut inválido: " + rutStr);
        char dv = s.charAt(s.length()-1);
        long num = Long.parseLong(s.substring(0, s.length()-1));
        return new Rut(num, dv);
    }
    public static Rut of(long numero, char dv){ return new Rut(numero, dv); }

    public long getNumero() { return numero; }
    public char getDv() { return dv; }

    public static boolean validar(long numero, char dv) {
        int m = 0, s = 1;
        long t = numero;
        while (t != 0) {
            s = (s + (int) (t % 10) * (9 - (m++ % 6))) % 11;
            t /= 10;
        }
        char esperado = (char) (s != 0 ? s + 47 : 'K');
        return Character.toUpperCase(dv) == esperado;
    }

    @Override public int compareTo(Rut o) {
        int c = Long.compare(this.numero, o.numero);
        if (c != 0) return c;
        return Character.compare(this.dv, o.dv);
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rut)) return false;
        Rut rut = (Rut) o;
        return numero == rut.numero && dv == rut.dv;
    }

    @Override public int hashCode() { return Objects.hash(numero, dv); }

    @Override public String toString() { return String.format("%d-%s", numero, dv); }
}
