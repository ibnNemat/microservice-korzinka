package uz.nt.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Subselect("select * from orders")
public class OrderedProductsDetail {
    @Id
    private Integer product_id;
    private Double price;
    private Double amount;
}
