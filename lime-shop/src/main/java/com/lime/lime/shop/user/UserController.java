package com.lime.lime.shop.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/a")
    public String tets(){
        return "tets";
    }

}
