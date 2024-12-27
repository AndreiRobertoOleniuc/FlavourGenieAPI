package ch.flavourgenie.flavourgenieapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping(value = "/token")
@Tag(name = "Token", description = "Token API")
public class TokenController {

    @GetMapping
    @Operation(summary = "Retrieve Token", description = "retrieve JWT Token user logged in with", operationId = "get-token")
    public Map<String, Object> getToken(@AuthenticationPrincipal OAuth2User oAuth2User) {
        String jwtToken = (String) oAuth2User.getAttributes().get("jwtToken");
        return Map.of("token", jwtToken);
    }
}
