package ru.itis.workproject.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.workproject.services.EmailService;

import java.util.concurrent.ExecutorService;

@Aspect
@Component
@Slf4j
public class FileUploadAspect {

    private String email;
    private String login;
    @Autowired
    private EmailService emailService;

    @Pointcut("execution(* ru.itis.workproject.services.FilesService.saveFile(..))")
    public void selectAllMethodsAvaliable() {

    }

    @Before("selectAllMethodsAvaliable() && args(file, email, login)")
    public void logStringArguments(MultipartFile file, String email, String login){
        System.out.println("String argument passed=" + email);
        this.email = email;
        this.login = login;
    }

    @AfterReturning(pointcut = "selectAllMethodsAvaliable()", returning = "someValue")
    public void afterReturningAdvice(Object someValue) {
        log.info("Value: " + someValue.toString());
        emailService.sendLinkToUploadedFile(email, someValue.toString(), login);
    }
}
