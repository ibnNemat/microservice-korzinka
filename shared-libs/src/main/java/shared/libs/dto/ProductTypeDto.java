package shared.libs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeDto {
    private Integer id;
    private String name;
    private String barcode;
    private List<ProductDto> products;
    private UnitDto unit;
    private Integer parentId;
}
