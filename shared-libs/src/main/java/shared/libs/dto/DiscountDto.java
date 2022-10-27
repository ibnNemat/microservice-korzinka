package shared.libs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

    private Integer id;

    private Double discount;

    private Date start;

    private Date finish;

    private Boolean status;

    private ProductDto product;
}