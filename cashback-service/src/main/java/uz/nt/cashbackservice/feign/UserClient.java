package uz.nt.cashbackservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.entity.LoginDto;

@FeignClient(url = "http://localhost:8223/api/user", name = "user-service")
public interface UserClient {

    @PostMapping("/login")
    ResponseDto<JWTResponseDto> login(@RequestBody LoginDto loginDto);

}
