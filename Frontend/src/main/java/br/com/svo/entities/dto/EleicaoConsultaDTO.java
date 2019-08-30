package br.com.svo.entities.dto;

import java.io.Serializable;
import java.util.Date;

public class EleicaoConsultaDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEleicao;
    private String titulo;
    private Date data;

//    GETTERS E SETTERS

    public Long getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(Long idEleicao) {
        this.idEleicao = idEleicao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
