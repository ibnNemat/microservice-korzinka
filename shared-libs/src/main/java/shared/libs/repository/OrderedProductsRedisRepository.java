package shared.libs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import shared.libs.entity.OrderedProductsRedis;
@Component
public interface OrderedProductsRedisRepository extends CrudRepository<OrderedProductsRedis, Long> {
}
