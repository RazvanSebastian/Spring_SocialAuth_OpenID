package edu.social.openid.oauth.google.client;

import edu.social.openid.oauth.google.model.CodeExchangeResponse;
import edu.social.openid.oauth.google.model.DocumentDiscovery;
import edu.social.openid.oauth.google.util.OAuthEndpointsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Component
public class OAuthGoogleClient {

    private static final String GOOGLE_DISCOVERY_URI = "https://accounts.google.com/.well-known/openid-configuration";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OAuthEndpointsBuilder oAuthEndpointsBuilder;

    public ResponseEntity<DocumentDiscovery> getDocumentDiscovery() {
        URI uri = URI.create(GOOGLE_DISCOVERY_URI);
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        return restTemplate.exchange(uri, GET, entity, DocumentDiscovery.class);
    }

    public ResponseEntity<?> exchangeCode(String code) {
        String uri = oAuthEndpointsBuilder.getTokenEndpoint(code);
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        return restTemplate.exchange(uri, POST, entity, CodeExchangeResponse.class);
    }
}
