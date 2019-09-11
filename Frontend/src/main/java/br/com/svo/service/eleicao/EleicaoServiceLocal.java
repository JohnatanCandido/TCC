package br.com.svo.service.eleicao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Candidato;
import br.com.svo.entities.Cargo;
import br.com.svo.entities.Eleicao;
import br.com.svo.entities.Partido;
import br.com.svo.entities.Pessoa;
import br.com.svo.entities.dto.EleicaoConsultaDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EleicaoServiceLocal {

    List<Cargo> consultaCargos();

    void salvar(Eleicao eleicao) throws BusinessException;

    Eleicao buscaEleicao(Long idEleicao) throws BusinessException;

    List<EleicaoConsultaDTO> consultarEleicoes(EleicaoConsultaDTO filtro) throws BusinessException;

    List<Pessoa> consultaPessoas(String filtro) throws BusinessException;

    List<Partido> consultaPartidos(String filtro) throws BusinessException;

    void salvarCandidato(Candidato candidato) throws BusinessException;
}
