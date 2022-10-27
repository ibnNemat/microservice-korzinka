package uz.nt.productservice.errors;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.errors.exceptions.DataExceptions;
import uz.nt.productservice.errors.exceptions.PaginationExceptions;

@ControllerAdvice
public class UtilExceptionHandlers {

    @ResponseBody
    @ExceptionHandler({PaginationExceptions.Page.class})
    public ResponseDto<Object> paginationPage(PaginationExceptions.Page ex){
        return ResponseDto.builder()
                .code(-3).success(false).message(ex.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler({PaginationExceptions.Size.class})
    public ResponseDto<Object> paginationSize(PaginationExceptions.Size ex){
        return ResponseDto.builder()
                .code(-3).success(false).message(ex.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler({DataExceptions.Enable.class})
    public ResponseDto<Object> enable(DataExceptions.Enable enable){
        return ResponseDto.builder()
                .code(-1).success(true).message(enable.getMessage()).build();
    }

    @ResponseBody
    @ExceptionHandler({DataExceptions.Disable.class})
    public ResponseDto<Object> disable(DataExceptions.Disable disable){
        return ResponseDto.builder()
                .code(-2).success(false).message(disable.getMessage()).build();
    }
}
