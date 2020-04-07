package edu.social.openid.oauth.security.service;

import edu.social.openid.oauth.exception.UserNotFoundException;
import edu.social.openid.oauth.local.model.UserPayload;
import org.springframework.stereotype.Service;

@Service
public interface IUserDetailsService {

    /**
     * @param userPayload used to find the user in your application and to update with the latest user details
     * @return UserPayload object
     * @throws UserNotFoundException if the user is not yet registered into your application you should redirect to a registration form which is already completed with the information received from payload
     */
    UserPayload findUser(UserPayload userPayload) throws UserNotFoundException;
}
