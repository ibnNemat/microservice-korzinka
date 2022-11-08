package uz.nt.orderservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsDto {
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Double amount;
}
