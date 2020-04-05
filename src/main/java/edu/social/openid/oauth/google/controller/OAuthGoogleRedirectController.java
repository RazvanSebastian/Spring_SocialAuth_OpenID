package edu.social.openid.oauth.google.controller;

import edu.social.openid.oauth.google.util.OAuthEndpointsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Controller
@RequestMapping("/api/oauth/google")
public class OAuthGoogleRedirectController {

    @Autowired
    private OAuthEndpointsBuilder oAuthEndpointsBuilder;

    @GetMapping("/auth")
    public ModelAndView method() {
        return new ModelAndView("redirect:" + oAuthEndpointsBuilder.getUserSigningEndpoint());
    }

}
