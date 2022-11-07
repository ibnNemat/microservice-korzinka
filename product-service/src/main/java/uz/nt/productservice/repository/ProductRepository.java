package uz.nt.productservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.nt.productservice.entity.Product;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);

    Optional<Product> findByIdAndActive(Integer id, boolean active);

    List<Product> findAllByActive(boolean active);

    Page<Product> findAllByActiveIsTrue(Pageable pageable);

//    @Query("select p.id from Product p where p.id = ?2 and p.amount >= ?1")
//    Integer getByIdAndAmount(Integer product_id, Double amount);
//
    Boolean existsByIdAndAmountGreaterThan(Integer productId, Double amount);

    @Transactional
    @Modifying
    @Query("update Product p set p.amount = p.amount - :amount where p.id = :productId")
    void subtractProductAmount(@Param("amount") Double amount, @Param("productId") Integer product_id);

    @Query("update Product p set p.amount = p.amount + ?1 where p.id = ?2")
    void addProductAmount(Double amount, Integer productId);

    List<Product> findByIdIn(List<Integer> ids);

    Page<Product> findAllByDiscountNotNull(Pageable pageable);

    @Query("update Product p set p.amount = p.amount + :amount where p.id = :id")
    void rollbackProductAmount(@Param("amount") Double amount,
                               @Param("id") Integer id);
}
