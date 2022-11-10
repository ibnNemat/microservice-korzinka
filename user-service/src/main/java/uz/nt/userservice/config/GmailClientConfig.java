package uz.nt.userservice.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Configuration
@ConfigurationProperties(prefix = "gmail.service.user")
public class GmailClientConfig {
    @Autowired
    private Environment environment;
    private String name;
    private String password;

    @PostConstruct
    void init(){
        name = environment.getProperty("gmail.service.user.name");
        password = environment.getProperty("gmail.service.user.password");
    }


    @Bean("gmail-interceptor")
    RequestInterceptor interceptor(){
        return (req) ->{
            req.header("Authorization", "Basic " + new String(Base64.getEncoder().encode((name + ":" + password).getBytes())));
        };
    }
}


