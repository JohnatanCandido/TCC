package br.com.svo.entities.dto;

import br.com.svo.entities.Cidade;
import br.com.svo.entities.Estado;

import java.io.Serializable;

public class PessoaConsultaDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idPessoa;
    private String nome;
    private String cpf;
    private String zonaEleitoral;
    private String secao;
    private String numeroInscricao;
    private Long idEstado;
    private Long idCidade;
    private Estado estado;
    private Cidade cidade;

//    GETTERS E SETTERS

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getZonaEleitoral() {
        return zonaEleitoral;
    }

    public void setZonaEleitoral(String zonaEleitoral) {
        this.zonaEleitoral = zonaEleitoral;
    }

    public String getSecao() {
        return secao;
    }

    public void setSecao(String secao) {
        this.secao = secao;
    }

    public String getNumeroInscricao() {
        return numeroInscricao;
    }

    public void setNumeroInscricao(String numeroInscricao) {
        this.numeroInscricao = numeroInscricao;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public Long getIdCidade() {
        return idCidade;
    }

    public void setIdCidade(Long idCidade) {
        this.idCidade = idCidade;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.idEstado = estado.getIdEstado();
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.idCidade = cidade.getIdCidade();
        this.cidade = cidade;
    }
}
