package uz.nt.cashbackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.cashbackservice.entity.Cashback;

@Repository
public interface CashbackRepository extends JpaRepository<Cashback, Integer> {



}
