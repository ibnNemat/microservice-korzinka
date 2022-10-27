package uz.nt.gmailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@ComponentScan({"shared.libs.security", "uz.nt.gmailservice.*"})
@EnableRedisRepositories({"shared.libs", "uz.nt.gmailservice.*"})
public class GmailServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GmailServiceApplication.class, args);
    }

}
