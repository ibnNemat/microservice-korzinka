package uz.nt.cashbackservice.configuration;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignConfiguration {

//    @Bean
//    public RequestInterceptor interceptor(){
//        return (req) -> req.header("Authorization", "Bearer "
//                .concat(SecurityContextHolder.getContext().getAuthentication().getCredentials().toString()));
//    }

}
