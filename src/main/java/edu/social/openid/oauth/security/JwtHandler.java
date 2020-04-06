package edu.social.openid.oauth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import edu.social.openid.oauth.local.model.UserApplication;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import static edu.social.openid.oauth.security.TokenClaims.*;

public class JwtHandler {

    private static final String ISSUER = "RazvanApp";
    private static final String AUDIENCE = "Client-ID-App";

    private RSAKeyPairs rsaKeyPairs;

    public JwtHandler(RSAKeyPairs rsaKeyPairs) {
        this.rsaKeyPairs = rsaKeyPairs;
    }

    public Map<String, Claim> extractClaims(String jwt) throws JsonProcessingException {
        DecodedJWT decodedJWT = JWT.decode(jwt);
        return decodedJWT.getClaims();
    }

    public String generateAccessToken(UserApplication user) {
        RSAPublicKey publicKey = (RSAPublicKey) rsaKeyPairs.getPublicKey();
        RSAPrivateKey privateKey = (RSAPrivateKey) rsaKeyPairs.getPrivateKey();
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            return JWT.create()
                    .withAudience(AUDIENCE)
                    .withExpiresAt(getExpirationDate())
                    .withIssuedAt(new Date())
                    .withIssuer(ISSUER)
                    .withSubject(user.getId().toString())
                    .withClaim(EMAIL_CLAIM.getValue(), user.getEmail())
                    .withClaim(PHONE_CLAIM.getValue(), user.getPhone())
                    .withClaim(PICTURE_CLAIM.getValue(), user.getUrlPicture())
                    .withClaim(GIVEN_NAME_CLAIM.getValue(), user.getGivenName())
                    .withClaim(FAMILY_NAME_CLAIM.getValue(), user.getFamilyName())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            exception.getStackTrace();
        }
        return null;
    }

    private Date getExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, 1);
        return calendar.getTime();
    }
}
