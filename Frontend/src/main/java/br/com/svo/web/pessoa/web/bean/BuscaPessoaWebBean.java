package br.com.svo.web.pessoa.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.business.exception.NoResultException;
import br.com.svo.entities.Cidade;
import br.com.svo.entities.Estado;
import br.com.svo.entities.Identity;
import br.com.svo.entities.dto.PessoaConsultaDTO;
import br.com.svo.service.pessoa.PessoaServiceLocal;
import br.com.svo.service.regiao.RegiaoServiceLocal;
import br.com.svo.util.Perfis;
import br.com.svo.util.RedirectUtils;
import br.com.svo.util.SvoMessages;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("buscaPessoaWebBean")
public class BuscaPessoaWebBean implements Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private PessoaServiceLocal pessoaService;

    @Inject
    private RegiaoServiceLocal regiaoService;

    @Inject
    private Identity identity;

    private PessoaConsultaDTO pessoaConsultaDTO = new PessoaConsultaDTO();
    private List<PessoaConsultaDTO> pessoas = new ArrayList<>();

    private List<Estado> estados = new ArrayList<>();
    private List<Cidade> cidades = new ArrayList<>();

    @PostConstruct
    public void verificaPermissaoAcessoBuscaPessoa() {
        if (!identity.hasPerfil(Perfis.ADMINISTRADOR))
            RedirectUtils.redirect("index.html");
    }

    public List<Estado> consultaEstados(String filtro) {
        try {
            estados = regiaoService.consultarEstados(filtro);
        } catch (BusinessException e) {
            estados = new ArrayList<>();
            e.printStackTrace();
        } catch (NoResultException ignored) {}
        return estados;
    }

    public boolean isDesabilitaCidade() {
        return this.pessoaConsultaDTO.getIdEstado() == null;
    }

    public List<Cidade> consultaCidades(String filtro) {
        try {
            cidades = regiaoService.consultarCidades(pessoaConsultaDTO.getIdEstado(), filtro);
        } catch (BusinessException e) {
            estados = new ArrayList<>();
            e.printStackTrace();
        } catch (NoResultException ignored) {}
        return cidades;
    }

    public void buscar() {
        try {
            pessoas = pessoaService.buscarPessoas(pessoaConsultaDTO);
            SvoMessages.addFoundMessage(pessoas.size());
        } catch (BusinessException e) {
            pessoas = new ArrayList<>();
            SvoMessages.addErrorMessage(e);
        } catch (NoResultException e) {
            pessoas = new ArrayList<>();
            SvoMessages.addErrorMessage(e);
        }
    }

//    GETTERS E SETTERS

    public PessoaConsultaDTO getPessoaConsultaDTO() {
        return pessoaConsultaDTO;
    }

    public void setPessoaConsultaDTO(PessoaConsultaDTO pessoaConsultaDTO) {
        this.pessoaConsultaDTO = pessoaConsultaDTO;
    }

    public List<PessoaConsultaDTO> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<PessoaConsultaDTO> pessoas) {
        this.pessoas = pessoas;
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
}
