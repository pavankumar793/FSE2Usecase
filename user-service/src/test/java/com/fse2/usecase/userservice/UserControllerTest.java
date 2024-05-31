package com.fse2.usecase.userservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fse2.usecase.userservice.controller.UserController;
import com.fse2.usecase.userservice.dto.LoginRequest;
import com.fse2.usecase.userservice.dto.UserRegistrationRequest;
import com.fse2.usecase.userservice.service.UserService;

public class UserControllerTest {

    @Test
    public void testRegister() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController();
        userController.userService = userService;

        UserRegistrationRequest request = new UserRegistrationRequest(/* Provide test data here */);
        when(userService.register(request)).thenReturn("User registered successfully");

        ResponseEntity<?> response = userController.register(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User registered successfully", response.getBody());
    }

    @Test
    public void testAuthenticateUser() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController();
        userController.userService = userService;

        LoginRequest loginRequest = new LoginRequest(/* Provide test data here */);
        when(userService.authenticateUser(loginRequest)).thenReturn("fake_jwt");

        ResponseEntity<?> response = userController.authenticateUser(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testLogout() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController();
        userController.userService = userService;

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(userService.logout(request)).thenReturn("User logged out successfully");

        ResponseEntity<?> response = userController.logout(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User logged out successfully", response.getBody());
    }
}
