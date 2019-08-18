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
    private Date inicio;
    private Date termino;
    private List<EleicaoCargo> eleicoesCargos = new ArrayList<>();
    private Cidade cidade;
    private Estado estado;
    private TipoEleicao tipoEleicao;

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

    public List<EleicaoCargo> getEleicoesCargos() {
        return eleicoesCargos;
    }

    public void setEleicoesCargos(List<EleicaoCargo> eleicoesCargos) {
        this.eleicoesCargos = eleicoesCargos;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public TipoEleicao getTipoEleicao() {
        return tipoEleicao;
    }

    public void setTipoEleicao(TipoEleicao tipoEleicao) {
        this.tipoEleicao = tipoEleicao;
    }
}
