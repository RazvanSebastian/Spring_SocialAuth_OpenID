package edu.social.openid.oauth.google.controller;

import edu.social.openid.oauth.google.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth/google")
public class OAuthGoogleCallbackController {

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    @GetMapping("/auth-callback")
    public ResponseEntity<?> getAuthCallback(@RequestParam(value = "code", required = true) String code) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userAuthenticationService.exchangeCodeForAccessToken(code));
    }
}
