package uz.nt.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusDto {
    private Integer id;
    private Integer deliveryId;
    private boolean accepted;
    private boolean inPreparation;
    private boolean onWay;
    private boolean delivered;
}
