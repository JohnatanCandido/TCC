package br.com.svo.web.eleicao.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Candidato;
import br.com.svo.entities.Cidade;
import br.com.svo.entities.Eleicao;
import br.com.svo.entities.Estado;
import br.com.svo.entities.Partido;
import br.com.svo.entities.Pessoa;
import br.com.svo.entities.TurnoCargo;
import br.com.svo.entities.TurnoCargoRegiao;
import br.com.svo.entities.enums.TipoCargo;
import br.com.svo.service.eleicao.EleicaoServiceLocal;
import br.com.svo.util.Messages;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named("candidatoCadastroWebBean")
public class CandidatoCadastroWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    private Candidato candidato;

    private List<TurnoCargo> turnoCargos;
    private List<Estado> estados = new ArrayList<>();
    private List<Cidade> cidades = new ArrayList<>();
    private List<Pessoa> pessoas = new ArrayList<>();
    private List<Partido> partidos = new ArrayList<>();

    private List<Pessoa> pessoasVice = new ArrayList<>();
    private List<Partido> partidosVice = new ArrayList<>();

    private TurnoCargo turnoCargo;
    private Estado estado;
    private Cidade cidade;

    @Inject
    private EleicaoWebBean eleicaoWebBean;

    @Inject
    private EleicaoServiceLocal eleicaoService;

    @PostConstruct
    public void init() {
        Eleicao eleicao = eleicaoWebBean.getEleicao();
        this.turnoCargos = eleicao.getTurnos().get(0).getTurnoCargos();
        this.candidato = new Candidato(eleicao.getIdEleicao());
        this.candidato.setViceCandidato(new Candidato(eleicao.getIdEleicao()));
    }

    public void filtraEstados() {
        if (isRenderizaEstado()) {
            this.estados = turnoCargo.getTurnoCargoRegioes()
                                     .stream()
                                     .map(TurnoCargoRegiao::getEstado)
                                     .collect(Collectors.toList());
        }
    }

    public void filtraCidades() {
        if (isRenderizaCidade()) {
            cidades = turnoCargo.getTurnoCargoRegioes()
                                .stream()
                                .filter(tcr -> tcr.getEstado().equals(estado))
                                .map(TurnoCargoRegiao::getCidade)
                                .collect(Collectors.toList());
        }
    }

    public List<Pessoa> consultaPessoas(String filtro) {
        try {
            pessoas = eleicaoService.consultaPessoas(filtro);
        } catch (BusinessException e) {
            Messages.addErrorMessage(e);
            pessoas = new ArrayList<>();
        }
        return pessoas;
    }

    public List<Partido> consultaPartidos(String filtro) {
        try {
            partidos = eleicaoService.consultaPartidos(filtro);
        } catch (BusinessException e) {
            Messages.addErrorMessage(e);
            partidos = new ArrayList<>();
        }
        return partidos;
    }

    public List<Pessoa> consultaPessoasVice(String filtro) {
        try {
            pessoasVice = eleicaoService.consultaPessoas(filtro);
        } catch (BusinessException e) {
            Messages.addErrorMessage(e);
            pessoasVice = new ArrayList<>();
        }
        return pessoasVice;
    }

    public List<Partido> consultaPartidosVice(String filtro) {
        try {
            partidosVice = eleicaoService.consultaPartidos(filtro);
        } catch (BusinessException e) {
            Messages.addErrorMessage(e);
            partidosVice = new ArrayList<>();
        }
        return partidosVice;
    }

    public void salvar() {
        try {
            insertCargo();
            eleicaoService.salvarCandidato(candidato);
        } catch (BusinessException e) {
            Messages.addErrorMessage(e);
        }
    }

    private void insertCargo() {
        List<TurnoCargoRegiao> tcr = turnoCargo.getTurnoCargoRegioes();
        if (isRenderizaEstado()) {
            tcr = tcr.stream().filter(t -> t.getEstado().equals(estado)).collect(Collectors.toList());
            if (isRenderizaCidade()) {
                tcr = tcr.stream().filter(t -> t.getCidade().equals(cidade)).collect(Collectors.toList());
            }
        }
        candidato.setTurnoCargoRegiao(tcr.get(0));
    }

    public boolean isRenderizaEstado() {
        return turnoCargo != null && !turnoCargo.getCargo().getNome().equals("Presidente");
    }

    public boolean isRenderizaCidade() {
        return estado != null
                && turnoCargo.getTipoCargo().equals(TipoCargo.MUNICIPAL.getTipo());
    }

    public boolean isRenderizarCandidato() {
        if (turnoCargo.getCargo().getNome().equals("Presidente"))
            return turnoCargo != null;
        if (isRenderizaCidade())
            return cidade != null;
        return estado != null;
    }

    public boolean isRenderizarVice() {
        return isRenderizarCandidato() && turnoCargo.getCargo().getNome().equals("Presidente");
    }

    public boolean isRenderizarNumero() {
        return candidato.getPessoa() != null && candidato.getPartido() != null;
    }

//    GETTERS E SETTERS

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public List<TurnoCargo> getTurnoCargos() {
        return turnoCargos;
    }

    public void setTurnoCargos(List<TurnoCargo> turnoCargos) {
        this.turnoCargos = turnoCargos;
    }

    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public TurnoCargo getTurnoCargo() {
        return turnoCargo;
    }

    public void setTurnoCargo(TurnoCargo turnoCargo) {
        this.turnoCargo = turnoCargo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
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
