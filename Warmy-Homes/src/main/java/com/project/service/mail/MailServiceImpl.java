package com.project.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
    private final JavaMailSender mailSender;

    @Override
    public String sendMail(String userMail, String resetCode) {
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("warmyhomes@gmail.com");
        message.setTo(userMail);
        message.setText(resetCode);
        message.setSubject("Reset Code");
        mailSender.send(message);
        return "Gonderildi";
    }

    @Override
    public String sendMultiMediaMail() {
        return null;
    }
}
