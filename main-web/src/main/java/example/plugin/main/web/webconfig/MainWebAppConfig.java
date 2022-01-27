package example.plugin.main.web.webconfig;

import example.plugin.main.appconfig.ApplicationConfig;
import example.plugin.main.domain.user.User;
import example.plugin.main.domain.user.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@Configuration
@Import(ApplicationConfig.class)
public class MainWebAppConfig {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public MainWebAppConfig(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createUsers()   {
        userRepository.save(new User("user1", passwordEncoder.encode("password1"), "admin"));
        userRepository.save(new User("user2", passwordEncoder.encode("password2"), "user"));
        userRepository.save(new User("user3", passwordEncoder.encode("password3"), "operator"));
    }
}
