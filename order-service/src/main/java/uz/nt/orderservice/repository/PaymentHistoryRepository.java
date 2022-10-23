package uz.nt.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.nt.orderservice.entity.PaymentHistory;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, Integer> {
}
