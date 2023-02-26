package com.cos.photogramstart.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;


//@Configuration
//@EnableOAuth2Client
public class sdOAuth2ClientConfiguration {

//    @Bean
//    public ClientRegistrationRepository clientRegistrationRepository() {
//        List<ClientRegistration> registrations = new ArrayList<>();

      //  registrations.add(googleClientRegistration());
        // 다른 OAuth2 클라이언트들도 추가할 수 있음
//
//        return new InMemoryClientRegistrationRepository(registrations);
//    }

//    private ClientRegistration googleClientRegistration() {
//        return ClientRegistration.withRegistrationId("google")
//            .clientId("200679894785-6a8ok6016lvsuu3v75fg26v688248amj.apps.googleusercontent.com")
//            .clientSecret("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")
//            .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
//            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//            .redirectUriTemplate("http://localhost:8082/login/oauth2/code/google")
//            .redirectUri("http://localhost:8082/login/oauth2/code/google")
//            .scope("email", "profile")
//            .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth")
//            .tokenUri("https://www.googleapis.com/oauth2/v4/token")
//            .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
 //           .userNameAttributeName(IdTokenClaimNames.SUB)
 //           .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
//            .clientName("Google")
//            .build();
//    }
}

