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
    @Column(name = "id")
    private Integer id;
    private Integer userId;
    private Boolean payed;
    @Column(columnDefinition = "date default current_date")
    private Date created_at;
    @OneToMany(mappedBy = "order_id")
    private List<OrderProducts> orderProducts;
}
