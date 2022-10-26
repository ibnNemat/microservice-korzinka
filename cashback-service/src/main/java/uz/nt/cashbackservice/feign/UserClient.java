package uz.nt.cashbackservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shared.libs.dto.ResponseDto;

import java.util.HashMap;
import java.util.List;


@FeignClient(url = "http://localhost:8083", name = "user-service")
public interface UserClient {

    @GetMapping("/")
    ResponseDto<Integer> getCashbackCardId(@RequestParam Integer userId);


    @GetMapping("/")
    ResponseDto<HashMap<Integer, Integer>> getListCashbackCardId(@RequestParam List<Integer> usersId);

}
