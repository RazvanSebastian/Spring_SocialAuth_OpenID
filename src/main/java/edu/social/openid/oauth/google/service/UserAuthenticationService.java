package edu.social.openid.oauth.google.service;

import edu.social.openid.oauth.exception.ApplicationException;
import edu.social.openid.oauth.google.model.ClientProperties;
import edu.social.openid.oauth.google.model.CodeExchangeResponse;
import edu.social.openid.oauth.google.util.DocumentDiscoveryService;
import edu.social.openid.oauth.google.util.OAuthEndpointsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.http.HttpMethod.POST;

@Component
public class UserAuthenticationService {

    @Autowired
    @Qualifier("googleClientProperties")
    private ClientProperties clientProperties;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OAuthEndpointsBuilder oAuthEndpointsBuilder;

    public CodeExchangeResponse exchangeCodeForAccessToken(String code) {
        String uri = oAuthEndpointsBuilder.getTokenEndpoint(code);
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<CodeExchangeResponse> responseEntity = restTemplate.exchange(uri, POST, entity, CodeExchangeResponse.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        throw new ApplicationException(responseEntity.getBody().toString());
    }
}
