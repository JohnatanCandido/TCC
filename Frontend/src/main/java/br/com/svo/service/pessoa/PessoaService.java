package br.com.svo.service.pessoa;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import br.com.svo.business.pessoa.PessoaBusiness;
import br.com.svo.entities.Perfil;
import br.com.svo.entities.Pessoa;
import br.com.svo.entities.dto.PessoaConsultaDTO;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Stateless
public class PessoaService implements PessoaServiceLocal, Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private PessoaBusiness pessoaBusiness;

    public Pessoa buscaPessoa(Long idPessoa) throws BusinessException, NoResultException {
        return pessoaBusiness.buscaPessoa(idPessoa);
    }

    public List<Perfil> listarPerfis() throws BusinessException {
        return pessoaBusiness.listarPerfis();
    }

    @Override
    public Long salvar(Pessoa pessoa) throws BusinessException {
        return pessoaBusiness.salvar(pessoa);
    }

    @Override
    public List<PessoaConsultaDTO> buscarPessoas(PessoaConsultaDTO pessoaConsultaDTO) throws BusinessException, NoResultException {
        return pessoaBusiness.buscarPessoas(pessoaConsultaDTO);
    }
}
