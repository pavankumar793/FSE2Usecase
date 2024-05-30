package com.fse2.usecase.userservice.service;

import javax.servlet.http.HttpServletRequest;

import com.fse2.usecase.userservice.dto.LoginRequest;
import com.fse2.usecase.userservice.dto.UserRegistrationRequest;

public interface UserService {
    String register(UserRegistrationRequest request);
    String authenticateUser(LoginRequest loginRequest);
    String logout(HttpServletRequest request);
}
