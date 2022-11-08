package uz.nt.orderservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistoryDto {
    private Integer id;
    private Integer userId;
    private Integer cardId;
    private Double cashbackPayment;
    private Double cardPayment;
    private Double totalPrice;
    private String status;
    private String description;
    private Date createdAt;
}