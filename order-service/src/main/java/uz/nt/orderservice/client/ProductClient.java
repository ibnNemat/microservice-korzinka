package uz.nt.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.config.FeignConfiguration;

@FeignClient(url = "http://localhost:8002/product-api", name = "product-service", configuration = FeignConfiguration.class)
public interface ProductClient {

    @PostMapping("/product/update-amount")
    ResponseDto<Boolean> update(@RequestParam Integer productId, @RequestParam Double amount);

    @GetMapping("product/check-amount")
    ResponseDto<Boolean> checkAmountProduct(@RequestParam Integer product_id, @RequestParam Double amount);
    @PostMapping("product/set-amount")
    ResponseDto setProductAmount(Double amount, Integer product_id);


}
