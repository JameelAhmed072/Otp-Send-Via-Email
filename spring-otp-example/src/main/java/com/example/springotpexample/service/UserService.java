package com.example.springotpexample.service;

import com.example.springotpexample.dto.LoginDto;
import com.example.springotpexample.dto.RegisterDto;
import com.example.springotpexample.entity.User;
import com.example.springotpexample.repository.UserRepository;
import com.example.springotpexample.util.EmailUtil;
import com.example.springotpexample.util.OtpUtil;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private OtpUtil otpUtil;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    private UserRepository userRepository;


    public String register(RegisterDto registerDto){
        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(registerDto.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again ");
        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "User Registerd Successfully";
    }

    public String verifyAccount(String email, String otp){
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found with this email" + email));

        if(user.getOtp().equals(otp) && Duration.between(user.getOtpGeneratedTime(),
                LocalDateTime.now()).getSeconds() < (1 * 60)){
            user.setActive(true);
            userRepository.save(user);
            return "OTP verified you can login";
        }
        return "Please regenerate otp and try again";
    }

    public String regenerateOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User Not Found with this email" + email));

        String otp = otpUtil.generateOtp();
        try {
            emailUtil.sendOtpEmail(email, otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send otp please try again ");
        }
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "Email send ... Please verify account within 1 minute";

    }

    public String login(LoginDto loginDto) {

        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User Not Found with this email" + loginDto.getEmail()));
        if(!loginDto.getPassword().equals(user.getPassword())){
            return "Password is incorrect";
        } else if (!user.isActive()) {
            return "Your account is not verified";
        }
        return "Login Successfull";
    }
}
