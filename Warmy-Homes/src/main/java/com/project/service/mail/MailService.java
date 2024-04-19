package com.project.service.mail;

import javax.servlet.http.HttpServletRequest;

public interface MailService {
    String sendMail(HttpServletRequest servletRequest);
    String sendMultiMediaMail();
}
