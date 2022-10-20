package shared.libs.dto;

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
    private Integer message;
    private ResponseData responseData;
}