package uz.nt.cashbackservice.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.nt.cashbackservice.entity.CashbackCard;

@Repository
public interface CashbackCardRepository extends JpaRepository<CashbackCard, Integer> {

    @Transactional
    void deleteCashbackCardByUserId(Integer userId);
    CashbackCard findCashbackCardByUserId(Integer userId);


    @Query(value = "select case" +
            " when ((select count(1) from cashback_card c where c.user_id= :userId) = 1) then true" +
            " else false" +
            " end", nativeQuery = true)
    Boolean existsByUserId(@Param("userId") Integer userId);
    @Query(value = "select max(barcode) from cashback_card", nativeQuery = true)
    Long getMaxBarcode();
}
