package com.project.controller.mail;

import com.project.entity.user.User;
import com.project.service.mail.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;
    @GetMapping()
    public ResponseEntity<String> sendMail(HttpServletRequest servletRequest){
        String email= (String) servletRequest.getAttribute("email");
        String resetCode= (String) servletRequest.getAttribute("reset_password_code");
        return ResponseEntity.ok(mailService.sendMail(email,resetCode));
    }

}
