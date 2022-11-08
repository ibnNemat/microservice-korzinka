package uz.nt.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import shared.libs.dto.OrderedProductsDetail;

import java.util.ArrayList;
import java.util.List;

@RedisHash(timeToLive = 60 * 15)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductsRedis {
    private Integer id;
    private List<OrderedProductsDetail> orderedProductsList = new ArrayList<>();
}
