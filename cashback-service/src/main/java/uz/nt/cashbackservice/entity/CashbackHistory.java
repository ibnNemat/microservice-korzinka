package uz.nt.cashbackservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "cashback_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashbackHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cashback_history_gen")
    @SequenceGenerator(name = "cashback_history_gen", sequenceName = "cashback_history_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cashback_card_id")
    private Integer cardId;

    @Column(name = "before_transaction")
    private Double beforeTransaction;

    @Column(name = "transaction_amount")
    private Double transactionAmount;

    @Column(name = "after_transaction")
    private Double afterTransaction;

    @Column(name = "transaction_date")
    private Date transactionDate;
}
