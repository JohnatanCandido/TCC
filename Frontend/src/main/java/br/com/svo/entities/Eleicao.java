package br.com.svo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Eleicao implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idEleicao;
    private String titulo;
    private String observacao;
    private boolean confirmada;
    private List<Turno> turnos = new ArrayList<>();
    private boolean usuarioVotou;

    public Eleicao() {
        this.turnos.add(new Turno(this, 1));
    }

    public boolean isAberta() {
        Date agora = new Date();
        return turnos.stream().anyMatch(t -> !t.getInicio().after(agora) && !t.getTermino().before(agora));
    }

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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public boolean isConfirmada() {
        return confirmada;
    }

    public void setConfirmada(boolean confirmada) {
        this.confirmada = confirmada;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<Turno> turnos) {
        this.turnos = turnos;
    }

    public boolean isUsuarioVotou() {
        return usuarioVotou;
    }

    public void setUsuarioVotou(boolean usuarioVotou) {
        this.usuarioVotou = usuarioVotou;
    }
}
