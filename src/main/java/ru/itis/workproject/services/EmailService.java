package ru.itis.workproject.services;

import ru.itis.workproject.dto.SignUpDto;

import javax.mail.internet.MimeMessage;

public interface EmailService {
    void sendNotificationAboutRegistration(SignUpDto form);
    void sendLinkToUploadedFile(String email, String fileName);
    String getEmailPage(SignUpDto form, String key, MimeMessage message);
    String getFileLinkPage(String url, MimeMessage message);
}
