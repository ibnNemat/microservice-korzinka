package uz.nt.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PromoCodeDto {
    private Integer id;
    private String name;
    private Date startDate;
    private Date endDate;
}
