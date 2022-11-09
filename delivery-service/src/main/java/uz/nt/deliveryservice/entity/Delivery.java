package uz.nt.deliveryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(generator = "delivery_id")
    @SequenceGenerator(name = "delivery_id", sequenceName = "delivery_id_seq", allocationSize = 1)
    private Integer id;

    private String name;
    private String phone;
    private String address;
    private String comment;
    private boolean payment;
    private boolean express;
    private Double price;

    private boolean canceled;

    @Column(name = "order_dt")
    private LocalDateTime orderDT;

    @Column(name = "delivery_dt")
    private LocalDateTime deliveryDT;

    private Integer userId;
    private Integer orderId;
    private Integer courierId;

    private Integer regionId;
    private Integer cityId;
    private Integer landmarkId;
}
