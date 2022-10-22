package uz.nt.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.productservice.entity.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    boolean existsByName(String name);
}
