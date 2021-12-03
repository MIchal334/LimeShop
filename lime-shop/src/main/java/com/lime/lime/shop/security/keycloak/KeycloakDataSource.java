package com.lime.lime.shop.security.keycloak;

import org.springframework.context.annotation.Profile;


public class KeycloakDataSource {
    public final static String serverUrl= "http://localhost:8080/auth";
    public final static String realm = "Lime-Shop";
    public final static String clientId = "Lime-Shop-app";
    public final static String clientSecret = "05d473ca-457c-439a-bf8e-0eed540aa3cc";

}
