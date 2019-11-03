package br.com.svo.service.eleicao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import br.com.svo.entities.*;
import br.com.svo.entities.dto.ApuracaoCandidatoDTO;
import br.com.svo.entities.dto.EleicaoConsultaDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EleicaoServiceLocal {

    List<Cargo> consultaCargos();

    Long salvar(Eleicao eleicao) throws BusinessException, NoResultException;

    Eleicao buscaEleicao(Long idEleicao) throws BusinessException, NoResultException;

    List<EleicaoConsultaDTO> consultarEleicoes(EleicaoConsultaDTO filtro) throws BusinessException, NoResultException;

    List<Pessoa> consultaPessoas(String filtro) throws BusinessException, NoResultException;

    List<Partido> consultaPartidos(String filtro) throws BusinessException, NoResultException;

    void salvarCandidato(Candidato candidato) throws BusinessException, NoResultException;

    List<ApuracaoCandidatoDTO> buscaCandidatos(Long idTurnoCargoRegiao) throws BusinessException, NoResultException;

    List<Coligacao> buscarColigacoes(Long idEleicao);

    List<Partido> buscarPartidos(Long idColigacao);

    void salvarPartido(Partido partido) throws BusinessException, NoResultException;

    Long salvarColigacao(Coligacao coligacao) throws BusinessException, NoResultException;

    List<EleicaoConsultaDTO> consultaEleicoesUsuario() throws BusinessException, NoResultException;

    void confirmarEleicao(Long idEleicao) throws BusinessException;

    String apurarTurno(Long idTurno) throws BusinessException;
}
