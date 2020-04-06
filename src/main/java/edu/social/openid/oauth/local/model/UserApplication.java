package edu.social.openid.oauth.local.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserApplication extends UserPayload {

    private Long id;
    private String phone;
    @JsonIgnore
    private String password;

    public UserApplication(Long id, String email) {
        super(email);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
