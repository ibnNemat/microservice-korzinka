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
@Table(name = "landmarks")
public class Landmark {

    @Id
    @GeneratedValue(generator = "landmark_id")
    @SequenceGenerator(name = "landmark_id", sequenceName = "landmark_id_seq", allocationSize = 1)
    private Integer id;

    private String name;

    @Column(name = "city_id")
    private Integer cityId;
}
