package uz.nt.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_history")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "payment_history_id_seq")
    @SequenceGenerator(name = "payment_history_id_seq", sequenceName = "payment_history_id_seq", allocationSize = 1)
    private Integer id;
    private Integer user_id;
    private Integer card_id;
    private Double total_price;
    private String status;
    private String description;
    @Column(columnDefinition = "date default current date")
    private Date created_at;
}
