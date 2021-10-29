package com.lime.lime.shop.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityService {
    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.realm}")
    private String realmName;


    @Value("${keycloak.credentials.secret}")
    private String client_secret;

    private final String grant_type = "password";


    public Map<String, String> getToken(MultiValueMap body) {

        Map<String, String> mapWithToken = new HashMap<>();
        String response = null;
        String url = "http://localhost:8080/auth/realms/"+realmName+"/protocol/openid-connect/token";
        body.add("client_id",clientId );
        body.add("client_secret", client_secret );
        body.add("grant_type", grant_type);
        RestTemplate restTemplate = new RestTemplate();

        try {
            response =
                    restTemplate.postForObject(url,
                            body,
                            String.class);
        } catch (HttpClientErrorException e) {
            response = e.getMessage();
        }

        if (response.contains("Unauthorized")) {
            throw new IllegalStateException("Password is wrong");
        }
        return mapWithToken;
    }
}
