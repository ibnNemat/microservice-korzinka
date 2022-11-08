package uz.nt.orderservice.repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.nt.orderservice.entity.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface OrderProductsRepository extends JpaRepository<OrderProducts, Integer> {
    Optional<OrderProducts> findByOrderIdAndProductId(Integer orderId, Integer productId);

    @Transactional
    @Modifying
    void deleteByProductIdAndOrderId(Integer productId, Integer orderId);

    @Modifying
    void deleteByOrderId(Integer id);

    @Modifying
    boolean existsByOrderId(Integer orderId);
}
