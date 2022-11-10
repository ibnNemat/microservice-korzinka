package uz.nt.deliveryservice.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import shared.libs.dto.ResponseDto;

import java.sql.SQLException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public ResponseDto<String> sqlExceptionHandler(SQLException e) {
        return ResponseDto.<String>builder()
                .code(-1)
                .success(false)
                .message("Backend-chi aybdor")
                .responseData(e.toString())
                .build();
    }
}
