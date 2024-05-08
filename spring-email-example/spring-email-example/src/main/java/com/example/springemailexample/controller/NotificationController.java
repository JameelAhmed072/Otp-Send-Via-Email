package com.example.springemailexample.controller;

import com.example.springemailexample.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class NotificationController {

    @Autowired
    private JavaMailSender javaMailSender;


//    just sending the simple email to the specified mail addrees

//    @PostMapping("/send-mail")
//    public String sendEmail(@RequestBody EmailDto emailDto){
//
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setTo(emailDto.getTo());
//        simpleMailMessage.setSubject(emailDto.getSubject());
//        simpleMailMessage.setText(emailDto.getText());
//
//        javaMailSender.send(simpleMailMessage);
//        return "Email send successfully!";
//    }


    @PostMapping("/send-mail")
    public String sendEmail(@ModelAttribute EmailDto emailDto) throws MessagingException, IOException {


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

        helper.setTo(emailDto.getTo());
        helper.setSubject(emailDto.getSubject());
        helper.setText(emailDto.getText(),true);
        helper.addAttachment(emailDto.getAttachment().getOriginalFilename(),
                convertMultipartToFile(emailDto.getAttachment(), emailDto.getAttachment().getOriginalFilename()));

        javaMailSender.send(mimeMessage);
        return "Email send successfully!";
    }

    private static File convertMultipartToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipartFile.transferTo(convFile);

        return convFile;
    }


}
