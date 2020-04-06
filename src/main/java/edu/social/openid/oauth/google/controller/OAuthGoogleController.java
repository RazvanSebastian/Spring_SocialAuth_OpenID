package edu.social.openid.oauth.google.controller;

import edu.social.openid.oauth.google.csrf.CsrfTokenUtil;
import edu.social.openid.oauth.google.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/oauth/google")
public class OAuthGoogleController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    /**
     * Receive a code which can be exchanged for an access_token and id_token
     * Also here we have check the CSRF token received from Google Authentication service with the token received from web client
     *
     * @param code
     * @return
     */
    @GetMapping("/auth-callback")
    public ResponseEntity<?> getAuthCallback(@RequestParam(value = "code", required = true) String code) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userAuthenticationService.exchangeCodeForAccessToken(code));
    }
}