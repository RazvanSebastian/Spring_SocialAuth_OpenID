package edu.social.openid.oauth.google.model;

public class AuthFlowProperties {
    private String callbackURI;
    private Boolean refreshToken;

    public String getCallbackURI() {
        return callbackURI;
    }

    public void setCallbackURI(String callbackURI) {
        this.callbackURI = callbackURI;
    }

    public Boolean isRefreshToken() {
        return refreshToken;
    }

    public void withRefreshToken(Boolean refreshToken) {
        this.refreshToken = refreshToken;
    }
}
