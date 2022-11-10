package uz.nt.gmailservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shared.libs.dto.ResponseDto;
import uz.nt.gmailservice.service.impl.GmailServiceImpl;

import javax.mail.MessagingException;

@RequiredArgsConstructor
@RestController
public class GmailController {
    private final GmailServiceImpl gmailService;
    @GetMapping("sent")
    public ResponseDto gg(@RequestParam String gmail) throws MessagingException {
      return gmailService.sentToGmail(gmail);
    }

    @PostMapping("send-verify-code")
    public ResponseDto sendToGmailVerificationCode(@RequestParam String gmail, @RequestParam Integer code) throws MessagingException {
        return gmailService.sendVerificationCodeToGmail(gmail, code);
    }

    @PostMapping("gmail-verify")
    public ResponseDto verifyGmail(@RequestParam String gmail, @RequestParam Integer code){
        return gmailService.checkGmailCode(gmail,code);
    }
}
