package shared.libs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto<ResponseData> extends RepresentationModel<ResponseDto<ResponseData>> {
    private Integer code;
    private Boolean success;
    private String message;
    private ResponseData responseData;
}