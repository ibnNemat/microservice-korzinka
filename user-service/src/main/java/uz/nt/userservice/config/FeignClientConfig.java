package uz.nt.userservice.config;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;


@Configuration
public class FeignClientConfig {

    @Bean
    public Logger.Level logger(){
        return Logger.Level.FULL;
    }

//    @Bean("microservices-interceptor")
//    public RequestInterceptor interceptor(){
//        return (req) ->{
//          if(SecurityContextHolder.getContext().getAuthentication() != null &&
//              SecurityContextHolder.getContext().getAuthentication().getCredentials() instanceof String token){
//              req.header("Authorization", "Bearer " + token);
//          }
//        };
//    }

}

