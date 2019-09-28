package br.com.svo.service.votacao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.votacao.VotacaoBusiness;
import br.com.svo.entities.Voto;
import br.com.svo.entities.dto.CandidatoDTO;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

public class VotacaoService implements VotacaoServiceLocal, Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private VotacaoBusiness votacaoBusiness;

    @Override
    public void votar(String usuario, String senha, Long idEleicao, List<Voto> votos) throws BusinessException {
        votacaoBusiness.votar(usuario, senha, idEleicao, votos);
    }

    @Override
    public List<Voto> consultaCargos(Long idEleicao) throws BusinessException {
        return votacaoBusiness.getCargosVotar(idEleicao);
    }

    @Override
    public CandidatoDTO consultaCandidato(Long idTurnoCargoRegiao, Integer numero) throws BusinessException {
        return votacaoBusiness.consultaCandidato(idTurnoCargoRegiao, numero);
    }
}
