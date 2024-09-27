package com.itm.space.backendresources;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.itm.space.backendresources.api.request.UserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createUser() throws Exception {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest userRequest = new UserRequest("username" + uniqueSuffix, "email" + uniqueSuffix + "@example.com", "password", "firstname", "lastname");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void getUserById() throws Exception {
        UUID id = UUID.fromString("89a40f76-5596-4c15-b3b1-dbd51a98fff4");
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void hello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/hello"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("user"));
    }
}
