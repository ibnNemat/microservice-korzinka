package uz.nt.cashbackservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "cashback_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashbackCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cashback_gen")
    @SequenceGenerator(name = "cashback_gen", sequenceName = "cashback_seq")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private Double amount;

    private String barcode;

    private Integer userId;
}
