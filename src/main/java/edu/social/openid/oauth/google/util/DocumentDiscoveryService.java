package edu.social.openid.oauth.google.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.social.openid.oauth.exception.ApplicationException;
import edu.social.openid.oauth.google.client.OAuthGoogleClient;
import edu.social.openid.oauth.google.model.DocumentDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Component
public class DocumentDiscoveryService {

    private static final String DISCOVERY_URI = "https://accounts.google.com/.well-known/openid-configuration";

    @Autowired
    @Qualifier("OAuthGoogleClient")
    private OAuthGoogleClient client;

    @Autowired
    private ResourceLoader resourceLoader;

    public DocumentDiscovery getDocumentDiscovery() {
        ResponseEntity<DocumentDiscovery> response = client.getDocumentDiscovery();
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            try {
                return getHardcodedDocument();
            } catch (IOException e) {
                throw new ApplicationException(e.getMessage());
            }
        }
    }

    private DocumentDiscovery getHardcodedDocument() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:discovery-docs/google-discovery.json");
        InputStream inputStream = resource.getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String discoveryDoc = reader.lines().collect(Collectors.joining());
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(discoveryDoc, DocumentDiscovery.class);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
