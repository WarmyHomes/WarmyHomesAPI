package com.project.service.mail;

import com.project.entity.user.User;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service

public class MailServiceImpl implements MailService{
    private JavaMailSender mailSender;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }

    @Override
    public String sendMail(String email) {
       SimpleMailMessage message=new SimpleMailMessage();
       message.setFrom("noreply@metsoft.com");
       message.setTo(email);
       message.setText("Reset Code");
       message.setSubject("acma");
       mailSender.send(message);
       return "Message sent";
    }

    @Override
    public String sendMultiMediaMail() {
        return null;
    }


//    @Override
//    public String sendMail(HttpServletRequest servletRequest) {
//        String email=(String) servletRequest.getAttribute("email");
//        User user=userService.findUserByEmail(email);
//        String resetCode= user.getReset_password_code();
//        SimpleMailMessage message=new SimpleMailMessage();
//        message.setFrom("warmyhomes@gmail.com");
//        message.setTo(email);
//        message.setText(resetCode);
//        message.setSubject("Reset Code");
//        mailSender.send(message);
//        return "Gonderildi";
//    }
//
//    @Override
//    public String sendMultiMediaMail() {
//        return null;
//    }
}
