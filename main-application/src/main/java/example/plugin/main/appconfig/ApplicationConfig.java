package example.plugin.main.appconfig;

import example.plugin.main.application.Applications;
import example.plugin.main.domainconfig.DomainConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DomainConfig.class)
@ComponentScan(basePackageClasses = {Applications.class})
public class ApplicationConfig {
}
