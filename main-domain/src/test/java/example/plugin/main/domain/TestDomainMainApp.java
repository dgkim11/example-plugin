package example.plugin.main.domain;

import example.plugin.main.domainconfig.DomainConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(DomainConfig.class)
public class TestDomainMainApp {
    public static void main(String[] args) {
        SpringApplication.run(TestDomainMainApp.class, args);
    }
}
