package br.com.svo.service.regiao;

import br.com.svo.entities.Cidade;
import br.com.svo.entities.Estado;

import javax.ejb.Local;
import java.util.List;

@Local
public interface RegiaoServiceLocal {

    List<Estado> consultarEstados(String filtro);

    List<Cidade> consultarCidades(Long idEstado, String filtro);
}
