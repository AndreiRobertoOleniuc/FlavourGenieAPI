package ch.flavourgenie.flavourgenieapi.config;

import java.util.Collections;
import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import ch.flavourgenie.flavourgenieapi.services.CustomOAuth2UserService;

@Configuration
public class SecurityConfig {
        private final CustomOAuth2UserService customOAuth2UserService;
        private final SecretKey secretKey;

        public SecurityConfig(CustomOAuth2UserService customOAuth2UserService, SecretKey secretKey) {
                this.customOAuth2UserService = customOAuth2UserService;
                this.secretKey = secretKey;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                        .csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/login/**", "/oauth2/**", "/token").permitAll()
                                .anyRequest().authenticated())
                        .oauth2Login(oauth2 -> oauth2
                                .userInfoEndpoint(userInfo -> userInfo
                                        .userService(customOAuth2UserService))
                                .defaultSuccessUrl("/token", true))
                        .oauth2ResourceServer(oauth2 -> oauth2
                                .jwt(jwt -> jwt
                                                .decoder(jwtDecoder())
                                                .jwtAuthenticationConverter(jwtAuthenticationConverter())
                                )
                        );
                return http.build();
        }

        @Bean
        public JwtDecoder jwtDecoder() {
            return NimbusJwtDecoder.withSecretKey(secretKey).build();
        }

        @Bean
        public JwtAuthenticationConverter jwtAuthenticationConverter() {
                JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
                converter.setJwtGrantedAuthoritiesConverter(jwt -> {
                        // Create a default authority if no roles are present
                        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                });
                return converter;
        }
}
