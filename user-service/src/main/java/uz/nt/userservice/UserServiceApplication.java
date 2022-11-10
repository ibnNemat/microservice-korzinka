package uz.nt.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.RepositoryDefinition;

@SpringBootApplication
@ComponentScan({"shared.libs.security", "uz.nt.userservice.*"})
@EnableRedisRepositories({"shared.libs", "uz.nt.userservice.*"})
@EnableFeignClients
//@EnableConfigurationProperties
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
