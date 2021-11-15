package com.lime.lime.shop.security;

import com.lime.lime.shop.model.entity.TokenModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
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


    public TokenModel getToken(MultiValueMap body) {

        body.remove("newPassword");
        Map<String, String> mapWithToken = new HashMap<>();
        TokenModel response = new TokenModel();
        String url = "http://localhost:8080/auth/realms/" + realmName + "/protocol/openid-connect/token";
        body.add("client_id", clientId);
        body.add("client_secret", client_secret);
        body.add("grant_type", grant_type);
        RestTemplate restTemplate = new RestTemplate();

        try {
            response =
                    restTemplate.postForObject(url,
                            body,
                            TokenModel.class);
        } catch (HttpClientErrorException.Unauthorized e) {

            throw new IllegalStateException("Password is wrong ");

        } catch (HttpClientErrorException.BadRequest e) {

            throw new IllegalStateException("User is deleted");

        } catch (HttpClientErrorException e) {

            throw new IllegalStateException("Error with login");
        }

        return response;
    }
}
