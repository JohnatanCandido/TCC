package br.com.svo.service.votacao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
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
    public void votar(String usuario, String senha, String pin, Long idEleicao, List<Voto> votos) throws BusinessException, NoResultException {
        votacaoBusiness.votar(usuario, senha, pin, idEleicao, votos);
    }

    @Override
    public List<Voto> consultaCargos(Long idEleicao) throws BusinessException, NoResultException {
        return votacaoBusiness.getCargosVotar(idEleicao);
    }

    @Override
    public CandidatoDTO consultaCandidato(Long idTurnoCargoRegiao, Integer numero) throws BusinessException, NoResultException {
        return votacaoBusiness.consultaCandidato(idTurnoCargoRegiao, numero);
    }

    @Override
    public void gerarPin() {
        votacaoBusiness.gerarPin();
    }
}
