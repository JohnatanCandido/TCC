package br.com.svo;

import br.com.svo.util.EncryptionUtils;
import br.com.svo.util.RestUtil;
import com.n1analytics.paillier.PaillierPublicKey;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Teste {

    private static List<String> votos = new ArrayList<>();

    public static void main(String[] args) {
        String key = RestUtil.httpGet("get_key", null);

        votos.add("10");
        votos.add("100");
        votos.add("1");
        votos.add("1000");
        votos.add("1000");
        votos.add("10000");
        votos.add("100000");
        votos.add("1");
        votos.add("1000");

        if (key != null) {
            for (String voto: votos) {
                votar(voto);
            }

            System.out.println(RestUtil.httpGet("get_res", null));
        }
    }

    private static void votar(String voto) {
        BigInteger cypherText = EncryptionUtils.encrypt(voto);
        RestUtil.httpPost("http://localhost:5000/vote", cypherText.toString());
    }
}
