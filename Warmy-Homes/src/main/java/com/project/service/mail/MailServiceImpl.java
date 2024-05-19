package com.project.service.mail;

import com.project.entity.user.User;
import com.project.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
    private JavaMailSender mailSender;
    private UserService userService;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender, UserService userService){
        this.mailSender=mailSender;
        this.userService = userService;
    }

    @Override
    public String sendMail(String email) {

        User user= userService.findUserByEmail(email);
        String resetCode=user.getReset_password_code();


       SimpleMailMessage message=new SimpleMailMessage();
       message.setFrom("noreply@metsoft.com");
       message.setTo(email);
       message.setText(resetCode);
       message.setSubject("Reset Code;");
       mailSender.send(message);
       return "Message sent";
    }

    @Override
    public String sendMultiMediaMail() {
        return null;
    }


}
