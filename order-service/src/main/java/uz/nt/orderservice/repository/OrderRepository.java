package uz.nt.orderservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.nt.orderservice.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    @Query("select o from Orders o where o.userId = ?1 and o.payed = false")
    Optional<Orders> findUserOrderByUserIdWherePayedIsFalse(Integer userId);

    @Query("select max(o.id) from Orders o")
    Integer getMax();

    @Transactional
    @Modifying
    @Query("update Orders o set o.payed = true where o.userId = ?1 and o.payed = false")
    void updateOrderPayed(Integer userId);

    @Query("select o.id from Orders o where o.payed = false")
    List<Integer> getAllOrdersIdIsPayedFalse();

    Orders getByUserIdAndPayedIsFalse(Integer userId);

    @Modifying
    @Query("update Orders o set o.totalPrice = :price where o.id = :orderId")
    void updateOrderTotalPrice(Integer orderId, Double price);


    Optional<Integer> findByIdAndUserIdAndPayedIsFalse(Integer orderId, Integer userId);

    @Query("select o from Orders o where o.payed = true" +
            " and o.createdAt between :startOfMonth and :endOfMonth")
    List<Orders> userPayedOrderedProducts(Date startOfMonth, Date endOfMonth);
}
