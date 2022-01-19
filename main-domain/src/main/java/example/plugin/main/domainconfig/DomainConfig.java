package example.plugin.main.domainconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import example.plugin.main.domain.Domains;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { Domains.class})
public class DomainConfig {
    @Bean
    public ObjectMapper objectMapper()  {
        return new ObjectMapper(new YAMLFactory());
    }
}
