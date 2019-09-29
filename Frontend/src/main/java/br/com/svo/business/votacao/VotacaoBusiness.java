package br.com.svo.business.votacao;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.entities.Identity;
import br.com.svo.entities.Voto;
import br.com.svo.entities.dto.CandidatoDTO;
import br.com.svo.entities.dto.VotoDTO;
import br.com.svo.entities.dto.VotosDTO;
import br.com.svo.util.EncryptionUtils;
import br.com.svo.util.RestUtil;
import br.com.svo.util.exception.RestException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.inject.Inject;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class VotacaoBusiness implements Serializable {

    public static final long serialVersionUID = 1L;

    @Inject
    private Identity identity;

    private static final Gson GSON = new Gson();

    public List<Voto> getCargosVotar(Long idEleicao) throws BusinessException {
        try {
            String response = new RestUtil("voto/cargos/eleicao/" + idEleicao).withHeader("Content-Type", "application/json")
                                                                              .withHeader("Authorization", identity.getToken())
                                                                              .get();

            return GSON.fromJson(response, new TypeToken<List<Voto>>(){}.getType());
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        }
    }

    public CandidatoDTO consultaCandidato(Long idTurnoCargoRegiao, Integer numero) throws BusinessException {
        try {
            String url = String.format("voto/turno-cargo-regiao/%s/candidato/%s", idTurnoCargoRegiao, numero);
            String response = new RestUtil(url).withHeader("Content-Type", "application/json")
                                               .withHeader("Authorization", identity.getToken())
                                               .get();

            return GSON.fromJson(response, CandidatoDTO.class);
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        }
    }

    public void votar(String usuario, String senha, Long idEleicao, List<Voto> votos) throws BusinessException {
        try {
            usuario = EncryptionUtils.encryptMD5(usuario);
            senha = EncryptionUtils.encryptMD5(senha);
            VotosDTO votosDTO  = new VotosDTO(usuario, senha, getVotosCriptografados(votos));
            String url = String.format("voto/eleicao/%s/votar", idEleicao);
            new RestUtil(url).withHeader("Content-Type", "application/json")
                             .withHeader("Authorization", identity.getToken())
                             .withBody(votosDTO)
                             .post();
        } catch (RestException e) {
            throw new BusinessException(e.getMessages().get(0));
        } catch (NoSuchAlgorithmException e) {
            throw new BusinessException("Houve um problema na criptografia dos dados");
        }
    }

    private List<VotoDTO> getVotosCriptografados(List<Voto> votos) throws RestException {
        List<VotoDTO> votosCriptografados = new ArrayList<>();
        for (Voto voto: votos) {
            VotoDTO votoCriptografado = new VotoDTO();
            votoCriptografado.setIdTurnoCargoRegiao(voto.getIdTurnoCargoRegiao());
            if (voto.getIdCandidato() != null)
                votoCriptografado.setIdCandidato(EncryptionUtils.encrypt(voto.getIdCandidato()));
            else
                votoCriptografado.setIdCandidato(EncryptionUtils.encrypt(-1L));
            if (voto.getIdPartido() != null)
                votoCriptografado.setIdPartido(EncryptionUtils.encrypt(voto.getIdPartido()));
            else
                votoCriptografado.setIdPartido(EncryptionUtils.encrypt(-1L));
            votosCriptografados.add(votoCriptografado);
        }
        return votosCriptografados;
    }
}
