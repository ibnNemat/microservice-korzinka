package uz.nt.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.config.FeignClientConfig;
import uz.nt.userservice.config.GmailClientConfig;

@FeignClient(url = "http://localhost:8006/gmail-api",name = "gmail-service", configuration = GmailClientConfig.class)
public interface GmailPlaceHolder {
    @PostMapping("/send-verify-code")
    ResponseDto<String> sendToGmailAndGetVerifyCode(@RequestParam String gmail, @RequestParam Integer code);
}
