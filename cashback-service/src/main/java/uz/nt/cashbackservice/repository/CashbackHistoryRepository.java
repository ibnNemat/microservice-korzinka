package uz.nt.cashbackservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.nt.cashbackservice.entity.CashbackHistory;

import java.util.Date;
import java.util.List;

@Repository
public interface CashbackHistoryRepository extends JpaRepository<CashbackHistory, Integer> {

    @Transactional
    void deleteCashbackHistoryByCardId(Integer cardId);

    List<CashbackHistory> findAllByCardId(Integer cardId);

    @Query("select c from cashback_history c where c.cardId = ?1 and c.transactionDate between ?2 and ?3")
    List<CashbackHistory> findAllByCardIdAndTransactionDateBetween(Integer cardId, Date transactionDate, Date transactionDate2);
}
