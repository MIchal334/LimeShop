package com.lime.lime.shop.security.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.*;

@Configuration
public class KeycloakConfig {

    @Bean
    @Lazy
    public static Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(KeycloakDataSource.serverUrl)
                .realm(KeycloakDataSource.realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(KeycloakDataSource.clientId)
                .clientSecret(KeycloakDataSource.clientSecret)
                .resteasyClient(
                        new ResteasyClientBuilder()
                                .connectionPoolSize(10).build()
                ).build();


    }
}