package com.lime.lime.shop.user;

import com.lime.lime.shop.address.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;



    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> addNewUser(@RequestBody UserDTO newUser ){
        UserEntity result = userService.creteNewAccount(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
