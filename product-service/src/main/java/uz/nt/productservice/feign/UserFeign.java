package uz.nt.productservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.JWTResponseDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;
import uz.nt.productservice.config.FeignConfig;
import uz.nt.productservice.dto.LoginDto;

@FeignClient(name = "UserFeignClient", url = "http://localhost:8001/user-api/user", configuration = {FeignConfig.class})
public interface UserFeign {

    @PostMapping("/login")
    ResponseDto<JWTResponseDto> token(@RequestBody LoginDto loginDto);

    @PostMapping
    ResponseDto<UserDto> addUser(@RequestBody UserDto userDto);

    @DeleteMapping("{id}")
    ResponseDto<UserDto> deleteUser(@PathVariable Integer id,
                                    @RequestHeader HttpHeaders headers);
}
