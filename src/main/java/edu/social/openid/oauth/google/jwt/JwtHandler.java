package edu.social.openid.oauth.google.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JwtHandler {

    private ObjectMapper objectMapper;

    public JwtHandler(){
        this.objectMapper = new ObjectMapper();
    }

    public Map<String, Claim> extractClaims(String jwt) throws JsonProcessingException {
        DecodedJWT decodedJWT = JWT.decode(jwt);
        return decodedJWT.getClaims();
    }
}
