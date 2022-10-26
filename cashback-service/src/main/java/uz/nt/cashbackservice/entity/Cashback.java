package uz.nt.cashbackservice.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class Cashback {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cashback_gen")
    @SequenceGenerator(name = "cashback_gen", sequenceName = "cashback_seq")
    @Column(name = "id", nullable = false)
    private Integer id;

    private Double amount;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "percent")
    private Integer percent = 1;

}
