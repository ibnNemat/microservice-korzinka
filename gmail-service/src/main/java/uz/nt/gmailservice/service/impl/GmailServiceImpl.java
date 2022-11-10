package uz.nt.gmailservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;
import uz.nt.gmailservice.entity.GmailRedis;
import uz.nt.gmailservice.repository.GmailRepository;
import uz.nt.gmailservice.service.GmailService;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GmailServiceImpl implements GmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Autowired
    private GmailRepository gmailRepository;

    @Override
    public ResponseDto sentToGmail(String... gmail) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "25");
        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        Random random = new Random();
        Integer code = random.nextInt(9999);

        Address[] recipients = Arrays.stream(gmail)
                .map(mail -> {
                    try {
                        return new InternetAddress(mail);
                    } catch (AddressException e) {
                        log.error("Error while creating email address");
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList()
                .toArray(new Address[]{});

        message.setFrom(new InternetAddress("faxadev@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, recipients);

        String html = "<HTML>" +
                "<head>" +
                "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js\"></script>" +
                "</head>" +
                "<body><h1>" +
                "Your verification code here: " + code +
                "</h1>" +
                "<h2><a href=\"http://localhost:8006/gmail-api/gmail-verify?code=%d&gmail=%s\" id = \"verify-link\" target=\"_blank\">" +
                "Verify with link</h2>"+
                "</body>" +
                "<script>" +
                "$(function() {\n" +
                "  $(\"#verify-link\").on(\"click\",function(e) {\n" +
                "    e.preventDefault(); // cancel the link itself\n" +
                "    $.post(this.href,function(data) {\n" +
                "    });\n" +
                "  });\n" +
                "});" +
                "</script>" +
                "</HTML>";

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(String.format(html, code, gmail[0]), "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);

        message.setContent(multipart);

        message.setSubject("Verifired code");
//        message.setText("Your verification code here /n " + code);

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDto userDto){
            mailSender.send(message);

            GmailRedis gmEntity = GmailRedis.builder().id(Long.valueOf(userDto.getId())).gmail(gmail[0]).code(code).build();

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

    @Override
    public ResponseDto<String> sendVerificationCodeToGmail(String gmail, Integer code) throws MessagingException {
//        Properties prop = new Properties();
//        prop.put("mail.smtp.auth", true);
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "smtp.mailtrap.io");
//        prop.put("mail.smtp.port", "25");
//        prop.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");
//
//        Session session = Session.getInstance(prop, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username, password);
//            }
//        });

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("faxadev@gmail.com");
//        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(gmail)});
        message.setSubject("Verification code");
        message.setText("Your verification code is " + code);
        message.setTo(gmail);

        mailSender.send(message);

        return ResponseDto.<String>builder()
                .code(0)
                .success(true)
                .message("OK")
                .build();
    }
}
