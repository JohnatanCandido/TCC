package br.com.svo.service.eleicao;

import br.com.svo.entities.Cargo;

import javax.ejb.Local;
import java.util.List;

@Local
public interface EleicaoServiceLocal {

    List<Cargo> consultaCargos();
}
