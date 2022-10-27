package shared.libs.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashMap;
import java.util.Map;

@RedisHash(timeToLive = 60 * 60 * 15)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductsRedis {
    private Long id;
    private Map<Integer, Integer> orderedProductsMap = new HashMap<>();
}
