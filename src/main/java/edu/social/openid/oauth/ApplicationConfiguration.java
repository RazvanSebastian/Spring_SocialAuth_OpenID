package edu.social.openid.oauth;

import edu.social.openid.oauth.security.RSAKeyPairs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


@Configuration
public class ApplicationConfiguration {

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RSAKeyPairs rsaKeyPairs() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return new RSAKeyPairs(resourceLoader);
    }

}
