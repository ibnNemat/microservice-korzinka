package shared.libs.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashbackCardDto {

    private Integer id;

    private Double amount;

    private Integer userId;

    private Long barcode;

}
