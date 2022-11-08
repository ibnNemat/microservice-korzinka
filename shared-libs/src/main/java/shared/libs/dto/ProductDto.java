package shared.libs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;

    private String name;

    private ProductTypeDto type;

    private Double amount;

    private Double price;

    private String caption;

    private Boolean active;

    private DiscountDto discount;

    private Date createdAt;

    public String toString(){
        return String.format("Id: %d\n" +
                "Name_product: %s\n" +
                "Price: %.2f\n" +
                "Amount: %.2f", getId(), getName(), getPrice(), getAmount());
    }
}
