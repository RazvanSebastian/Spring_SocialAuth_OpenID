package edu.social.openid.oauth.google.controller;

import edu.social.openid.oauth.google.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth/google")
public class OAuthGoogleController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    /**
     * Return a response which contains the prepared URI to redirect the user to consent OAuth Google Endpoint
     *
     * @return
     */
    @GetMapping("/auth")
    public ResponseEntity<?> getAuthenticationGoogleConsentEndpoint() {
        return userAuthenticationService.getOAuthGoogleConsentEndpointResponse();
    }

    /**
     * Receive a code which can be exchanged for an access_token and id_token
     * Also here we have check the CSRF token received from Google Authentication service with the token received from web client
     *
     * @param code
     * @return
     */
    @GetMapping("/auth-callback")
    public ResponseEntity<?> getAuthCallback(@RequestParam(value = "code", required = true) String code) {
        return userAuthenticationService.authenticate(code);
    }


}