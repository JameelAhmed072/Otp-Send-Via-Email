package com.example.springotpexample.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


public class LoginDto {

    private String email;
    private String password;

}
