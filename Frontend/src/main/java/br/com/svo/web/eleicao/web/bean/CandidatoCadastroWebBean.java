package br.com.svo.web.eleicao.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.*;
import br.com.svo.util.Messages;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("candidatoCadastroWebBean")
public class CandidatoCadastroWebBean extends FiltroCandidatoWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private Candidato candidato;

    private List<Pessoa> pessoas = new ArrayList<>();
    private List<Partido> partidos = new ArrayList<>();

    private List<Pessoa> pessoasVice = new ArrayList<>();
    private List<Partido> partidosVice = new ArrayList<>();

    @PostConstruct
    public void init() {
        Eleicao eleicao = eleicaoWebBean.getEleicao();
        this.turnoCargos = eleicao.getTurnos().get(0).getTurnoCargos();
        this.candidato = new Candidato(eleicao.getIdEleicao());
    }

    @Override
    public void filtraEstados() {
        super.filtraEstados();
        limpaDadosCandidato();
    }

    @Override
    public void filtraCidades() {
        super.filtraCidades();
        limpaDadosCandidato();
    }

    public List<Pessoa> consultaPessoas(String filtro) {
        try {
            pessoas = eleicaoService.consultaPessoas(filtro);
        } catch (BusinessException e) {
            pessoas = new ArrayList<>();
        }
        return pessoas;
    }

    public List<Partido> consultaPartidos(String filtro) {
        try {
            partidos = eleicaoService.consultaPartidos(filtro);
        } catch (BusinessException e) {
            partidos = new ArrayList<>();
        }
        return partidos;
    }

    public List<Pessoa> consultaPessoasVice(String filtro) {
        try {
            pessoasVice = eleicaoService.consultaPessoas(filtro);
        } catch (BusinessException e) {
            pessoasVice = new ArrayList<>();
        }
        return pessoasVice;
    }

    public List<Partido> consultaPartidosVice(String filtro) {
        try {
            partidosVice = eleicaoService.consultaPartidos(filtro);
        } catch (BusinessException e) {
            partidosVice = new ArrayList<>();
        }
        return partidosVice;
    }

    public void salvar() {
        try {
            insertCargo();
            eleicaoService.salvarCandidato(candidato);
            Messages.addMessage("Salvo com sucesso");
            novoCandidato();
        } catch (BusinessException e) {
            Messages.addErrorMessage(e);
        }
    }

    public void novoCandidato() {
        Eleicao eleicao = eleicaoWebBean.getEleicao();
        candidato = new Candidato(eleicao.getIdEleicao());
        estado = null;
        cidade = null;
        turnoCargo = turnoCargos.size() == 1 ? turnoCargos.get(0) : null;
        limpaDadosCandidato();
    }

    private void insertCargo() {
        TurnoCargoRegiao tcr;
        if (isRenderizaCidade())
            tcr = turnoCargo.turnoCargoRegiaoByCidade(estado, cidade);
        else if (isRenderizaEstado())
            tcr = turnoCargo.turnoCargoRegiaoByEstado(estado);
        else
            tcr = turnoCargo.getTurnoCargoRegioes().get(0);
        candidato.setTurnoCargoRegiao(tcr);
    }

    private void limpaDadosCandidato() {
        candidato.setPessoa(null);
        candidato.setPartido(null);

        if (turnoCargo.getCargo().isPermiteSegundoTurno()) {
            candidato.setViceCandidato(new Candidato(candidato.getIdEleicao()));
        } else {
            candidato.setViceCandidato(null);
        }
    }

    public boolean isRenderizarCandidato() {
        if (turnoCargo != null) {
            if (turnoCargo.getCargo().getNome().equals("Presidente"))
                return turnoCargo != null;
            if (isRenderizaCidade())
                return cidade != null;
            return estado != null;
        }
        return false;
    }

    public boolean isRenderizarVice() {
        return isRenderizarCandidato() && turnoCargo.getCargo().isPermiteSegundoTurno();
    }

    public boolean isRenderizarNumero() {
        return candidato.getPessoa() != null && candidato.getPartido() != null;
    }

    public boolean isDesabilitarNumero() {
        return turnoCargo.getCargo().isPermiteSegundoTurno();
    }

    public void verificaSetarNumeroCandidato() {
        if (isDesabilitarNumero()) {
            candidato.setNumero(candidato.getPartido().getNumeroPartido());
            if (candidato.getViceCandidato() != null && candidato.getViceCandidato().getPartido() != null)
                candidato.getViceCandidato().setNumero(candidato.getViceCandidato().getPartido().getNumeroPartido());
        }
    }

//    GETTERS E SETTERS

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public List<Pessoa> getPessoasVice() {
        return pessoasVice;
    }

    public void setPessoasVice(List<Pessoa> pessoasVice) {
        this.pessoasVice = pessoasVice;
    }

    public List<Partido> getPartidosVice() {
        return partidosVice;
    }

    public void setPartidosVice(List<Partido> partidosVice) {
        this.partidosVice = partidosVice;
    }
}
