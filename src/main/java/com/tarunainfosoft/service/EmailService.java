package com.tarunainfosoft.service;

import com.tarunainfosoft.entity.Appear;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender  mailSender;

    public void sendVerificationEmail(Appear appear) throws MessagingException {
        String url = "http://192.168.233.155:8082/appear/verify?token=" + appear.getVerificationToken();
        String subject = "Email Verification";
        String messageContent = buildEmailContent(appear.getFirstName(), appear.getEmail(), url, "Verify your email", "Verify Email");

        sendHtmlEmail(appear.getEmail(), subject, messageContent);
    }

    public void sendPasswordResetEmail(Appear appear) throws MessagingException {
        String url = "http://192.168.233.155:8082/appear/reset-password?token=" + appear.getResetToken();
        String subject = "Reset your password";
        String messageContent = buildEmailContent(appear.getFirstName(), appear.getEmail(), url, "Reset your password", "Reset Password");

        sendHtmlEmail(appear.getEmail(), subject, messageContent);
    }
    private void sendHtmlEmail(String toEmail, String subject, String htmlContent) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // 'true' means the content is HTML
        mailSender.send(mimeMessage);
    }

    private String buildEmailContent(String name,String email, String url, String actionText, String buttonLabel) {
        return "<!DOCTYPE html>"
                + "<html lang=\"en\">"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "<title>" + actionText + "</title>"
                + "<style>"
                + "    .container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #f7f7f7; border-radius: 10px; font-family: Arial, sans-serif; }"
                + "    .header { background-color: #4CAF50; padding: 10px; color: white; text-align: center; font-size: 24px; }"
                + "    .content { margin: 20px 0; font-size: 16px; color: #333; }"
                + "    .button { display: inline-block; padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px; font-size: 16px; }"
                + "    .footer { margin-top: 20px; text-align: center; color: #777; font-size: 12px; }"
                + "</style>"
                + "</head>"
                + "<body>"
                + "<div class=\"container\">"
                + "<div class=\"header\">" + actionText + "</div>"
                + "<div class=\"content\">"
                + "<p>Dear " + name + ",</p>"
                + "<p>Please click the button below to " + actionText.toLowerCase() + ":</p>"
                + "<a href=\"" + url + "\" class=\"button\">" + buttonLabel + "</a>"
                + "<p>If you did not request this, please ignore this email.</p>"
                + "</div>"
                + "<div class=\"footer\">&copy; 2024 TarunaInfosoft. All rights reserved.</div>"
                + "</div>"
                + "</body>"
                + "</html>";
    }

}
