package com.fse2.usecase.userservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class User {
    @Id
    private String id;
    private String name;
    private String address;
    private String username;
    private String email;
    private String password;
    private String gender;
    private String dateOfBirth;
}
