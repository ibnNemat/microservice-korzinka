package uz.nt.cashbackservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "cashback_card")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashbackCard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cashback_gen")
    @SequenceGenerator(name = "cashback_gen", sequenceName = "cashback_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column
    private Double amount;

    private Long barcode;

    @Column(name = "user_id")
    private Integer userId;
}
