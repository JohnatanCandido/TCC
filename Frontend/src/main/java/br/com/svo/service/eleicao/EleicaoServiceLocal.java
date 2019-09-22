package br.com.svo.service.eleicao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.*;
import br.com.svo.entities.dto.EleicaoConsultaDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EleicaoServiceLocal {

    List<Cargo> consultaCargos();

    Long salvar(Eleicao eleicao) throws BusinessException;

    Eleicao buscaEleicao(Long idEleicao) throws BusinessException;

    List<EleicaoConsultaDTO> consultarEleicoes(EleicaoConsultaDTO filtro) throws BusinessException;

    List<Pessoa> consultaPessoas(String filtro) throws BusinessException;

    List<Partido> consultaPartidos(String filtro) throws BusinessException;

    void salvarCandidato(Candidato candidato) throws BusinessException;

    List<Candidato> buscaCandidatos(Long idTurnoCargoRegiao) throws BusinessException;

    List<Coligacao> buscarColigacoes(Long idEleicao);

    List<Partido> buscarPartidos(Long idColigacao);

    void salvarPartido(Partido partido) throws BusinessException;

    Long salvarColigacao(Coligacao coligacao) throws BusinessException;

    List<EleicaoConsultaDTO> consultaEleicoesUsuario() throws BusinessException;
}
