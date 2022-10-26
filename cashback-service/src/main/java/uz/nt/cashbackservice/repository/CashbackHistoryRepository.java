package uz.nt.cashbackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.nt.cashbackservice.entity.CashbackHistory;

import java.util.Date;
import java.util.List;

@Repository
public interface CashbackHistoryRepository extends JpaRepository<CashbackHistory, Integer> {

    void deleteCashbackHistoryByCardId(Integer cardId);

    List<CashbackHistory> findAllByCardId(Integer cardId);

    List<CashbackHistory> findAllByTransactionDateBetween(Date from, Date until);
}
