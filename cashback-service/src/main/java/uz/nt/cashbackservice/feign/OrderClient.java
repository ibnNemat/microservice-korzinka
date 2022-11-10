package uz.nt.cashbackservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shared.libs.dto.ResponseDto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@FeignClient(url = "http://locahost:8082/orders", name = "order-service")
public interface OrderClient {

    @GetMapping("/")
    ResponseDto<HashMap<Integer, Double>> getUsersBoughtMoreMillion(@RequestParam Date date);


    @GetMapping("/")
    ResponseDto<HashMap<Integer, Double>> getUsersBoughtMoreThreeMillion(@RequestParam Date date);


}
