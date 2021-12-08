package com.lime.lime.shop.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.lime.shop.H2Config;
import com.lime.lime.shop.MockObjects;
import com.lime.lime.shop.model.dto.TransactionDTO;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.LimeEntity;
import com.lime.lime.shop.model.entity.OrderEntity;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.LimeRepository;
import com.lime.lime.shop.repository.OrderRepository;
import com.lime.lime.shop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
@ActiveProfiles("test")
@WithMockUser(username = "admin", roles = {"ADMIN"})
class AdministratorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LimeRepository limeRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockObjects mockObjects;

    @Autowired
    private H2Config h2Config;

    @Autowired
    private WebApplicationContext context;

    private UserEntity admin;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @BeforeEach
    public void prepareDB() {
        h2Config.prepareDataBase();
        userRepository.deleteAll();
        orderRepository.deleteAll();
        admin = userRepository.save(mockObjects.mockUserEntity("123", "a@wp.pl",
                "ADMIN", "admin"));
    }

    @Test
    void getAllUser() throws Exception {
        add2ClientToDb();
        add2DealerToDb();

        MvcResult resultMvc = mockMvc.perform(get("/admin")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        UserDTO[] result = mapper.readValue(resultMvc.getResponse()
                .getContentAsString(), UserDTO[].class);

        assertEquals(5, result.length);
    }


    private void add2ClientToDb() {
        UserEntity user1 = mockObjects.mockUserEntity("1234", "a@wp.pl", "Client", "client1");
        userRepository.save(user1);

        UserEntity user2 = mockObjects.mockUserEntity("4456", "b@wp.pl", "Client", "client2");
        userRepository.save(user2);
    }

    private void add2DealerToDb() {
        UserEntity user1 = mockObjects.mockUserEntity("9999", "c@wp.pl", "PRODUCER", "dealer1");
        userRepository.save(user1);

        UserEntity user2 = mockObjects.mockUserEntity("8888", "d@wp.pl", "PRODUCER", "dealer2");
        userRepository.save(user2);
    }


}