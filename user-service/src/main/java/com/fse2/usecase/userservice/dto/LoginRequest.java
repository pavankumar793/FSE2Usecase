package com.fse2.usecase.userservice.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String usernameOrEmail;
    private String password;
    
}
