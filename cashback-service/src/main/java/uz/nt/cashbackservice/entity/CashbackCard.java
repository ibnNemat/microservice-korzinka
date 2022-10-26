package uz.nt.cashbackservice.entity;

import lombok.Data;
import javax.persistence.*;

@Entity(name = "cashback_card")
@Data
public class CashbackCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cashback_gen")
    @SequenceGenerator(name = "cashback_gen", sequenceName = "cashback_seq")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private Double amount;

    private String barcode;

    @Column(name = "user_id")
    private Integer userId;
}
