package uz.nt.cashbackservice.entity;

import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Builder
public class Cashback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cashback_gen")
    @SequenceGenerator(name = "cashback_gen", sequenceName = "cashback_seq")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "amount", columnDefinition = "double precision default 5000")
    private Double amount;

    private String barcode;

}
