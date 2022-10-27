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
    private Integer user_id;
    private Integer card_id;
    private Double cashback_payment;
    private Double card_payment;
    private Double total_price;
    private String status;
    private String description;
    private Date created_at;
}