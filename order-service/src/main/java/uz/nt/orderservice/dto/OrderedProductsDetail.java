package uz.nt.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderedProductsDetail {
    private Integer product_id;
    private Double price;
    private Double amount;
}
