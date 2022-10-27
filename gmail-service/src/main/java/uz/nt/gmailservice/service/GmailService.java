package uz.nt.gmailservice.service;

import shared.libs.dto.ResponseDto;

public interface GmailService {
    ResponseDto sentToGmail(String gmail);

    ResponseDto checkGmailCode(String gmail,Integer code);

}
