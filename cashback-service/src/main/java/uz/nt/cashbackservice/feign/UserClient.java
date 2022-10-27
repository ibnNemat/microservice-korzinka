package uz.nt.cashbackservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.LoginDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.configuration.FeignConfiguration;

import java.util.HashMap;
import java.util.List;


@FeignClient(url = "http://localhost:8003", name = "user-service", configuration = FeignConfiguration.class)
public interface UserClient {

    @GetMapping("/")
    ResponseDto<Integer> getCashbackCardId(@RequestParam Integer userId);


    @GetMapping("/")
    ResponseDto<HashMap<Integer, Integer>> getListCashbackCardId(@RequestParam List<Integer> usersId);

    @PostMapping
    ResponseDto<JWTResponseDto> login(@RequestBody LoginDto loginDto);

}
