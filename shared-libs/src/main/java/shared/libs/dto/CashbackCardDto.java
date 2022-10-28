package shared.libs.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashbackCardDto {

    private Integer id;

    private Double amount;

    private Integer userId;

    private String barcode;

}
