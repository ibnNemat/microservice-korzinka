package uz.nt.cashbackservice.handler;

import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.entity.CashbackCard;

@ControllerAdvice
public class ExceptionHandlerUtil {

    @ResponseBody
    @ExceptionHandler({ExceptionCashBackHandler.CashBackId.class})
    public static ResponseDto<CashbackCard> cashBackId(ExceptionCashBackHandler.CashBackId exception) {
        return ResponseDto.<CashbackCard>builder()
                .code(-1)
                .message(exception.getMessage())
                .success(false)
                .build();
    }

    @ResponseBody
    @ExceptionHandler({ExceptionCashBackHandler.UserId.class})
    public static ResponseDto userId(ExceptionCashBackHandler.UserId exception) {
        return ResponseDto.builder()
               .code(-1)
               .message(exception.getMessage())
               .success(false)
               .build();
    }

}
