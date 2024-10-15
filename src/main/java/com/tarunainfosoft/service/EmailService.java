package com.tarunainfosoft.service;

import com.tarunainfosoft.entity.Appear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender  mailSender;

    public void sendVerificationEmail(Appear appear) {
        String url = "http://192.168.233.155:8082/appear/verify?token=" + appear.getVerificationToken();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("donotreply@tarunainfosoft.in");
        message.setTo(appear.getEmail());
        message.setSubject("Email Verification");
        message.setText("To confirm your account, please click on the following link: " + url);

        mailSender.send(message);
        System.out.println(url +" "+message);
    }

    public void sendPasswordResetEmail(Appear appear){
        String url = "http://192.168.233.155:8082/appear/reset-password?token=" + appear.getResetToken();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(appear.getEmail());
        message.setSubject("Rest your password");
        message.setText("To confirm your account, please click on the following link: " + url);
        mailSender.send(message);
    }
}
