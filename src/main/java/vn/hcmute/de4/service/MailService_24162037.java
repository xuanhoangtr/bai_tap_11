package vn.hcmute.de4.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService_24162037 {
    private final JavaMailSender sender;
    private final String username;
    private final boolean required;
    public MailService_24162037(JavaMailSender sender, @Value("${spring.mail.username:}") String username, @Value("${app.smtp.required:true}") boolean required) { this.sender = sender; this.username = username; this.required = required; }
    public boolean sendOtp(String recipient, String otp) {
        if (username == null || username.isBlank()) return !required;
        try {
            SimpleMailMessage message = new SimpleMailMessage(); message.setFrom(username); message.setTo(recipient); message.setSubject("Mã OTP kích hoạt tài khoản");
            message.setText("Mã OTP của bạn là: " + otp + "\nMã có hiệu lực trong 5 phút."); sender.send(message); return true;
        } catch (Exception ex) { return false; }
    }
}
