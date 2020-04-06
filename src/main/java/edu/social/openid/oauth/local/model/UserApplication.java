package edu.social.openid.oauth.local.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserApplication extends UserPayload {

    private String phone;
    @JsonIgnore
    private String password;

    public UserApplication(String email) {
        super(email);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
