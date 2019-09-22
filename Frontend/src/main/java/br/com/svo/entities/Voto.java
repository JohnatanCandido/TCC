package br.com.svo.entities;

import br.com.svo.entities.dto.CandidatoDTO;

import java.io.Serializable;

public class Voto implements Serializable {

    public static final long serialVersionUID = 1L;

    private Long idTurnoCargoRegiao;
    private String nomeCargo;
    private Integer numero;
    private Long idCandidato;
    private Long idPartido;
    private String nome;
    private String nomeVice;
    private String partido;
    private String partidoVice;
    private int index_voto;

    public void selecionaCandidato(CandidatoDTO candidato) {
        limpaCampos();
        if (candidato.isCandidato())
            idCandidato = candidato.getId();
        else
            idPartido = candidato.getId();
        nome = candidato.getNome();
        partido = candidato.getPartido();
        if (candidato.getVice() != null) {
            nomeVice = candidato.getVice();
            partidoVice = candidato.getPartidoVice();
        }
    }

    public void limpaCampos() {
        idCandidato = null;
        idPartido = null;
        nome = null;
        partido = null;
        nomeVice = null;
        partidoVice = null;
    }

//    GETTERS E SETTERS

    public Long getIdTurnoCargoRegiao() {
        return idTurnoCargoRegiao;
    }

    public void setIdTurnoCargoRegiao(Long idTurnoCargoRegiao) {
        this.idTurnoCargoRegiao = idTurnoCargoRegiao;
    }

    public String getNomeCargo() {
        return nomeCargo;
    }

    public void setNomeCargo(String nomeCargo) {
        this.nomeCargo = nomeCargo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Long getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Long idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Long getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(Long idPartido) {
        this.idPartido = idPartido;
    }

    public String getNome() {
        if (partido != null)
            return nome + " - " + partido;
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeVice() {
        if (partidoVice != null)
            return nomeVice + " - " + partidoVice;
        return nomeVice;
    }

    public void setNomeVice(String nomeVice) {
        this.nomeVice = nomeVice;
    }

    public int getIndex_voto() {
        return index_voto;
    }

    public void setIndex_voto(int index_voto) {
        this.index_voto = index_voto;
    }
}
