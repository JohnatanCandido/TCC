package br.com.svo.service.eleicao;

import br.com.svo.business.eleicao.EleicaoBusiness;
import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import br.com.svo.entities.*;
import br.com.svo.entities.dto.ApuracaoCandidatoDTO;
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

    public Long salvar(Eleicao eleicao) throws BusinessException, NoResultException {
        return eleicaoBusiness.salvar(eleicao);
    }

    @Override
    public Eleicao buscaEleicao(Long idEleicao) throws BusinessException, NoResultException {
        return eleicaoBusiness.buscaEleicao(idEleicao);
    }

    @Override
    public List<EleicaoConsultaDTO> consultarEleicoes(EleicaoConsultaDTO filtro) throws BusinessException, NoResultException {
        return eleicaoBusiness.consultarEleicoes(filtro);
    }

    @Override
    public List<Pessoa> consultaPessoas(String filtro) throws BusinessException, NoResultException {
        return eleicaoBusiness.consultaPessoas(filtro);
    }

    @Override
    public List<Partido> consultaPartidos(String filtro) throws BusinessException, NoResultException {
        return eleicaoBusiness.consultaPartidos(filtro);
    }

    @Override
    public void salvarCandidato(Candidato candidato) throws BusinessException, NoResultException {
        eleicaoBusiness.salvarCandidato(candidato);
    }

    @Override
    public List<ApuracaoCandidatoDTO> buscaCandidatos(Long idTurnoCargoRegiao) throws BusinessException, NoResultException {
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
    public void salvarPartido(Partido partido) throws BusinessException, NoResultException {
        eleicaoBusiness.salvarPartido(partido);
    }

    @Override
    public Long salvarColigacao(Coligacao coligacao) throws BusinessException, NoResultException {
        return eleicaoBusiness.salvarColigacao(coligacao);
    }

    @Override
    public List<EleicaoConsultaDTO> consultaEleicoesUsuario() throws BusinessException, NoResultException {
        return eleicaoBusiness.consultaEleicoesUsuario();
    }
}
