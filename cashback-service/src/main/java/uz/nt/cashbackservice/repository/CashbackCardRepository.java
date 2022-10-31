package uz.nt.cashbackservice.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.nt.cashbackservice.entity.CashbackCard;

@Repository
public interface CashbackCardRepository extends JpaRepository<CashbackCard, Integer> {


    Boolean deleteCashbackCardByUserId(Integer userId);
    CashbackCard findCashbackCardByUserId(Integer userId);

    @Query(value = "select max(barcode) from cashback_card", nativeQuery = true)
    Long getMaxBarcode();
}
