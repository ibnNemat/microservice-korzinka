package uz.nt.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shared.libs.dto.ResponseDto;

@FeignClient(url = "//http://localhost:8888",name = "gmail-service",configuration = FeignClient.class)
public interface GmailPlaceHolder {
    @GetMapping("/send")
    ResponseDto<Integer> sendToGmailAndGetVerifyCode(@RequestParam String gmail);
}
