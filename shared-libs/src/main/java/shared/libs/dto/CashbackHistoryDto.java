package shared.libs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashbackHistoryDto {

    private Integer id;
    private Integer cardId;
    private Double beforeTransaction;
    private Double transactionAmount;
    private Double afterTransaction;
    private Date transactionDate;

}
