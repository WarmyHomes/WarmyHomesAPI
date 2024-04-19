package com.project.controller.mail;

import com.project.entity.user.User;
import com.project.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@RestController
//@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    @PostMapping("/forgot-password") // http://localhost:8080/forgot-password
    public ResponseEntity<String> sendMail(HttpServletRequest servletRequest) throws MessagingException {
//        String email= (String) servletRequest.getAttribute("email");
//        String resetCode= (String) servletRequest.getAttribute("reset_password_code");
        return ResponseEntity.ok(mailService.sendMail(servletRequest));
    }


}
