package uz.nt.cashbackservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class CashbackServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CashbackServiceApplication.class, args);
    }

}
