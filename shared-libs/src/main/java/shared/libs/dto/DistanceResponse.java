package shared.libs.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DistanceResponse {

    private Double Distance;
    private String unit;
}
