package uz.nt.gmailservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;
import uz.nt.gmailservice.config.FeignConfig;

import java.util.List;

@FeignClient(value = "user-service",url = "localhost:8001/user-api/user",configuration = FeignConfig.class)
public interface UserFeign {
    @GetMapping()
    public ResponseDto<List<UserDto>> getAllUsers();
}
