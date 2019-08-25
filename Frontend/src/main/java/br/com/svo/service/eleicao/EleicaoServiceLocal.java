package br.com.svo.service.eleicao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Cargo;
import br.com.svo.entities.Eleicao;
import br.com.svo.entities.Eleitor;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EleicaoServiceLocal {

    List<Cargo> consultaCargos();

    void salvar(Eleicao eleicao) throws BusinessException;

    Eleicao buscaEleicao(Long idEleicao) throws BusinessException;
}
