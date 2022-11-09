package uz.nt.deliveryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatus {

    @Id
    @GeneratedValue(generator = "status_id")
    @SequenceGenerator(name = "status_id", sequenceName = "status_id_seq", allocationSize = 1)
    private Integer id;

    private boolean accepted;

    @Column(name = "in_preparation")
    private boolean inPreparation;

    @Column(name = "on_way")
    private boolean onWay;

    private boolean delivered;

    private Integer deliveryId;
}
