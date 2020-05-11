package ru.itis.workproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.itis.workproject.security.config.WebSecurityConfig;

@Configuration
@Import({WebSecurityConfig.class, WebMvcConfig.class, LocalizationConfig.class, SessionConfig.class})
public class AppConfig {
}
