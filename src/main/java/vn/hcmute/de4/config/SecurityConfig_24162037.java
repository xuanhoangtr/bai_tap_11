package vn.hcmute.de4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig_24162037 {
    @Bean
    public PasswordEncoder passwordEncoder_24162037() {
        return new BCryptPasswordEncoder();
    }
}
