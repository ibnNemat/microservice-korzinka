package uz.nt.orderservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<ResponseData> {
    private Integer code;
    private Boolean success;
    private String message;
    private ResponseData responseData;
}
