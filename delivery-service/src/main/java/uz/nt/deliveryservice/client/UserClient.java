package uz.nt.deliveryservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.LoginDto;
import shared.libs.dto.ResponseDto;


@FeignClient(url = "localhost:8001/user-api/user", name = "user-service")
public interface UserClient {

    @PostMapping("/login")
    ResponseDto<JWTResponseDto> getToken(@RequestBody LoginDto loginDto);
}
