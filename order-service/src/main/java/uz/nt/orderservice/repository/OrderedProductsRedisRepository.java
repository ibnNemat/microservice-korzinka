package uz.nt.orderservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uz.nt.orderservice.dto.OrderedProductsDetail;
import uz.nt.orderservice.entity.OrderedProductsRedis;

import java.util.List;

@Repository
public interface OrderedProductsRedisRepository extends CrudRepository<List<OrderedProductsDetail>, Integer> {
}
