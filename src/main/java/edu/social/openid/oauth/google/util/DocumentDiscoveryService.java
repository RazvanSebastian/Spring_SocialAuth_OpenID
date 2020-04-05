package edu.social.openid.oauth.google.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.social.openid.oauth.exception.ApplicationException;
import edu.social.openid.oauth.google.model.DocumentDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Collectors;

import static org.springframework.http.HttpMethod.GET;

@Component
public class DocumentDiscoveryService {

    private static final String DISCOVERY_URI = "https://accounts.google.com/.well-known/openid-configuration";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    public DocumentDiscovery getDocumentDiscovery() {
        URI uri = URI.create(DISCOVERY_URI);
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<DocumentDiscovery> response = restTemplate.exchange(uri, GET, entity, DocumentDiscovery.class);
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
