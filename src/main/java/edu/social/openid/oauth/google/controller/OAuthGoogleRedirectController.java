package edu.social.openid.oauth.google.controller;

import edu.social.openid.oauth.google.csrf.CsrfTokenUtil;
import edu.social.openid.oauth.google.util.OAuthEndpointsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Controller
@RequestMapping("/api/oauth/google")
public class OAuthGoogleRedirectController {

    @Autowired
    private OAuthEndpointsBuilder oAuthEndpointsBuilder;

    @GetMapping("/auth")
    public ResponseEntity<?> authnetication(HttpServletResponse response) {
        String csrfToken = CsrfTokenUtil.generate();
        response.setHeader(CsrfTokenUtil.getCsrfTokenHeaderName(), csrfToken);
        String oauthGoogleEndpoint = oAuthEndpointsBuilder.getUserSigningEndpoint(csrfToken);
        OAuthGoogleResponse oAuthGoogleResponse = new OAuthGoogleResponse(oauthGoogleEndpoint);
        return ResponseEntity.ok().contentType(APPLICATION_JSON).body(oAuthGoogleResponse);
    }

    class OAuthGoogleResponse {
        private String oauthGoogleEndpoint;

        public OAuthGoogleResponse(String oauthGoogleEndpoint) {
            this.oauthGoogleEndpoint = oauthGoogleEndpoint;
        }

        public String getOauthGoogleEndpoint() {
            return oauthGoogleEndpoint;
        }

        public void setOauthGoogleEndpoint(String oauthGoogleEndpoint) {
            this.oauthGoogleEndpoint = oauthGoogleEndpoint;
        }
    }

}
