package uz.nt.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "product_type_id_seq")
    @SequenceGenerator(name = "product_type_id_seq", sequenceName = "product_type_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;

    private String barcode;

    @OneToMany(mappedBy = "type")
    private List<Product> products;

    @ManyToOne
    private Unit unit;

    @Column(name = "parent_id")
    private Integer parentId;
}
