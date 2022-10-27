package uz.nt.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shared.libs.dto.ProductDto;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.config.FeignConfiguration;

import java.util.List;
import java.util.Map;

@FeignClient(url = "http://localhost:8002/product-api", name = "product-service", configuration = FeignConfiguration.class)
public interface ProductClient {

    @PostMapping("/product/update-amount")
    ResponseDto<Boolean> update(@RequestParam Integer productId, @RequestParam Double amount);

    @GetMapping("product/check-amount")
    ResponseDto<Boolean> checkAmountProduct(@RequestParam Integer productId, @RequestParam Double amount);
    @PostMapping("product/set-amount")
    ResponseDto setProductAmount(Double amount, Integer productId);

    @PostMapping("/products-by-id")
    ResponseDto<Map<Integer, ProductDto>> getProductDtoList(@RequestBody List<Integer> productIdList);
}
