package uz.nt.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import uz.nt.orderservice.dto.OrderedProductsDetail;

import java.util.ArrayList;
import java.util.List;

@RedisHash(timeToLive = 60 * 60 * 15)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductsRedis {
    private Long id;
    private List<OrderedProductsDetail> orderedProductsList = new ArrayList<>();
}
