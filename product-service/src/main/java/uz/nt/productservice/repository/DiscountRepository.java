package uz.nt.productservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.productservice.entity.Discount;

import java.util.Date;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    boolean existsByDiscountAndStartAndFinish(Double discount, Date start, Date finish);

    Page<Discount> findAllByStatusTrue(Pageable pageable);


}
