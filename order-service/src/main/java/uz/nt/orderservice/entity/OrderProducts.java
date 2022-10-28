package uz.nt.orderservice.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_products_id_seq")
    @SequenceGenerator(name = "order_products_id_seq", sequenceName = "order_products_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    private Integer orderId;
    private Integer productId;
    private Double amount;
}
