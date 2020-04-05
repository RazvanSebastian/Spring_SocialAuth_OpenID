package edu.social.openid.oauth.google.model;

public class CodeExchangeResponse {
    private String access_token;
    private float expires_in;
    private String refresh_token;
    private String scope;
    private String token_type;
    private String id_token;


    // Getter Methods

    public String getAccess_token() {
        return access_token;
    }

    public float getExpires_in() {
        return expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public String getId_token() {
        return id_token;
    }

    // Setter Methods

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setExpires_in(float expires_in) {
        this.expires_in = expires_in;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }
}