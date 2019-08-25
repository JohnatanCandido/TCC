package br.com.svo.business.eleicao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Cargo;
import br.com.svo.entities.Eleicao;
import br.com.svo.entities.ListaCargo;
import br.com.svo.util.RestUtil;
import br.com.svo.util.exception.RestException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EleicaoBusiness implements Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private RestUtil restUtil;

    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();

    public List<Cargo> consultaCargos() {
        try {
            String response = restUtil.httpGet("eleicao/cargos", null);
            if (response == null) // Retorno quando não conectar com o back
                return new ArrayList<>();
            return GSON.fromJson(response, ListaCargo.class).getCargos();
        } catch (RestException e) {
            return new ArrayList<>();
        }
    }

    public void salvar(Eleicao eleicao) throws BusinessException {
        try {
            restUtil.httpPost("eleicao/salvar", eleicao);
        } catch (RestException e) {
            throw new BusinessException("Erros ao salvar a eleição:", e.getMessages());
        }
    }

    public Eleicao buscaEleicao(Long idEleicao) throws BusinessException {
        try {
            String response = restUtil.httpGet("eleicao/" + idEleicao, null);
            return GSON.fromJson(response, Eleicao.class);
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        }
    }
}
