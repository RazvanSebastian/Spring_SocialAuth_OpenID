package edu.social.openid.oauth.security;

public enum TokenClaims {
    EMAIL_CLAIM("email"),
    GIVEN_NAME_CLAIM("given_name"),
    FAMILY_NAME_CLAIM("family_name"),
    PICTURE_CLAIM("picture"),
    PHONE_CLAIM("phone"),
    LOCALE_CLAIM("picture");

    private String value;

    TokenClaims(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
