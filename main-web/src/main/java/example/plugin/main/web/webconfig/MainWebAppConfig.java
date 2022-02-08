package example.plugin.main.web.webconfig;

import example.plugin.main.appconfig.ApplicationConfig;
import example.plugin.main.domain.page.AuthorizedPageService;
import example.plugin.main.domain.user.User;
import example.plugin.main.domain.user.UserRepository;
import example.plugin.main.web.interceptor.MenuAuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Properties;

@Configuration
@Import(ApplicationConfig.class)
public class MainWebAppConfig implements WebMvcConfigurer {
    private UserRepository userRepository;
    private AuthorizedPageService authorizedPageService;

    public MainWebAppConfig(UserRepository userRepository, AuthorizedPageService authorizedPageService) {
        this.userRepository = userRepository;
        this.authorizedPageService = authorizedPageService;
    }

    @Bean
    public PasswordEncoder passwordEncoder()    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()    {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(menuAuthorizationInterceptor());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3);

        return messageSource;
    }

    @Bean
    public HandlerInterceptor menuAuthorizationInterceptor()    {
        return new MenuAuthorizationInterceptor(authorizedPageService);
    }
}
