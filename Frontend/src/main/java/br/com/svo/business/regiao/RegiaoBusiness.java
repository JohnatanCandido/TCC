package br.com.svo.business.regiao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Cidade;
import br.com.svo.entities.Estado;
import br.com.svo.util.RestUtil;
import br.com.svo.util.exception.RestException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.List;

public class RegiaoBusiness implements Serializable {

    public static final long serialVersionUID = 1L;

    private static final Gson GSON = new Gson();

    public List<Estado> consultarEstados(String filtro) throws BusinessException {
        try {
            String response = new RestUtil("regiao/estado/" + filtro).get();
            return GSON.fromJson(response, new TypeToken<List<Estado>>(){}.getType());
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        }
    }

    public List<Cidade> consultarCidades(Long idEstado, String filtro) throws BusinessException {
        try {
            String response = new RestUtil("regiao/estado/" + idEstado + "/cidade/" + filtro).get();
            return GSON.fromJson(response, new TypeToken<List<Cidade>>(){}.getType());
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        }
    }
}
