package ch.flavourgenie.flavourgenieapi.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class AuthController {

    @GetMapping("/token")
    public Map<String, Object> getToken(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String jwtToken = (String) oAuth2User.getAttributes().get("jwtToken");
        return Map.of("token", jwtToken);
    }
}
