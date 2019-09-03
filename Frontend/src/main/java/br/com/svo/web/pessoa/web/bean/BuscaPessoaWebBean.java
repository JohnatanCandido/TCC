package br.com.svo.web.pessoa.web.bean;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Cidade;
import br.com.svo.entities.Estado;
import br.com.svo.entities.dto.PessoaConsultaDTO;
import br.com.svo.service.pessoa.PessoaServiceLocal;
import br.com.svo.service.regiao.RegiaoServiceLocal;
import br.com.svo.util.Messages;
import org.omnifaces.cdi.ViewScoped;

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

    private PessoaConsultaDTO pessoaConsultaDTO = new PessoaConsultaDTO();
    private List<PessoaConsultaDTO> pessoas = new ArrayList<>();

    private List<Estado> estados = new ArrayList<>();
    private List<Cidade> cidades = new ArrayList<>();

    public List<Estado> consultaEstados(String filtro) {
        try {
            estados = regiaoService.consultarEstados(filtro);
        } catch (BusinessException e) {
            estados = new ArrayList<>();
            e.printStackTrace();
        }
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
        }
        return cidades;
    }

    public void buscar() {
        try {
            pessoas = pessoaService.buscarPessoas(pessoaConsultaDTO);
            Messages.addFoundMessage(pessoas.size());
        } catch (BusinessException e) {
            pessoas = new ArrayList<>();
            Messages.addErrorMessage(e);
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
