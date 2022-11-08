package uz.nt.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "product_id_seq")
    @SequenceGenerator(name = "product_id_seq", sequenceName = "product_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;

    @ManyToOne
    private ProductType type;

    private Double amount;

    private Double price;

    @Column(length = 1024)
    private String caption;

    @Column(name = "photo_path", length = 512)
    private String photoPath;

    @Column(name = "is_active")
    private Boolean active;

    @OneToOne
    private Discount discount;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "storage_life")
    private Date storageLife;
}
