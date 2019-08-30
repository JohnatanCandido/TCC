package br.com.svo.business.eleicao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Cargo;
import br.com.svo.entities.Eleicao;
import br.com.svo.entities.Identity;
import br.com.svo.entities.ListaCargo;
import br.com.svo.entities.dto.EleicaoConsultaDTO;
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

    public void salvar(Eleicao eleicao) throws BusinessException {
        try {
            new RestUtil("eleicao/salvar").withBody(eleicao)
                                          .withHeader("Content-Type", "application/json")
                                          .withHeader("Authorization", identity.getToken())
                                          .post();
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
}
