package uz.nt.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderedProducts {
    private Integer orderId;
    private String nameProduct;
    private String type;
    private Double amountOrder;
}
