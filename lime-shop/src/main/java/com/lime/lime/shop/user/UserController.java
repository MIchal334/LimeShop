package com.lime.lime.shop.user;

import com.lime.lime.shop.security.SecurityService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;



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
        userService.deleteUser();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> addNewUser(@RequestBody UserDTO newUser ){
        UserEntity result = userService.creteNewAccount(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
