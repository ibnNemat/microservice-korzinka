package uz.nt.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.nt.orderservice.config.FeignConfiguration;

@FeignClient(url = "http://localhost:8005/cashback-api", name = "cashback-service", configuration = FeignConfiguration.class)
public interface CashbackClient {
    @PutMapping("cashback/subtract")
    void subtractCashback(@RequestParam Integer userId, @RequestParam Double cashbackAmount);
    @PutMapping("cashback/calculate-cashback")
    void calculateCashbackForEachShopping(@RequestParam Integer userId, @RequestParam Double totalPrice);

}
