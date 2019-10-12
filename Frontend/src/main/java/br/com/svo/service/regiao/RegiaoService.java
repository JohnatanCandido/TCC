package br.com.svo.service.regiao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import br.com.svo.business.regiao.RegiaoBusiness;
import br.com.svo.entities.Cidade;
import br.com.svo.entities.Estado;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Stateless
public class RegiaoService implements RegiaoServiceLocal, Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private RegiaoBusiness regiaoBusiness;

    @Override
    public List<Estado> consultarEstados(String filtro) throws BusinessException, NoResultException {
        return regiaoBusiness.consultarEstados(filtro);
    }

    @Override
    public List<Cidade> consultarCidades(Long idEstado, String filtro) throws BusinessException, NoResultException {
        return regiaoBusiness.consultarCidades(idEstado, filtro);
    }
}
