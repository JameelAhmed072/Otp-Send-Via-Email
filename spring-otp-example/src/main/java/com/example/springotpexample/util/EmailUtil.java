package com.example.springotpexample.util;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {


        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        helper.setTo(email);
        helper.setSubject("Verify OTP");
        helper.setText("""
                <div>
                    <a href="http://localhost:9092/verify-account?email=%s&otp=%s" target="_blank">click link to verify</a>
                </div>   
                """.formatted(email,otp), true);

        javaMailSender.send(mimeMessage);

    }

}
