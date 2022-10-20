package shared.libs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String name;
    private ProductTypeDto type;
    private Integer amount;
    private Double price;

    public String toString(){
        return String.format("Id: %d\n" +
                "Name_product: %s\n" +
                "Price: %.2f\n" +
                "Amount: %d", getId(), getName(), getPrice(), getAmount());
    }
}
