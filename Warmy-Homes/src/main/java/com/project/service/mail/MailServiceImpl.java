package com.project.service.mail;

import com.project.entity.user.User;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
    private final JavaMailSender mailSender;
    private final UserService userService;

    @Override
    public String sendMail(HttpServletRequest servletRequest) {
        String email=(String) servletRequest.getAttribute("email");
        User user=userService.findUserByEmail(email);
        String resetCode= user.getReset_password_code();
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("warmyhomes@gmail.com");
        message.setTo(email);
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
