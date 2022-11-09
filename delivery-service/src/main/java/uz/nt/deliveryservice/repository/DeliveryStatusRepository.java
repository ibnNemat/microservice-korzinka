package uz.nt.deliveryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.deliveryservice.entity.DeliveryStatus;

import java.util.Optional;

@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Integer> {
    Optional<DeliveryStatus> findByDeliveryId(Integer deliveryId);

    void deleteByDeliveryId(Integer deliveryId);
}
