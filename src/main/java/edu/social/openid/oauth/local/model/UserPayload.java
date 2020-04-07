package edu.social.openid.oauth.local.model;

public class UserPayload {

    private String email;
    private String givenName;
    private String familyName;
    private String urlPicture;
    private String locale;

    public UserPayload() {
    }

    public UserPayload(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public UserPayload setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public UserPayload setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getFamilyName() {
        return familyName;
    }

    public UserPayload setFamilyName(String familyName) {
        this.familyName = familyName;
        return this;
    }

    public String getUrlPicture() {
        return urlPicture;
    }

    public UserPayload setUrlPicture(String urlPicture) {
        this.urlPicture = urlPicture;
        return this;
    }

    public String getLocale() {
        return locale;
    }

    public UserPayload setLocale(String locale) {
        this.locale = locale;
        return this;
    }
}
