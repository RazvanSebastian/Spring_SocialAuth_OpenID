package edu.social.openid.oauth.local.service;

import edu.social.openid.oauth.exception.UserNotFoundException;
import edu.social.openid.oauth.local.model.UserApplication;
import edu.social.openid.oauth.local.model.UserPayload;
import edu.social.openid.oauth.security.service.IUserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsService implements IUserDetailsService {

    private static final UserApplication embedded = new UserApplication(123l, "razvan.parautiu@gmail.com");

    public UserPayload findUser(UserPayload userPayload) throws UserNotFoundException {
        if (embedded.getEmail().equals(userPayload.getEmail())) {
            return embedded;
        }
        throw new UserNotFoundException("User with email " + userPayload.getEmail() + " was not found!");
    }

}
