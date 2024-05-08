package com.example.springotpexample.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private boolean active;
    private String otp;
    private LocalDateTime otpGeneratedTime;

}
