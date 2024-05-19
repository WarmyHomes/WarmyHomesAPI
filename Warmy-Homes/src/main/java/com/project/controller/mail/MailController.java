package com.project.controller.mail;

import com.project.payload.request.user.PasswordUpdateRequest;
import com.project.service.mail.MailService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MailController {
    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<String> sendMail(@RequestBody String email){

        return ResponseEntity.ok(mailService.sendMail(email));
    }


//    @PostMapping("/forgot-password") // http://localhost:8080/forgot-password
//    public ResponseEntity<String> sendMail(HttpServletRequest servletRequest) throws MessagingException {
////        String email= (String) servletRequest.getAttribute("email");
////        String resetCode= (String) servletRequest.getAttribute("reset_password_code");
//        return ResponseEntity.ok(mailService.sendMail(servletRequest));
//    }


}
