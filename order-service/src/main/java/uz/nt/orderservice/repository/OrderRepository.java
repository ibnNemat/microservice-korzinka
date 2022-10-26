package uz.nt.orderservice.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.nt.orderservice.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
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

//    @Query("select o.orderProducts from Order o where o.userId = :user_id and o.payed = true" +
//            " and o.created_at between :startOfMonth and :endOfMoth")
//    List<List<OrderProducts>> sumAllOfUserOrderProductsMonthly(Integer user_id, Date startOfMonth, Date endOfMonth);
}
