package uz.nt.productservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.config.FeignConfig;
import uz.nt.productservice.dto.LoginDto;

@FeignClient(name = "UserFeignClient", url = "http://localhost:8001/user", configuration = {FeignConfig.class})
public interface UserFeign {

    @PostMapping("/login")
    ResponseDto<JWTResponseDto> token(@RequestBody LoginDto loginDto);
}
