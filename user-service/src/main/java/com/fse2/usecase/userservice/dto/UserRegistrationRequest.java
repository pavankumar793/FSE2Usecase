package com.fse2.usecase.userservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRegistrationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password must be at least 8 characters and contain a number")
    private String password;

    @NotBlank
    private String gender;

    @NotBlank
    private String dateOfBirth;
    
}
