package uz.nt.cashbackservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CashbackCardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CashbackCardServiceApplication.class, args);
    }

}
