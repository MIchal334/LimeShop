package com.lime.lime.shop;

import com.lime.lime.shop.dictionaryTable.role.RoleEntity;
import com.lime.lime.shop.dictionaryTable.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class H2Config {

    @Autowired
    private RoleRepository roleRepository;

    private void prepareRoleTable(){
        RoleEntity roleProducer = new RoleEntity();
        roleProducer.setRoleName("PRODUCER");
        roleProducer.setId((short)1);
        roleRepository.save(roleProducer);

        RoleEntity roleClient = new RoleEntity();
        roleClient.setRoleName("CLIENT");
        roleClient.setId((short)2);
        roleRepository.save(roleClient);


        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setRoleName("ADMIN");
        roleAdmin.setId((short)3);
        roleRepository.save(roleAdmin);
    }

    public void prepareDataBase(){
        if(roleRepository.count() == 0){
            prepareRoleTable();
        }
    }

}
