package br.com.svo.business.eleicao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.*;
import br.com.svo.entities.dto.EleicaoConsultaDTO;
import br.com.svo.entities.dto.PessoaConsultaDTO;
import br.com.svo.util.RestUtil;
import br.com.svo.util.exception.RestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EleicaoBusiness implements Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private Identity identity;

    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();

    public List<Cargo> consultaCargos() {
        try {
            String response = new RestUtil("eleicao/cargos").get();
            return GSON.fromJson(response, ListaCargo.class).getCargos();
        } catch (RestException | BusinessException e) {
            return new ArrayList<>();
        }
    }

    public Long salvar(Eleicao eleicao) throws BusinessException {
        try {
            String response = new RestUtil("eleicao/salvar").withBody(eleicao)
                                                            .withHeader("Content-Type", "application/json")
                                                            .withHeader("Authorization", identity.getToken())
                                                            .post();

            return new Long(response);
        } catch (RestException e) {
            throw new BusinessException("Erros ao salvar a eleição:", e.getMessages());
        }
    }

    public Eleicao buscaEleicao(Long idEleicao) throws BusinessException {
        try {
            String response = new RestUtil("eleicao/" + idEleicao).get();
            return GSON.fromJson(response, Eleicao.class);
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        }
    }

    public List<EleicaoConsultaDTO> consultarEleicoes(EleicaoConsultaDTO filtro) throws BusinessException {
        try {
            String response = new RestUtil("eleicao/consultar").withBody(filtro)
                                                               .withHeader("Content-Type", "application/json")
                                                               .get();

            return GSON.fromJson(response, new TypeToken<List<EleicaoConsultaDTO>>(){}.getType());
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        }
    }

    public List<Pessoa> consultaPessoas(String filtro) throws BusinessException {
        try {
            PessoaConsultaDTO pessoa = new PessoaConsultaDTO();
            pessoa.setNome(filtro);
            String response = new RestUtil("pessoa/consultar").withBody(pessoa)
                                                              .withHeader("Content-Type", "application/json")
                                                              .get();

            return GSON.fromJson(response, new TypeToken<List<Pessoa>>(){}.getType());
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        }
    }

    public List<Partido> consultaPartidos(String filtro) throws BusinessException {
        try {
            String response = new RestUtil("partido/" +  filtro.replaceAll("%", "+")).withHeader("Content-Type", "application/json").get();

            return GSON.fromJson(response, new TypeToken<List<Partido>>(){}.getType());
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        }
    }

    public void salvarCandidato(Candidato candidato) throws BusinessException {
        try {
            new RestUtil("candidato/cadastrar").withBody(candidato)
                                               .withHeader("Content-Type", "application/json")
                                               .withHeader("Authorization", identity.getToken())
                                               .post();
        } catch (RestException e) {
            throw new BusinessException("Erros ao salvar o candidato:", e.getMessages());
        }
    }

    public List<Candidato> buscaCandidatos(Long idTurnoCargoRegiao) throws BusinessException {
        try {
            String response = new RestUtil("candidato/turnoCargoRegiao/" + idTurnoCargoRegiao).get();

            return GSON.fromJson(response, new TypeToken<List<Candidato>>(){}.getType());
        } catch (RestException e) {
            throw new BusinessException("Erros ao buscar candidatos:", e.getMessages());
        }
    }

    public List<Coligacao> buscarColigacoes(Long idEleicao) {
        try {
            String response = new RestUtil("partido/coligacao/eleicao/" +  idEleicao).get();

            return GSON.fromJson(response, new TypeToken<List<Coligacao>>(){}.getType());
        } catch (RestException | BusinessException e) {
            return new ArrayList<>();
        }
    }

    public List<Partido> buscarPartidos(Long idColigacao) {
        try {
            String response = new RestUtil("partido/coligacao/eleicao/" +  idColigacao).get();

            return GSON.fromJson(response, new TypeToken<List<Partido>>(){}.getType());
        } catch (RestException | BusinessException e) {
            return new ArrayList<>();
        }
    }

    public void salvarPartido(Partido partido) throws BusinessException {
        try {
            new RestUtil("partido/cadastrar").withBody(partido)
                                             .withHeader("Content-Type", "application/json")
                                             .withHeader("Authorization", identity.getToken())
                                             .post();
        } catch (RestException e) {
            throw new BusinessException("Erros ao cadastrar partido:", e.getMessages());
        }
    }

    public Long salvarColigacao(Coligacao coligacao) throws BusinessException {
        try {
            String response = new RestUtil("partido/coligacao").withBody(coligacao)
                                                               .withHeader("Content-Type", "application/json")
                                                               .withHeader("Authorization", identity.getToken())
                                                               .post();
            return new Long(response);
        } catch (RestException e) {
            throw new BusinessException("Erros ao cadastrar coligação:", e.getMessages());
        }
    }

    public List<EleicaoConsultaDTO> consultaEleicoesUsuario() throws BusinessException {
        try {
            String response = new RestUtil("eleicao/usuario").withHeader("Content-Type", "application/json")
                                                             .withHeader("Authorization", identity.getToken())
                                                             .get();
            return GSON.fromJson(response, new TypeToken<List<EleicaoConsultaDTO>>(){}.getType());
        } catch (RestException e) {
            throw new BusinessException("Erros ao buscar eleições:", e.getMessages());
        }
    }
}
