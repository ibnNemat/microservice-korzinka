package uz.nt.cloudconfigserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
@EnableGlobalAuthentication
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();

            return http.build();
        }
}
