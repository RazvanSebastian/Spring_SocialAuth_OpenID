package edu.social.openid.oauth.google.model.exception;

import edu.social.openid.oauth.local.model.UserPayload;

public class UnregisteredGoogleUserNotFound {

    private String message;
    private UserPayload userPayload;

    public UnregisteredGoogleUserNotFound(String message, UserPayload userPayload) {
        this.message = message;
        this.userPayload = userPayload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserPayload getUserPayload() {
        return userPayload;
    }

    public void setUserPayload(UserPayload userPayload) {
        this.userPayload = userPayload;
    }
}
