package edu.social.openid.oauth.security;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;

public final class RSAKeyPairs {
    private static final String ALGORITHM = "RSA";

    private static final String RSA_KEYS_BASE_PATH = "classpath:security";
    private static final String RSA_PUBLIC_KEY_FILE_NAME = "rsa-public-key";
    private static final String RSA_PRIVATE_KEY_FILE_NAME = "rsa-private-key";

    private ResourceLoader resourceLoader;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public RSAKeyPairs(ResourceLoader resourceLoader) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        this.resourceLoader = resourceLoader;
        initializePublicKey();
        initializePrivateKey();
    }

    public final PublicKey getPublicKey() {
        return publicKey;
    }

    public final PrivateKey getPrivateKey() {
        return privateKey;
    }

    private void initializePublicKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String publicKeyString = readKeyFromFile(RSA_PUBLIC_KEY_FILE_NAME);
        byte[] keyBytes = Base64.getDecoder().decode(publicKeyString.getBytes("utf-8"));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        this.publicKey = keyFactory.generatePublic(spec);
    }

    private void initializePrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String privateKeyString = readKeyFromFile(RSA_PRIVATE_KEY_FILE_NAME);
        byte[] keyBytes = Base64.getDecoder().decode(privateKeyString.getBytes("utf-8"));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        this.privateKey = keyFactory.generatePrivate(keySpec);
    }

    private String readKeyFromFile(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource(RSA_KEYS_BASE_PATH.concat("/").concat(fileName));
        InputStream inputStream = resource.getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
