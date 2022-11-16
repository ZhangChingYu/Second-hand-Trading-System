package dev.silvia.wechattrade.handlers.common.cryto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import dev.silvia.wechattrade.handlers.common.auth.AuthConstant;

import java.util.Date;

//JWT签发和校验
public class Sign {
    private static final String TOKEN = "123456";
    private static final Algorithm algorithm = Algorithm.HMAC512(TOKEN);

    public static JWTVerifier getVerifier() {
        return JWT.require(algorithm).build();
    }

    public static String generateToken(Integer id,
                                       String userName,
                                       Integer authority,
                                       long duration) {
        return JWT.create()
                .withClaim(String.valueOf(AuthConstant.CLAIM_USER_ID), id)
                .withClaim(AuthConstant.CLAIM_USER_NAME, userName)
                .withClaim(String.valueOf(AuthConstant.CLAIM_ROLE), authority)
                .withExpiresAt(new Date(System.currentTimeMillis() + duration))
                .sign(algorithm);
    }
}
