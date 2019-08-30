package br.com.svo.util;

import br.com.svo.business.exception.BusinessException;
import br.com.svo.util.exception.RestException;
import com.n1analytics.paillier.PaillierPublicKey;

import javax.inject.Inject;
import java.math.BigInteger;

public final class EncryptionUtils {

    private static PaillierPublicKey pub;

    static {
        String key = null;
        try {
            key = new RestUtil("get_public_key").get();
        } catch (RestException | BusinessException e) {
            e.printStackTrace();
        }
        if (key == null)
            throw new NullPointerException("Erro ao buscar chave pública no backend");
        pub = new PaillierPublicKey(new BigInteger(key));
    }

    public static BigInteger encrypt(String valor) {
        return encrypt(new BigInteger(valor));
    }

    public static BigInteger encrypt(BigInteger valor) {
        return pub.raw_encrypt(valor);
    }

    public static String getKey() {
        if (pub == null)
            return "nulo";
        return pub.getModulus().toString();
    }
}
