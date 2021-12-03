package com.lime.lime.shop.security;

import com.lime.lime.shop.model.entity.TokenModel;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/auth")
@ApiIgnore
@Profile({"!test"})
public class SecurityController {

    private final SecurityService securityService;

    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    ResponseEntity<TokenModel> getToken(@RequestBody MultiValueMap<String, String> body)  {
        TokenModel token = securityService.getToken(body);
        return ResponseEntity.ok(token);
    }


}
