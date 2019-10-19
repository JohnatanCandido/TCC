package br.com.svo.web.pessoa.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import br.com.svo.entities.*;
import br.com.svo.service.pessoa.PessoaServiceLocal;
import br.com.svo.service.regiao.RegiaoServiceLocal;
import br.com.svo.util.Perfis;
import br.com.svo.util.SvoMessages;
import br.com.svo.util.RedirectUtils;
import org.omnifaces.cdi.Param;
import org.omnifaces.cdi.ViewScoped;
import org.primefaces.model.DualListModel;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("pessoaWebBean")
public class PessoaWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    @Param(name = "idPessoa")
    private Long idPessoa;

    @Inject
    private PessoaServiceLocal pessoaService;

    @Inject
    private RegiaoServiceLocal regiaoService;

    @Inject
    private Identity identity;

    private Pessoa pessoa;

    private List<Estado> estados = new ArrayList<>();
    private List<Cidade> cidades = new ArrayList<>();
    private Estado estado;

    private DualListModel<Perfil> perfis;
    private List<Perfil> listaPerfil = new ArrayList<>();

    @PostConstruct
    public void init() {
        if (!identity.hasPerfil(Perfis.ADMINISTRADOR)) {
            RedirectUtils.redirect("index.html");
            return;
        }

        try {
            if (idPessoa == null)
                pessoa = new Pessoa();
            else {
                pessoa = pessoaService.buscaPessoa(idPessoa);
                estado = pessoa.getEleitor().getCidade().getEstado();
            }
            initPerfis();
        } catch (BusinessException e) {
            e.printStackTrace();
        } catch (NoResultException e) {
            SvoMessages.addFlashGlobalError("Pessoa não encontrada");
            RedirectUtils.redirectToHome();
        }
    }

    private void initPerfis() throws BusinessException {
        List<Perfil> source = pessoaService.listarPerfis();
        listaPerfil = new ArrayList<>(source);
        List<Perfil> target = pessoa.getPerfis();
        source.removeAll(target);
        perfis = new DualListModel<>(source, target);
    }

    public List<Estado> consultaEstados(String filtro) {
        try {
            estados = regiaoService.consultarEstados(filtro);
        } catch (BusinessException e) {
            estados = new ArrayList<>();
            SvoMessages.addErrorMessage(e);
        } catch (NoResultException ignored) {}
        return estados;
    }

    public List<Cidade> consultarCidades(String filtro) {
        try {
            cidades = regiaoService.consultarCidades(estado.getIdEstado(), filtro);
        } catch (BusinessException e) {
            cidades = new ArrayList<>();
            SvoMessages.addErrorMessage(e);
        } catch (NoResultException ignored) {}
        return cidades;
    }

    public void salvar() {
        try {
            pessoa.setPerfis(perfis.getTarget());
            Long idPessoa = pessoaService.salvar(pessoa);
            if (pessoa.getIdPessoa() == null) {
                SvoMessages.addFlash("Salvo com sucesso");
                RedirectUtils.redirect("pessoa/cadastro-pessoa.html?idPessoa=" + idPessoa);
            } else {
                SvoMessages.addMessage("Salvo com sucesso.");
                initPerfis();
            }
        } catch (BusinessException e) {
            SvoMessages.addErrorMessage(e);
        }
    }

    public boolean isDesabilitaCidade() {
        return estado == null;
    }

//    GETTERS E SETTERS

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public DualListModel<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(DualListModel<Perfil> perfis) {
        this.perfis = perfis;
    }

    public List<Perfil> getListaPerfil() {
        return listaPerfil;
    }

    public void setListaPerfil(List<Perfil> listaPerfil) {
        this.listaPerfil = listaPerfil;
    }
}
