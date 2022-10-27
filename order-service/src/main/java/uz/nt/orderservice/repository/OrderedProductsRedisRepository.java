package uz.nt.orderservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import uz.nt.orderservice.entity.OrderedProductsRedis;
@Component
public interface OrderedProductsRedisRepository extends CrudRepository<OrderedProductsRedis, Long> {
}
