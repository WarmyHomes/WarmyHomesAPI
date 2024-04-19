package com.project.service.mail;

import javax.servlet.http.HttpServletRequest;

public interface MailService {
    String sendMail(String email);
    String sendMultiMediaMail();
}
