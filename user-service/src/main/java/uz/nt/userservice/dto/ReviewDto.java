package uz.nt.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.nt.userservice.entity.Review;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link Review} entity
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ReviewDto {
    private Integer id;
    private String message;
    private Integer ball;
    @NotNull(message = "orderId is Null")
    private Integer orderId;
    private Integer deliveryId;
}