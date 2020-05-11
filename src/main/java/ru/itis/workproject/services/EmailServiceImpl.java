package ru.itis.workproject.services;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.itis.workproject.dto.LogInDto;
import ru.itis.workproject.dto.SignUpDto;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private Configuration configuration;

    @Value("${secret.key}")
    private String secretKey;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendNotificationAboutRegistration(SignUpDto form) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String text = getEmailPage(form, secretKey, message);
            helper.setText(text, true);
            helper.addTo(form.getEmail());
            helper.setSubject("Email confirmation");
        } catch (MessagingException e) {
            throw new IllegalStateException();
        }
        emailSender.send(message);
    }

    @Override
    public void sendLinkToUploadedFile(String email, String fileName) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String text = getFileLinkPage(fileName, message);
            helper.setText(text, true);
            helper.addTo(email);
            helper.setSubject("Image url");
        } catch (MessagingException e) {
            throw new IllegalStateException();
        }
        emailSender.send(message);
    }

    @Override
    public String getEmailPage(SignUpDto form, String key, MimeMessage message) {
        try {
            Map<String, Object> model = new HashMap();
            model.put("email", form.getEmail());
            model.put("token", secretKey);
            model.put("name", form.getLogin());
            Template template = configuration.getTemplate("confirmation_email.ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String getFileLinkPage(String url, MimeMessage message) {
        try {
            Map<String, Object> model = new HashMap();
            model.put("url", url);
            Template template = configuration.getTemplate("url.ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
