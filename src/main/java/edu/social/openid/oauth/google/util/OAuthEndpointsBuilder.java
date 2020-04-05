package edu.social.openid.oauth.google.util;

import edu.social.openid.oauth.google.model.AuthFlowProperties;
import edu.social.openid.oauth.google.model.ClientProperties;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Create endpoint paths from the template received from Google Document Discovery endpoint.
 * Since the document may be changing a request to this page is mandatory
 */
@Component
public class OAuthEndpointsBuilder {
    private static final Integer NONCE_LENGTH = 10;

    private static final String USER_SIGNING_GOOGLE_ENDPOINT = "https://accounts.google.com/signin/oauth/oauthchooseaccount";

    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String CLIENT_SECRET_PARAM = "client_secret";
    private static final String REDIRECT_URI_PARAM = "redirect_uri";
    private static final String SCOPE_PARAM = "scope";
    private static final String RESPONSE_TYPE_PARAM = "response_type";
    private static final String RESPONSE_MODE_PARAM = "response_mode";
    private static final String STATE_PARAM = "state";
    private static final String NONCE_PARAM = "nonce";
    private static final String ACCESS_TYPE_PARAM = "access_type";
    private static final String PROMPT_PARAM = "prompt";
    private static final String GRANT_TYPE_PARAM = "grant_type";
    private static final String CODE_PARAM = "code";

    @Autowired
    private DocumentDiscoveryService documentDiscoveryService;

    @Autowired
    @Qualifier("googleClientProperties")
    private ClientProperties clientProperties;

    @Autowired
    @Qualifier("googleAuthFlowProperties")
    private AuthFlowProperties authFlowProperties;

    /**
     * Google endpoint which display the consent regarding used details which are asked by the application
     *
     * @return String uri
     */
    public String getUserSigningEndpoint() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(USER_SIGNING_GOOGLE_ENDPOINT)
                .queryParam(CLIENT_ID_PARAM, clientProperties.getClientId())
                .queryParam(REDIRECT_URI_PARAM, authFlowProperties.getCallbackURI())
                .queryParam(SCOPE_PARAM, "openid profile email")
                .queryParam(RESPONSE_TYPE_PARAM, "code")
                .queryParam(RESPONSE_MODE_PARAM, "query")
                /*TODO : CSRF implementation
                 .queryParam(STATE_PARAM, "123") */
                .queryParam(NONCE_PARAM, RandomStringUtils.random(NONCE_LENGTH))
                .queryParam(ACCESS_TYPE_PARAM, authFlowProperties.isRefreshToken() ? "offline" : "online")
                .queryParam(PROMPT_PARAM, "consent");

        return builder.toUriString();
    }

    public String getTokenEndpoint(String code) {
        String tokenEndpoint = documentDiscoveryService.getDocumentDiscovery().getToken_endpoint();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(tokenEndpoint)
                .queryParam(GRANT_TYPE_PARAM, "authorization_code")
                .queryParam(CODE_PARAM, code)
                .queryParam(CLIENT_ID_PARAM, clientProperties.getClientId())
                .queryParam(CLIENT_SECRET_PARAM, clientProperties.getClientSecret())
                .queryParam(REDIRECT_URI_PARAM, authFlowProperties.getCallbackURI());
        return builder.toUriString();
    }
}
