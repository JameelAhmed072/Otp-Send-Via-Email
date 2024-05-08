package com.example.springemailexample.dto;


import jakarta.mail.Multipart;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder

public class EmailDto {

    private String to;
    private String subject;
    private String text;

    private MultipartFile attachment;


}
