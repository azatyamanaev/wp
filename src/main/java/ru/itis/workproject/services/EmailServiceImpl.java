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
    static {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "smtp.yandex.ru");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "8080");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    @Value("${secret.key}")
    private String secretKey;

    @Override
    public void sendNotificationAboutRegistration(SignUpDto form) {
        Properties properties = System.getProperties();
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("azatyamanaev", "Tuckjedtemyaux0");
            }
        });
        MimeMessage message = new MimeMessage(session);
        try {
            String text = getEmailPage(form, secretKey, message);
            message.setFrom(new InternetAddress("azatyamanaev@yandex.ru"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(form.getEmail()));
            message.setSubject("Registration on site");
            message.setContent(text, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void sendLinkToUploadedFile(String email, String fileName, String login) {
        Properties properties = System.getProperties();
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("azatyamanaev", "Tuckjedtemyaux0");
            }
        });
        MimeMessage message = new MimeMessage(session);
        try {
            String text = getFileLinkPage(fileName, message, login);
            message.setFrom("azatyamanaev@yandex.ru");
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Uploaded file");
            message.setContent(text, "text/html");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new IllegalStateException(e);
        }
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
    public String getFileLinkPage(String url, MimeMessage message, String login) {
        try {
            Map<String, Object> model = new HashMap();
            model.put("url", url);
            model.put("login", login);
            Template template = configuration.getTemplate("url.ftl");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
