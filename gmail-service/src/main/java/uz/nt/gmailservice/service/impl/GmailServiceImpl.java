package uz.nt.gmailservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;
import uz.nt.gmailservice.entity.GmailRedis;
import uz.nt.gmailservice.repository.GmailRepository;
import uz.nt.gmailservice.service.GmailService;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Service
public class GmailServiceImpl implements GmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private GmailRepository gmailRepository;

    @Override
    public ResponseDto sentToGmail(String gmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        Random random = new Random();
        Integer code = random.nextInt(9999);

        message.setTo(gmail);
        message.setFrom("faxadev@gmail.com");
        message.setSubject("Verifired code");
        message.setText("Verifired code here /n " + code);

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDto userDto){
            mailSender.send(message);

            GmailRedis gmEntity = GmailRedis.builder().id(Long.valueOf(userDto.getId())).gmail(gmail).code(code).build();

            gmailRepository.save(gmEntity);

            return ResponseDto
                    .builder()
                    .code(0)
                    .success(true)
                    .message(userDto.toString())
                    .build();
        }else {
            return ResponseDto
                    .builder()
                    .code(-1)
                    .success(false)
                    .message("User not found")
                    .build();
        }
    }

    @Override
    public ResponseDto checkGmailCode(String gmail, Integer code) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDto userDto){
           Optional<GmailRedis> temp =  gmailRepository.findById(Long.valueOf(userDto.getId()));

           if (temp.isPresent()){
                GmailRedis gmEntity = temp.get();
                if (gmEntity.getGmail().equals(gmail) && Objects.equals(gmEntity.getCode(), code)){

                    gmailRepository.delete(gmEntity);
                    return ResponseDto.builder()
                            .code(0)
                            .success(true)
                            .message("Gmail verifired")
                            .build();
                }
           }
        }
        return ResponseDto
                .builder()
                .code(-3)
                .message("error")
                .success(false)
                .build();
    }
}
