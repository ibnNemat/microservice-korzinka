package uz.nt.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    private Double discount;

//    @Column(name = "start",  columnDefinition = " set default now()")
    private Date start;

    private Date finish;

//    @Column(name = "status", columnDefinition = " set default true")
    private Boolean status;

    @OneToOne
    private Product product;
}
