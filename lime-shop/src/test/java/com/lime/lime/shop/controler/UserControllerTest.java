package com.lime.lime.shop.controler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lime.lime.shop.H2Config;
import com.lime.lime.shop.MockObjects;
import com.lime.lime.shop.model.dto.UserDTO;
import com.lime.lime.shop.model.entity.UserEntity;
import com.lime.lime.shop.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
@ActiveProfiles("test")
@WithMockUser(username = "admin")
class UserControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockObjects mockObjects;

    @Autowired
    private H2Config h2Config;

    @Autowired
    private WebApplicationContext context;


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
        userRepository.save(mockObjects.mockUserEntity("123", "a@wp.pl",
                "CLIENT", "admin"));
    }

    @Test
    void getCurrentUser() throws Exception {


        MvcResult resultMvc = mockMvc.perform(get("/profile")).andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        UserDTO result = mapper.readValue(resultMvc.getResponse()
                .getContentAsString(), UserDTO.class);

        assertEquals(result.getUsername(), "admin");
    }

    @Test
    void addNewUser() throws Exception {

        long sizeRepo = userRepository.count();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/register")
                .content(new ObjectMapper().writeValueAsString(
                        mockObjects.mockUserDTO("123456789", "aa@wp.pl", "PRODUCER", "miki")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        assertEquals(sizeRepo + 1, userRepository.count());
    }

    @Test
    void editProfile() throws Exception {


        MvcResult resultMcv = mockMvc.perform(put("/profile")
                        .content(mapper.writeValueAsString(mockObjects.mockUserDTO("987654321", "email@wp.pl",
                        "CLIENT", "userName")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserDTO result = mapper.readValue(resultMcv.getResponse()
                .getContentAsString(), UserDTO.class);

        assertEquals(result.getPhoneNumber(), "987654321");
        assertEquals(result.getEmail(),"email@wp.pl");
        assertEquals(result.getUsername(),"userName");
        assertEquals(result.getRoleName(),"CLIENT");
    }

    @Test
    void deleteProfile() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/profile"))
                .andExpect(status().isOk());
        Optional<UserEntity> user = userRepository.getUserByUsername("admin");

        assertTrue(user.get().isDeleted());
    }
}