package uz.nt.orderservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uz.nt.orderservice.entity.OrderedProductsRedis;


@Repository
public interface OrderedProductsRedisRepository extends CrudRepository<OrderedProductsRedis, Integer> {
}
