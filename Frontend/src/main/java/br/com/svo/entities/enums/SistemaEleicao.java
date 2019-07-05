package br.com.svo.entities.enums;

public enum SistemaEleicao {

    MAIORIA_SIMPLES("Maioria Simples"),
    REPRESENTACAO_PROPORCIONAL("Representação Proporcional");

    private String tipo;
    SistemaEleicao(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }
}
