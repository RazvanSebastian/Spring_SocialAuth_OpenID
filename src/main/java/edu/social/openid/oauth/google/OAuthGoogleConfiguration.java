package edu.social.openid.oauth.google;

import edu.social.openid.oauth.google.model.AuthFlowProperties;
import edu.social.openid.oauth.google.model.ClientProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class OAuthGoogleConfiguration {

    @Value(value = "${credentials.google.client-id}")
    private String clientId;
    @Value(value = "${credentials.google.client-secret}")
    private String clientSecret;
    @Value(value = "${authentication-flow.google.callback-url}")
    private String callbackUri;
    @Value(value = "${authentication-flow.google.refresh-token}")
    private Boolean withRefreshToken;


    @Bean(name = "googleClientProperties")
    public ClientProperties ClientProperties() {
        ClientProperties clientProperties = new ClientProperties();
        clientProperties.setClientId(clientId);
        clientProperties.setClientSecret(clientSecret);
        return clientProperties;
    }

    @Bean(name = "googleAuthFlowProperties")
    public AuthFlowProperties authFlowProperties() {
        AuthFlowProperties authFlowProperties = new AuthFlowProperties();
        authFlowProperties.setCallbackURI(callbackUri);
        authFlowProperties.withRefreshToken(withRefreshToken);
        return authFlowProperties;
    }
}
