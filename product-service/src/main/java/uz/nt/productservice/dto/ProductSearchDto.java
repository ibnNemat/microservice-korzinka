package uz.nt.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchDto {

    private Integer id;

    private String name;

    private Double amount;

    private String balance = "EQUAL";

    private Double startingPrice;

    private Double endingPrice;

    private Double currentPrice;

    private Boolean active = true;

    private Double startingDiscount;

    private Double endingDiscount;

    private Date startingDate;

    private Date endingDate;

    private Date currentDate;
}
