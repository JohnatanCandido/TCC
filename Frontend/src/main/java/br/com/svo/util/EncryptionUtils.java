package br.com.svo.util;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.util.exception.RestException;
import com.n1analytics.paillier.PaillierPublicKey;

import javax.inject.Inject;
import java.math.BigInteger;

public final class EncryptionUtils {

    private static PaillierPublicKey pub;

    static {
        buscaChave();
    }

    public static String encrypt(Long valor) throws RestException {
        return encrypt(new BigInteger(valor.toString())).toString();
    }

    public static BigInteger encrypt(BigInteger valor) throws RestException {
        verificaChave();
        return pub.raw_encrypt(valor);
    }

    private static void verificaChave() throws RestException {
        if (pub == null)
            buscaChave();
        if (pub == null)
            throw new RestException("Não foi possível buscar a chave pública.");
    }

    private static void buscaChave() {
        String key = null;
        try {
            key = new RestUtil("voto/chave-publica").get();
        } catch (RestException | BusinessException e) {
            e.printStackTrace();
        }
        if (key == null)
            throw new NullPointerException("Erro ao buscar chave pública no backend");
        pub = new PaillierPublicKey(new BigInteger(key));
    }
}
