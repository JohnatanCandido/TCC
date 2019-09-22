package br.com.svo.entities.dto;

import java.io.Serializable;
import java.util.Date;

public class EleicaoConsultaDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEleicao;
    private Long idTurno;
    private String titulo;
    private Date data;
    private Date inicio;
    private Date termino;
    private boolean votou;

//    GETTERS E SETTERS

    public Long getIdEleicao() {
        return idEleicao;
    }

    public void setIdEleicao(Long idEleicao) {
        this.idEleicao = idEleicao;
    }

    public Long getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Long idTurno) {
        this.idTurno = idTurno;
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

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getTermino() {
        return termino;
    }

    public void setTermino(Date termino) {
        this.termino = termino;
    }

    public boolean isVotou() {
        return votou;
    }

    public void setVotou(boolean votou) {
        this.votou = votou;
    }

    public boolean isAberta() {
        Date agora = new Date();
        return !inicio.after(agora) && !termino.before(agora);
    }
}
