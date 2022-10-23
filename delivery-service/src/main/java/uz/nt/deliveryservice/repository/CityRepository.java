package uz.nt.deliveryservice.repository;

import uz.nt.deliveryservice.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {

    List<City> findAllByRegionId(Integer id);
}
