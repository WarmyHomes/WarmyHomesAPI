package com.project.service.mail;

public interface MailService {
    String sendMail(String userMail, String resetCode);
    String sendMultiMediaMail();
}
