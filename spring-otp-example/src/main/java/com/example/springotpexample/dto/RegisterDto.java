package com.example.springotpexample.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class RegisterDto {

    private String name;
    private String email;
    private String password;

}
