package edu.social.openid.oauth.google.service;

import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.core.JsonProcessingException;
import edu.social.openid.oauth.exception.ApplicationException;
import edu.social.openid.oauth.exception.UserNotFoundException;
import edu.social.openid.oauth.google.client.OAuthGoogleClient;
import edu.social.openid.oauth.google.csrf.CsrfTokenUtil;
import edu.social.openid.oauth.google.model.ClientProperties;
import edu.social.openid.oauth.google.model.CodeExchangeResponse;
import edu.social.openid.oauth.google.model.exception.UnregisteredGoogleUserNotFound;
import edu.social.openid.oauth.google.util.OAuthEndpointsBuilder;
import edu.social.openid.oauth.local.model.UserApplication;
import edu.social.openid.oauth.local.model.UserPayload;
import edu.social.openid.oauth.security.JwtHandler;
import edu.social.openid.oauth.security.service.IUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import static edu.social.openid.oauth.security.TokenClaims.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.util.StringUtils.isEmpty;

@Service
public class UserAuthenticationService {

    private static final String ACCESS_TOKEN_HEADER = "access_token";

    @Autowired
    @Qualifier("googleClientProperties")
    private ClientProperties clientProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuthGoogleClient client;

    @Autowired
    private JwtHandler jwtHandler;

    @Autowired
    private OAuthEndpointsBuilder oAuthEndpointsBuilder;

    @Autowired
    private IUserDetailsService userDetailsService;

    public ResponseEntity<?> getOAuthGoogleConsentEndpointResponse() {
        String csrfToken = CsrfTokenUtil.generate();
        String uri = oAuthEndpointsBuilder.getUserSigningEndpoint(csrfToken);
        Map<String, String> map = Collections.singletonMap("uri", uri);
        return ResponseEntity
                .ok()
                .header(CsrfTokenUtil.getCsrfTokenHeaderName(), csrfToken)
                .contentType(APPLICATION_JSON)
                .body(map);
    }

    public ResponseEntity<?> authenticate(String code) {
        ResponseEntity<?> response = client.exchangeCode(code);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new ApplicationException("The response message from OAuth Google client is " + response.getBody());
        }
        String idToken = ((CodeExchangeResponse) response.getBody()).getId_token();
        if (isEmpty(idToken)) {
            throw new ApplicationException("Invalid authentication response received from Google OAuth APi = ID_TOKEN missing!");
        }
        try {
            UserPayload userPayload = parseToUserPayload(idToken);
            try {
                UserApplication user = (UserApplication) userDetailsService.findUser(userPayload);
                String accessToken = jwtHandler.generateAccessToken(user);
                return getSuccessResponse(accessToken);
            } catch (UserNotFoundException e) {
                return getErrorMessage(e.getMessage(), userPayload);
            }
        } catch (JsonProcessingException e) {
            throw new ApplicationException("Invalid ID_Token received from Google OAuth API : " + e.getMessage());
        }
    }

    private UserPayload parseToUserPayload(String idToken) throws JsonProcessingException {
        Map<String, Claim> claims = jwtHandler.extractClaims(idToken);
        UserPayload userPayload = new UserPayload()
                .setEmail(claims.get(EMAIL_CLAIM.getValue()).asString())
                .setFamilyName(claims.get(FAMILY_NAME_CLAIM.getValue()).asString())
                .setGivenName(claims.get(GIVEN_NAME_CLAIM.getValue()).asString())
                .setUrlPicture(claims.get(PICTURE_CLAIM.getValue()).asString())
                .setLocale(claims.get(LOCALE_CLAIM.getValue()).asString());
        return userPayload;
    }

    private ResponseEntity<?> getErrorMessage(String message, UserPayload userPayload) {
        UnregisteredGoogleUserNotFound response = new UnregisteredGoogleUserNotFound(message, userPayload);
        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON).body(response);
    }

    private ResponseEntity<?> getSuccessResponse(String accessToken) {
        return ResponseEntity
                .ok()
                .header(ACCESS_TOKEN_HEADER, accessToken)
                .body("");
    }

}
