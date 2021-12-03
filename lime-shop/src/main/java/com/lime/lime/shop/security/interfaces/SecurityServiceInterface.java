package com.lime.lime.shop.security.interfaces;

import com.lime.lime.shop.model.entity.TokenModel;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public interface SecurityServiceInterface {
    public TokenModel getToken(MultiValueMap body);
}
