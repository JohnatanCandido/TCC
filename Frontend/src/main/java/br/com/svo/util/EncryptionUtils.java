package br.com.svo.util;

import br.com.svo.business.exception.NoResultException;
import br.com.svo.util.exception.RestException;
import com.n1analytics.paillier.PaillierPublicKey;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String encryptMD5(String texto) throws NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(texto.getBytes());
        return new BigInteger(1, m.digest()).toString(16).trim();
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
        } catch (RestException | NoResultException e) {
            e.printStackTrace();
        }
        if (key == null)
            throw new NullPointerException("Erro ao buscar chave pública no backend");
        pub = new PaillierPublicKey(new BigInteger(key));
    }
}
