package br.com.svo.business.pessoa;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Identity;
import br.com.svo.entities.Perfil;
import br.com.svo.entities.Pessoa;
import br.com.svo.entities.dto.PessoaConsultaDTO;
import br.com.svo.util.RestUtil;
import br.com.svo.util.exception.RestException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PessoaBusiness implements Serializable {

    public static final long serialVersionUID = 1L;

    private static final Gson GSON = new Gson();

    @Inject
    private Identity identity;

    public Pessoa buscaPessoa(Long idPessoa) throws BusinessException {
        try {
            String response = new RestUtil("pessoa/" + idPessoa).get();
            return GSON.fromJson(response, Pessoa.class);
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        }
    }

    public List<Perfil> listarPerfis() throws BusinessException {
        try {
            String response = new RestUtil("login/perfis").get();
            return GSON.fromJson(response, new TypeToken<List<Perfil>>(){}.getType());
        } catch (RestException e) {
            return new ArrayList<>();
        }
    }

    public Long salvar(Pessoa pessoa) throws BusinessException {
        try {
            String response = new RestUtil("pessoa/salvar").withBody(pessoa)
                                                           .withHeader("Content-Type", "application/json")
                                                           .withHeader("Authorization", identity.getToken())
                                                           .post();

            return new Long(response);
        } catch (RestException e) {
            throw new BusinessException("Erros ao salvar a eleição:", e.getMessages());
        }
    }

    public List<PessoaConsultaDTO> buscarPessoas(PessoaConsultaDTO pessoaConsultaDTO) throws BusinessException {
        try {
            String response = new RestUtil("pessoa/consultar").withBody(pessoaConsultaDTO)
                                                              .withHeader("Content-Type", "application/json")
                                                              .get();
            return GSON.fromJson(response, new TypeToken<List<PessoaConsultaDTO>>(){}.getType());
        } catch (RestException e) {
            throw new BusinessException("Erro ao consultar pessoa:", e.getMessages());
        }
    }
}
