package br.com.svo.service.eleicao;

import br.com.svo.business.eleicao.EleicaoBusiness;
import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.*;
import br.com.svo.entities.dto.EleicaoConsultaDTO;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class EleicaoService implements EleicaoServiceLocal, Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private EleicaoBusiness eleicaoBusiness;

    public List<Cargo> consultaCargos() {
        return eleicaoBusiness.consultaCargos();
    }

    public Long salvar(Eleicao eleicao) throws BusinessException {
        return eleicaoBusiness.salvar(eleicao);
    }

    @Override
    public Eleicao buscaEleicao(Long idEleicao) throws BusinessException {
        return eleicaoBusiness.buscaEleicao(idEleicao);
    }

    @Override
    public List<EleicaoConsultaDTO> consultarEleicoes(EleicaoConsultaDTO filtro) throws BusinessException {
        return eleicaoBusiness.consultarEleicoes(filtro);
    }

    @Override
    public List<Pessoa> consultaPessoas(String filtro) throws BusinessException {
        return eleicaoBusiness.consultaPessoas(filtro);
    }

    @Override
    public List<Partido> consultaPartidos(String filtro) throws BusinessException {
        return eleicaoBusiness.consultaPartidos(filtro);
    }

    @Override
    public void salvarCandidato(Candidato candidato) throws BusinessException {
        eleicaoBusiness.salvarCandidato(candidato);
    }

    @Override
    public List<Candidato> buscaCandidatos(Long idTurnoCargoRegiao) throws BusinessException {
        return eleicaoBusiness.buscaCandidatos(idTurnoCargoRegiao);
    }

    @Override
    public List<Coligacao> buscarColigacoes(Long idEleicao) {
        return eleicaoBusiness.buscarColigacoes(idEleicao);
    }

    @Override
    public List<Partido> buscarPartidos(Long idColigacao) {
        return eleicaoBusiness.buscarPartidos(idColigacao);
    }

    @Override
    public void salvarPartido(Partido partido) throws BusinessException {
        eleicaoBusiness.salvarPartido(partido);
    }

    @Override
    public Long salvarColigacao(Coligacao coligacao) throws BusinessException {
        return eleicaoBusiness.salvarColigacao(coligacao);
    }

    @Override
    public List<EleicaoConsultaDTO> consultaEleicoesUsuario() {
        return eleicaoBusiness.consultaEleicoesUsuario();
    }
}
