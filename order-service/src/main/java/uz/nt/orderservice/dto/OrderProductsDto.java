package uz.nt.orderservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shared.libs.dto.ProductDto;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductsDto {
    private Integer id;
    private Integer order_id;
    private Integer product_id;
    private Integer amount;
}
