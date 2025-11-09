package utilidades;
import java.util.Objects;

public final class Rut implements Comparable<Rut> {
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