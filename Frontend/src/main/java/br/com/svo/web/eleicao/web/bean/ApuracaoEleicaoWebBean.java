package br.com.svo.web.eleicao.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import br.com.svo.entities.Candidato;
import br.com.svo.entities.Eleicao;
import br.com.svo.entities.TurnoCargo;
import br.com.svo.entities.TurnoCargoRegiao;
import br.com.svo.entities.dto.ApuracaoCandidatoDTO;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@ViewScoped
@Named("apuracaoEleicaoWebBean")
public class ApuracaoEleicaoWebBean extends FiltroCandidatoWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private List<ApuracaoCandidatoDTO> candidatos = new ArrayList<>();
    private Integer turno;
    private List<TurnoCargo> turnoCargosSegundoTurno;

    @PostConstruct
    public void init() {
        Eleicao eleicao = eleicaoWebBean.getEleicao();
        this.turnoCargos = eleicao.getTurnos().get(0).getTurnoCargos();
        if (eleicao.getTurnos().size() > 1)
            this.turnoCargosSegundoTurno = eleicao.getTurnos().get(1).getTurnoCargos();
    }

    @Override
    public void filtraEstados() {
        super.filtraEstados();
        candidatos.clear();
        if (turnoCargo != null && !isRenderizaEstado())
            buscaCandidatos();
        else
            turno = null;
    }

    @Override
    public void filtraCidades() {
        super.filtraCidades();
        candidatos.clear();
        if (turnoCargo != null && !isRenderizaCidade())
            buscaCandidatos();
        else
            turno = null;
    }

    public void buscaCandidatos() {
        candidatos.clear();
        TurnoCargo tc = turno == null || turno == 1 ? turnoCargo : turnoCargosSegundoTurno.stream().filter(tcst -> tcst.getCargo().equals(turnoCargo.getCargo())).findFirst().orElse(null);
        TurnoCargoRegiao tcr = null;
        if (tc != null) {
            if (isRenderizaCidade() && cidade != null)
                tcr = tc.turnoCargoRegiaoByCidade(estado, cidade);
            else if (isRenderizaEstado() && estado != null)
                tcr = tc.turnoCargoRegiaoByEstado(estado);
            else if (!isRenderizaCidade() && !isRenderizaEstado())
                tcr = tc.getTurnoCargoRegioes().get(0);
            if (tcr != null && (turno != null || !tcr.isPossuiSegundoTurno())) {
                try {
                    candidatos = eleicaoService.buscaCandidatos(tcr.getIdTurnoCargoRegiao());
                } catch (BusinessException | NoResultException ignored) {}
            }
        }
    }

    public boolean isRenderizaTurno() {
        if (turnoCargo != null) {
            TurnoCargoRegiao tcr = null;
            if (isRenderizaCidade() && cidade != null)
                tcr = turnoCargo.turnoCargoRegiaoByCidade(estado, cidade);
            else if (isRenderizaEstado() && !isRenderizaCidade() && estado != null)
                tcr = turnoCargo.turnoCargoRegiaoByEstado(estado);
            else if (!isRenderizaCidade() && !isRenderizaEstado())
                tcr = turnoCargo.getTurnoCargoRegioes().get(0);
            return tcr != null && tcr.isPossuiSegundoTurno();
        }
        return false;
    }

    public boolean isRenderizaPanelHashEleitor() {
        return getHashEleitorTurno() != null;
    }

    public String getHashEleitorTurno() {
        return turno != null ? eleicaoWebBean.getEleicao().getTurnos().get(turno-1).getHashEleitor() : null;
    }

//    GETTERS E SETTERS

    public List<ApuracaoCandidatoDTO> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(List<ApuracaoCandidatoDTO> candidatos) {
        this.candidatos = candidatos;
    }

    public Integer getTurno() {
        return turno;
    }

    public void setTurno(Integer turno) {
        this.turno = turno;
    }

    public List<SelectItem> getTurnos() {
        SelectItem turno1 = new SelectItem(1, "1º Turno");
        if (turnoCargosSegundoTurno == null)
            return Collections.singletonList(turno1);
        SelectItem turno2 = new SelectItem(2, "2º Turno");
        return Arrays.asList(turno1, turno2);
    }
}
