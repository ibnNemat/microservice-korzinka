package uz.nt.cashbackservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
@ComponentScan({"shared.libs.*", "uz.nt.cashbackservice.*"})
@EnableRedisRepositories({"shared.libs.*", "uz.nt.cashbackservice.*"})
public class CashbackCardServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CashbackServiceApplication.class, args);
    }

}
