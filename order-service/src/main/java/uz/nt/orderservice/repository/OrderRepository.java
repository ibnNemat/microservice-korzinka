package uz.nt.orderservice.repository;

import org.springframework.data.jpa.repository.Query;
import uz.nt.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.user_id = ?1 and o.payed = false")
    Optional<Order> findUserOrderByUserIdWherePayedIsFalse(Integer user_id);

    @Query("select max(o.id) from Order o")
    Integer getMax();

    @Query("update Order o set o.payed = true where o.user_id = ?1 and o.payed = false")
    void updateOrderPayed(Integer user_id);

    @Query("select o.id from Order o where o.payed = false")
    List<Integer> getIdByPayedFalse();

    boolean existsByUser_idAndPayedIsFalse(Integer user_id);

    Integer getByUser_idAndPayedIsFalse(Integer user_id);
}
