package br.com.svo.entities.enums;

public enum TipoCargo {
    FEDERAL("Federal"),
    ESTADUAL("Estadual"),
    MUNICIPAL("Municipal");

    private String tipo;
    TipoCargo(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public static TipoCargo parse(String tipo) {
        for (TipoCargo tipoCargo: values()) {
            if (tipoCargo.getTipo().equals(tipo)) {
                return tipoCargo;
            }
        }
        return null;
    }
}
