package uz.nt.cashbackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.cashbackservice.entity.CashbackCard;

@Repository
public interface CashbackCardRepository extends JpaRepository<CashbackCard, Integer> {

}
