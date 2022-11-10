package uz.nt.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.productservice.entity.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Integer> {

    boolean existsByName(String name);
}
