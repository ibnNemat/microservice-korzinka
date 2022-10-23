package uz.nt.deliveryservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(generator = "city_id")
    @SequenceGenerator(name = "city_id", sequenceName = "city_id_seq", allocationSize = 1)
    private Integer id;

    private String name;

    @Column(name = "region_id")
    private Integer regionId;

    @OneToMany(mappedBy = "cityId")
    @Fetch(FetchMode.SUBSELECT)
    private List<Landmark> landmarks;
}
