package br.com.svo.web.eleicao.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Candidato;
import br.com.svo.entities.Eleicao;
import br.com.svo.entities.TurnoCargoRegiao;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("apuracaoEleicaoWebBean")
public class ApuracaoEleicaoWebBean extends FiltroCandidatoWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private List<Candidato> candidatos = new ArrayList<>();

    @PostConstruct
    public void init() {
        Eleicao eleicao = eleicaoWebBean.getEleicao();
        this.turnoCargos = eleicao.getTurnos().get(0).getTurnoCargos();
    }

    @Override
    public void filtraEstados() {
        super.filtraEstados();
        candidatos.clear();
        if (turnoCargo != null && !isRenderizaEstado())
            buscaCandidatos();
    }

    @Override
    public void filtraCidades() {
        super.filtraCidades();
        candidatos.clear();
        if (turnoCargo != null && !isRenderizaCidade())
            buscaCandidatos();
    }

    public void buscaCandidatos() {
        TurnoCargoRegiao tcr = null;
        if (isRenderizaCidade() && cidade != null)
            tcr = turnoCargo.turnoCargoRegiaoByCidade(estado, cidade);
        else if (isRenderizaEstado() && estado != null)
            tcr = turnoCargo.turnoCargoRegiaoByEstado(estado);
        else if (!isRenderizaCidade() && !isRenderizaEstado())
            tcr = turnoCargo.getTurnoCargoRegioes().get(0);
        if (tcr != null) {
            try {
                candidatos = eleicaoService.buscaCandidatos(tcr.getIdTurnoCargoRegiao());
            } catch (BusinessException ignored) {}
        }
    }

//    GETTERS E SETTERS

    public List<Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(List<Candidato> candidatos) {
        this.candidatos = candidatos;
    }
}
