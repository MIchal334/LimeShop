package com.lime.lime.shop;

import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusEntity;
import com.lime.lime.shop.dictionaryTable.orderStatus.OrderStatusRepository;
import com.lime.lime.shop.dictionaryTable.role.RoleEntity;
import com.lime.lime.shop.dictionaryTable.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class H2Config {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

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

    private void prepareStatusTable(){
        OrderStatusEntity orderStatus1 = new OrderStatusEntity();
        orderStatus1.setId((short) 1);
        orderStatus1.setStatusName("WAITING");
        orderStatusRepository.save(orderStatus1);

        OrderStatusEntity orderStatus2 = new OrderStatusEntity();
        orderStatus2.setId((short) 2);
        orderStatus2.setStatusName("ACCEPTED");
        orderStatusRepository.save(orderStatus2);

        OrderStatusEntity orderStatus3 = new OrderStatusEntity();
        orderStatus3.setId((short) 3);
        orderStatus3.setStatusName("DONE");
        orderStatusRepository.save(orderStatus3);

        OrderStatusEntity orderStatus4 = new OrderStatusEntity();
        orderStatus4.setId((short) 4);
        orderStatus4.setStatusName("CANCELED");
        orderStatusRepository.save(orderStatus4);

    }

    public void prepareDataBase(){
        if(roleRepository.count() == 0){
            prepareRoleTable();
        }

        if(orderStatusRepository.count() == 0){
            prepareStatusTable();
        }
    }

}
