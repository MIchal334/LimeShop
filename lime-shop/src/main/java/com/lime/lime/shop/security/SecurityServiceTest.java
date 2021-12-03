package com.lime.lime.shop.security;

import com.lime.lime.shop.model.entity.TokenModel;
import com.lime.lime.shop.security.interfaces.SecurityServiceInterface;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
@Profile({"test"})
public class SecurityServiceTest implements SecurityServiceInterface {


    @Override
    public TokenModel getToken(MultiValueMap body) {
        return null;
    }
}
