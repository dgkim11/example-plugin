package example.plugin.main.web;

import example.plugin.main.domain.user.User;
import example.plugin.main.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MainWebApp {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(MainWebApp.class, args);
    }

    @PostConstruct
    public void createSampleUsers() {
        userRepository.save(new User("user1", passwordEncoder.encode("password1"), "admin"));
        userRepository.save(new User("user2", passwordEncoder.encode("password2"), "user"));
        userRepository.save(new User("user3", passwordEncoder.encode("password3"), "operator"));
    }
}
