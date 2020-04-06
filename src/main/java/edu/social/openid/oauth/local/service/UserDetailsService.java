package edu.social.openid.oauth.local.service;

import edu.social.openid.oauth.exception.UserNotFoundException;
import edu.social.openid.oauth.local.model.UserApplication;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    private static final UserApplication embedded = new UserApplication("razvan.parautiu@gmail.com");

    public UserApplication findUser(String email) throws UserNotFoundException {
        if (embedded.getEmail().equals(email)) {
            return embedded;
        }
        throw new UserNotFoundException("User with email " + email + " was not found!");
    }

}
