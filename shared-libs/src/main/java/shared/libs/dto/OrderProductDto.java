package shared.libs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto {
    private Integer id;
    private OrderDto order;
    private ProductDto user;
    private Integer amount;
}
