package com.itm.space.backendresources;

import com.itm.space.backendresources.api.request.UserRequest;
import com.itm.space.backendresources.api.response.UserResponse;
import com.itm.space.backendresources.exception.BackendResourcesException;
import com.itm.space.backendresources.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;


@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createUser() {
        String uniqueSuffix = String.valueOf(System.currentTimeMillis());
        UserRequest userRequest = new UserRequest("username" + uniqueSuffix, "email" + uniqueSuffix + "@example.com", "password", "firstname", "lastname");
        userService.createUser(userRequest);
        // проверка создания пользователя в кейклоке
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void getUserById() {
        UUID id = UUID.fromString("89a40f76-5596-4c15-b3b1-dbd51a98fff4");
        UserResponse userResponse = userService.getUserById(id);
        assertNotNull(userResponse);
        // проверка корректности возврата пользователя по юид
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void createUser_ThrowsBackendResourcesException() {
        UserRequest userRequest = new UserRequest("myusername", "", "password", "firstname", "lastname");
        // Имитируйет кейклок для выброса исключения при создании пользователя
        assertThrows(BackendResourcesException.class, () -> userService.createUser(userRequest));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void getUserById_ThrowsBackendResourcesException() {
        UUID id = UUID.randomUUID();
        // Имитируйет кейклок для выброса исключения при попытке вернуть пользователя по юид (неверному)
        assertThrows(BackendResourcesException.class, () -> userService.getUserById(id));
    }
}
