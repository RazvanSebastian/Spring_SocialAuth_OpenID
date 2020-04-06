package edu.social.openid.oauth.google.model;

import java.util.ArrayList;

public class DocumentDiscovery {
    ArrayList<Object> response_types_supported = new ArrayList<Object>();
    ArrayList<Object> subject_types_supported = new ArrayList<Object>();
    ArrayList<Object> id_token_signing_alg_values_supported = new ArrayList<Object>();
    ArrayList<Object> scopes_supported = new ArrayList<Object>();
    ArrayList<Object> token_endpoint_auth_methods_supported = new ArrayList<Object>();
    ArrayList<Object> claims_supported = new ArrayList<Object>();
    ArrayList<Object> code_challenge_methods_supported = new ArrayList<Object>();
    ArrayList<Object> grant_types_supported = new ArrayList<Object>();
    private String issuer;
    private String authorization_endpoint;
    private String device_authorization_endpoint;
    private String token_endpoint;
    private String userinfo_endpoint;
    private String revocation_endpoint;
    private String jwks_uri;


    // Getter Methods

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAuthorization_endpoint() {
        return authorization_endpoint;
    }

    public void setAuthorization_endpoint(String authorization_endpoint) {
        this.authorization_endpoint = authorization_endpoint;
    }

    public String getDevice_authorization_endpoint() {
        return device_authorization_endpoint;
    }

    public void setDevice_authorization_endpoint(String device_authorization_endpoint) {
        this.device_authorization_endpoint = device_authorization_endpoint;
    }

    public String getToken_endpoint() {
        return token_endpoint;
    }

    // Setter Methods

    public void setToken_endpoint(String token_endpoint) {
        this.token_endpoint = token_endpoint;
    }

    public String getUserinfo_endpoint() {
        return userinfo_endpoint;
    }

    public void setUserinfo_endpoint(String userinfo_endpoint) {
        this.userinfo_endpoint = userinfo_endpoint;
    }

    public String getRevocation_endpoint() {
        return revocation_endpoint;
    }

    public void setRevocation_endpoint(String revocation_endpoint) {
        this.revocation_endpoint = revocation_endpoint;
    }

    public String getJwks_uri() {
        return jwks_uri;
    }

    public void setJwks_uri(String jwks_uri) {
        this.jwks_uri = jwks_uri;
    }
}