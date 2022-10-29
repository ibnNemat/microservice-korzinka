package uz.nt.gmailservice.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignConfig {
    @Bean
    RequestInterceptor interceptor(){
        return (req)->{
            req.header("Authorization","Bearer " + SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
        };
    }
}
