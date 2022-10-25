package uz.nt.productservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shared.libs.dto.ProductTypeDto;
import uz.nt.productservice.entity.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    boolean existsByName(String name);

    Page<ProductType> findAllByParentIdIsNotNull(Pageable pageable);

    Page<ProductType> findByParentIdIsNull(Pageable pageable);
}
