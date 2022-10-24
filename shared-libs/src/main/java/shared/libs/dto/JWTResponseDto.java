package shared.libs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JWTResponseDto {
    private String token;
    private Date expireDate;
    private String refreshToken;
}
