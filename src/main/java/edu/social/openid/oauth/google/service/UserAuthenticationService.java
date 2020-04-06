package edu.social.openid.oauth.google.service;

import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.core.JsonProcessingException;
import edu.social.openid.oauth.exception.ApplicationException;
import edu.social.openid.oauth.google.client.OAuthGoogleClient;
import edu.social.openid.oauth.google.csrf.CsrfTokenUtil;
import edu.social.openid.oauth.google.jwt.JwtHandler;
import edu.social.openid.oauth.google.model.ClientProperties;
import edu.social.openid.oauth.google.model.CodeExchangeResponse;
import edu.social.openid.oauth.google.model.UserBasicInformation;
import edu.social.openid.oauth.google.util.OAuthEndpointsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class UserAuthenticationService {

    private static final String EMAIL_CLAIM = "email";
    private static final String GIVEN_NAME_CLAIM = "given_name";
    private static final String FAMILY_NAME_CLAIM = "family_name";
    private static final String PICTURE_CLAIM = "picture";
    private static final String LOCALE_CLAIM = "locale";

    private static final String ACCESS_TOKEN_HEADER = "access_token";

    @Autowired
    @Qualifier("googleClientProperties")
    private ClientProperties clientProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("OAuthGoogleClient")
    private OAuthGoogleClient client;

    @Autowired
    private JwtHandler jwtHandler;

    @Autowired
    private OAuthEndpointsBuilder oAuthEndpointsBuilder;

    public ResponseEntity<?> getOAuthGoogleConsentEndpointResponse(){
        String csrfToken = CsrfTokenUtil.generate();
        String uri = oAuthEndpointsBuilder.getUserSigningEndpoint(csrfToken);
        Map<String,String> map = Collections.singletonMap("uri",uri);
        return ResponseEntity
                .ok()
                .header(CsrfTokenUtil.getCsrfTokenHeaderName(), csrfToken)
                .contentType(APPLICATION_JSON)
                .body(map);
    }

    public ResponseEntity<?> authenticate(String code) {
        ResponseEntity<?> response = client.exchangeCode(code);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return getErrorMessage(response.getBody().toString());
        }
        String idToken = ((CodeExchangeResponse) response.getBody()).getId_token();
        if (isEmpty(idToken)) {
            return getErrorMessage("Invalid authentication response received from Google OAuth APi = ID_TOKEN missing!");
        }
        try {
            Map<String, Claim> claims = jwtHandler.extractClaims(idToken);
            String accessToken = "NOT YET IMPLEMENTED";
            if (isAlreadyAuthenticated(claims)) {
                //TODO : implement user verification on DB
                UserBasicInformation userBasicInformation = new UserBasicInformation();
                return getSuccessResponse(userBasicInformation, accessToken);
            } else {
                UserBasicInformation userBasicInformation = getUserBasicInformation(claims);
                return getSuccessResponse(userBasicInformation, accessToken);
            }
        } catch (JsonProcessingException e) {
            return getErrorMessage("Invalid ID_Token received from Google OAuth API : " + e.getMessage());
        }
    }

    private boolean isAlreadyAuthenticated(Map<String, Claim> claims) {
        Claim email = claims.get(EMAIL_CLAIM);
        if (isEmpty(email)) {
            throw new ApplicationException("Invalid ID_Token received from Google OAuth API : email claim missing!");
        }
        //TODO : check a database
        return false;
    }

    /**
     * Used to help the user to authenticate by using claims received from Google OAuth API
     *
     * @return
     */
    private UserBasicInformation getUserBasicInformation(Map<String, Claim> claims) {
        UserBasicInformation userBasicInformation = new UserBasicInformation();
        userBasicInformation.setEmail(claims.get(EMAIL_CLAIM).asString());
        userBasicInformation.setFamilyName(claims.get(FAMILY_NAME_CLAIM).asString());
        userBasicInformation.setGivenName(claims.get(GIVEN_NAME_CLAIM).asString());
        userBasicInformation.setUrlPicture(claims.get(PICTURE_CLAIM).asString());
        userBasicInformation.setLocale(claims.get(LOCALE_CLAIM).asString());
        return userBasicInformation;
    }

    private ResponseEntity<?> getErrorMessage(String message) {
        return ResponseEntity.badRequest().body(message);
    }

    private ResponseEntity<?> getSuccessResponse(UserBasicInformation userBasicInformation, String accessToken) {
        return ResponseEntity
                .ok()
                .header(ACCESS_TOKEN_HEADER, accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userBasicInformation);
    }

}
