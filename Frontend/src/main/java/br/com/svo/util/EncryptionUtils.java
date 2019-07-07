package br.com.svo.util;

import com.n1analytics.paillier.PaillierPublicKey;

import java.math.BigInteger;

public final class EncryptionUtils {

    private static PaillierPublicKey pub;

    static {
        String key = RestUtil.httpGet("get_public_key", null);
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
