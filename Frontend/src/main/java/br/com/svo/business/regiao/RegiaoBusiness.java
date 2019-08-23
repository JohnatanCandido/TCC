package br.com.svo.business.regiao;

import br.com.svo.entities.Cidade;
import br.com.svo.entities.Estado;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class RegiaoBusiness implements Serializable {

    public static final long serialVersionUID = 1L;

    private List<Estado> estadosPlaceHolder = Collections.singletonList(new Estado(1L, "Santa Catarina"));
    private List<Cidade> cidadesPlaceHolder = Collections.singletonList(new Cidade(1L, estadosPlaceHolder.get(0), "Imbituba"));

    public List<Estado> consultarEstados(String filtro) {
        return estadosPlaceHolder;
    }

    public List<Cidade> consultarCidades(Long idEstado, String filtro) {
        return cidadesPlaceHolder;
    }
}
