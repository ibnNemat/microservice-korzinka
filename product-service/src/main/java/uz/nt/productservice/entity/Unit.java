package uz.nt.productservice.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "unit_id_seq")
    @SequenceGenerator(name = "unit_id_seq", sequenceName = "unit_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;

    private String shortName;
}
