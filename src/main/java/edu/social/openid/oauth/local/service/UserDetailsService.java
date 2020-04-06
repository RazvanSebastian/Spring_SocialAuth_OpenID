package edu.social.openid.oauth.local.service;

import edu.social.openid.oauth.exception.UserNotFoundException;
import edu.social.openid.oauth.local.model.UserApplication;
import edu.social.openid.oauth.local.model.UserPayload;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    private static final UserApplication embedded = new UserApplication(123l, "razvan.parautiu@gmail.com");

    public UserApplication findUser(UserPayload userPayload) throws UserNotFoundException {
        if (embedded.getEmail().equals(userPayload.getEmail())) {
            return embedded;
        }
        throw new UserNotFoundException("User with email " + userPayload.getEmail() + " was not found!");
    }

}
