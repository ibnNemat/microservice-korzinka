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
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(generator = "region_id")
    @SequenceGenerator(name = "region_id", sequenceName = "region_id_seq", allocationSize = 1)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "regionId")
    @Fetch(FetchMode.SUBSELECT)
    private List<City> cities;
}
