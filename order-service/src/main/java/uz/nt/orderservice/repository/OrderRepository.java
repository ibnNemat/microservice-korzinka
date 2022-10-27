package uz.nt.orderservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.nt.orderservice.entity.OrderProducts;
import uz.nt.orderservice.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("select o from Orders o where o.userId = ?1 and o.payed = false")
    Optional<Orders> findUserOrderByUserIdWherePayedIsFalse(Integer user_id);

    @Query("select max(o.id) from Orders o")
    Integer getMax();

    @Transactional
    @Modifying
    @Query("update Orders o set o.payed = true where o.userId = ?1 and o.payed = false")
    void updateOrderPayed(Integer user_id);

    @Query("select o.id from Orders o where o.payed = false")
    List<Integer> getAllOrdersIdIsPayedFalse();

    Orders getByUserIdAndPayedIsFalse(Integer user_id);

    @Modifying
    @Query("update Orders o set o.total_price = :price where o.id = :order_id")
    void updateOrderTotalPrice(Integer order_id, Double price);

    @Query("select o from Orders o where o.payed = true" +
            " and o.created_at between :startOfMonth and :endOfMoth")
    List<Orders> userPayedOrderedProducts(Date startOfMonth, Date endOfMonth);
}
