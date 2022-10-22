package uz.nt.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nt.productservice.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByName(String name);

    @Query("select p.id from Product p where p.id = ?2 and p.amount >= ?1")
    Integer getByIdAndAmount(Integer product_id, Integer amount);
    @Query("update Product p set p.amount = p.amount - ?1 where p.id = ?2")
    void subtractProductAmount(Integer amount, Integer product_id);

    @Query("update Product p set p.amount = p.amount + ?1 where p.id = ?2")
    void addProductAmount(Integer amount, Integer product_id);
}
