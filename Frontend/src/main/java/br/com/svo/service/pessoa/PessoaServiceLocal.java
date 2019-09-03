package br.com.svo.service.pessoa;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Perfil;
import br.com.svo.entities.Pessoa;
import br.com.svo.entities.dto.PessoaConsultaDTO;

import javax.ejb.Local;
import java.util.List;

@Local
public interface PessoaServiceLocal {

    Pessoa buscaPessoa(Long idPessoa) throws BusinessException;

    List<Perfil> listarPerfis() throws BusinessException;

    void salvar(Pessoa pessoa) throws BusinessException;

    List<PessoaConsultaDTO> buscarPessoas(PessoaConsultaDTO pessoaConsultaDTO) throws BusinessException;
}