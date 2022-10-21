package uz.nt.orderservice.repository;

import uz.nt.orderservice.entity.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductsRepository extends JpaRepository<OrderProducts, Integer> {

}
