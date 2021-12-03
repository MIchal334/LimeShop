package com.lime.lime.shop.controler;

import com.lime.lime.shop.model.dto.TransactionDTO;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.dto.UserDTOForAdmin;
import com.lime.lime.shop.service.AdministratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RolesAllowed("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdministratorController {

    private final AdministratorService adminService;

    public AdministratorController(AdministratorService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    ResponseEntity<List<UserDTOForAdmin>> getAllUser(){
        List<UserDTOForAdmin> result = adminService.getAllUser();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/transaction/{id}")
    ResponseEntity<List<TransactionDTO>> getAllTransactionOfUser(@PathVariable(name = "id") Long userId){
        List<TransactionDTO> result = adminService.getAllTransactionOfUser(userId);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUserById(@PathVariable(name = "id") Long userId){
        adminService.deleteUserByAdmin(userId);
        return ResponseEntity.ok().build();
    }


}
