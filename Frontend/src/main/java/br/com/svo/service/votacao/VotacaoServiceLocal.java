package br.com.svo.service.votacao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import br.com.svo.entities.Voto;
import br.com.svo.entities.dto.CandidatoDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface VotacaoServiceLocal {

    void votar(String usuario, String senha, String pin, Long idEleicao, List<Voto> votos) throws BusinessException, NoResultException;

    List<Voto> consultaCargos(Long idEleicao) throws BusinessException, NoResultException;

    CandidatoDTO consultaCandidato(Long idTurnoCargoRegiao, Integer numero) throws BusinessException, NoResultException;

    void gerarPin();

}
