package uz.nt.userservice.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import shared.libs.security.MyFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private MyFilterChain filterChain;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .antMatchers("/user/login/**").permitAll()
                .antMatchers("/user/sign-in/**").permitAll()
                .and()
                .addFilterBefore(filterChain, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}