package uz.nt.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.nt.orderservice.entity.OrderProducts;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Integer id;
    private Integer user_id;
    private Boolean payed;
    private List<OrderProductsDto> orderProducts;
    private Date created_at;
}
