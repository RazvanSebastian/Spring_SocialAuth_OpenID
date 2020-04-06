package edu.social.openid.oauth.google.model.exception;

import edu.social.openid.oauth.local.model.UserBasicInformation;

public class UnregisteredGoogleUserNotFound {

    private String message;
    private UserBasicInformation userBasicInformation;

    public UnregisteredGoogleUserNotFound(String message, UserBasicInformation userBasicInformation) {
        this.message = message;
        this.userBasicInformation = userBasicInformation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserBasicInformation getUserBasicInformation() {
        return userBasicInformation;
    }

    public void setUserBasicInformation(UserBasicInformation userBasicInformation) {
        this.userBasicInformation = userBasicInformation;
    }
}
