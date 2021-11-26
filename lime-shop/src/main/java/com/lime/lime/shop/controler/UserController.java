package com.lime.lime.shop.controler;

import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.UserEntity;

import com.lime.lime.shop.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getCurrentUser(){
        UserDTO user = new UserDTO(userService.handleCurrentUser());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> addNewUser(@RequestBody UserDTO newUser ){
        UserEntity result = userService.creteNewAccount(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> editProfile(@RequestBody UserDTO newUserData){
        UserDTO result = new UserDTO(userService.editProfile(newUserData));
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/profile")
    ResponseEntity<?> editPassword(@RequestParam MultiValueMap body)  {

        userService.changePassword(body);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping("/profile")
    public ResponseEntity<?> deleteProfile(){
        userService.deleteUser(Optional.empty());
        return ResponseEntity.ok().build();
    }



}
