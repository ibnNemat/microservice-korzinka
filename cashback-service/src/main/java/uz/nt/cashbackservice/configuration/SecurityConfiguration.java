package uz.nt.cashbackservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shared.libs.security.MyFilterChain;

@EnableWebSecurity
@Configuration
@EnableGlobalAuthentication
public class SecurityConfiguration {

    @Autowired
    private MyFilterChain myFilterChain;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .addFilterBefore(myFilterChain, UsernamePasswordAuthenticationFilter.class);

            return http.build();
        }
}
