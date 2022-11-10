package uz.nt.gmailservice.service;

import shared.libs.dto.ResponseDto;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface GmailService {
    ResponseDto sentToGmail(String... gmail) throws MessagingException;

    ResponseDto checkGmailCode(String gmail,Integer code);

    ResponseDto<String> sendVerificationCodeToGmail(String gmail, Integer code) throws MessagingException;
}

