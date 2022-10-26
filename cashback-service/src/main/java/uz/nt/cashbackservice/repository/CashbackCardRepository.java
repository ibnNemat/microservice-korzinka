package uz.nt.cashbackservice.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.nt.cashbackservice.entity.CashbackCard;

@Repository
public interface CashbackCardRepository extends JpaRepository<CashbackCard, Integer> {

    @Query(value = "update cashback_card c set amount = :money where id = :cardId", nativeQuery = true)
    void increaseCashback(@Param("money") Double money, @Param("cardId") Integer cardId);

    Boolean deleteCashbackCardByUserId(Integer userId);
    CashbackCard findCashbackCardByUserId(Integer userId);
}
