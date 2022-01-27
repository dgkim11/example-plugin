package example.plugin.main.appconfig;

import example.plugin.main.application.Applications;
import example.plugin.main.domainconfig.DomainConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Import(DomainConfig.class)
@ComponentScan(basePackageClasses = {Applications.class})
public class ApplicationConfig {
    @Bean
    public PasswordEncoder passwordEncoder()    {
        return new BCryptPasswordEncoder();
    }
}
