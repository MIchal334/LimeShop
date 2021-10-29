package com.lime.lime.shop.security;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    private  final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    ResponseEntity<JSONObject> getToken(@RequestBody MultiValueMap<String,String> body) throws JSONException {
        JSONObject json = new JSONObject();
        Map<String,String> token = securityService.getToken(body);
        json.put("token", token.get("token"));
        json.put("token", token.get("refreshToken"));
        return ResponseEntity.ok(json);
    }


}
