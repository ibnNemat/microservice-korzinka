package uz.nt.orderservice.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_id_seq")
    @SequenceGenerator(name = "order_id_seq", sequenceName = "order_id_seq", allocationSize = 1)
    private Integer id;
    private Integer userId;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean payed;
    @Column(columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date createdAt;
    @Column(columnDefinition = "DOUBLE PRECISION DEFAULT 0")
    private Double totalPrice;
    @OneToMany(mappedBy = "orderId")
    private List<OrderProducts> orderProducts;
}
