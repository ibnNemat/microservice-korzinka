package uz.nt.gmailservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shared.libs.dto.ResponseDto;
import uz.nt.gmailservice.service.impl.GmailServiceImpl;

import javax.mail.MessagingException;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
public class GmailController {
    private final GmailServiceImpl gmailService;
    @GetMapping("sent")
    public ResponseDto gg(@RequestParam String... gmail) throws MessagingException {
      return gmailService.sentToGmail(gmail);
    }

    @PostMapping("gmail-verify")
    public  ResponseDto verifyGmail(@RequestParam String gmail,Integer code){
        return gmailService.checkGmailCode(gmail,code);
    }
    @GetMapping("like")
    public void sendProductsSales(){
        gmailService.SendDiscountProductToUser();
    }
}
