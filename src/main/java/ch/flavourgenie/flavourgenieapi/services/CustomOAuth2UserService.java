package ch.flavourgenie.flavourgenieapi.services;

import ch.flavourgenie.flavourgenieapi.models.CustomOAuth2User;
import ch.flavourgenie.flavourgenieapi.models.User;
import ch.flavourgenie.flavourgenieapi.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final SecretKey secretKey;
    private final UserRepository userRepository;

    public CustomOAuth2UserService(SecretKey secretKey, UserRepository userRepository) {
        this.secretKey = secretKey;
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> userAttributes = oAuth2User.getAttributes();

        // Determine the provider
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String nameAttributeKey = getNameAttributeKey(registrationId);
        String email = getEmail(userAttributes, registrationId);
        String name = getName(userAttributes, registrationId);
        String providerId = getUserSubject(userAttributes, registrationId);

        // Save user to database
        User user = userRepository.findByEmail(email).orElse(new User());
        user.setEmail(email);
        user.setName(name);
        user.setProvider(registrationId);
        user.setProviderId(providerId);
        userRepository.save(user);

        String jwtToken = Jwts.builder()
                .setSubject(providerId)
                .claim("email", email)
                .claim("name", name)
                .claim("provider", registrationId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        Map<String, Object> attributes = new HashMap<>(userAttributes);
        attributes.put("jwtToken", jwtToken);

        return new CustomOAuth2User(oAuth2User.getAuthorities(), attributes, nameAttributeKey);
    }

    private String getNameAttributeKey(String registrationId) {
        return registrationId.equals("google") ? "sub" : "login";
    }

    private String getUserSubject(Map<String, Object> attributes, String registrationId) {
        return registrationId.equals("google")
                ? attributes.get("sub").toString()
                : attributes.get("id").toString();
    }

    private String getEmail(Map<String, Object> attributes, String registrationId) {
        if (registrationId.equals("google")) {
            return attributes.get("email").toString();
        } else {
            // GitHub may set email as private, handle this case
            return attributes.get("login").toString();
        }
    }

    private String getName(Map<String, Object> attributes, String registrationId) {
        if (registrationId.equals("google")) {
            return attributes.get("name").toString();
        } else {
            return attributes.get("name") != null
                    ? attributes.get("name").toString()
                    : attributes.get("login").toString();
        }
    }

}
