package ru.itis.workproject.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.workproject.services.EmailService;

@Aspect
@Component
@Slf4j
public class FileUploadAspect {

    @Autowired
    private EmailService emailService;

    @After(value = "execution(* ru.itis.workproject.services.FileLoadService.uploadFile(*))")
    public void after() {
    }
}
