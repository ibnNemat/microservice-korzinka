package uz.nt.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private Integer id;
    @NotBlank(message = "name null or empty")
    private String name;
    @NotBlank(message = "phone null or empty")
    private String phone;
    @NotNull(message = "comment null")
    private String comment;
    @NotNull(message = "payment null")
    private boolean payment;
    @NotNull(message = "express null")
    private boolean express;
    @NotNull(message = "canceled null")
    private boolean canceled;
    @NotNull(message = "price null")
    private Double price;

    @NotBlank(message = "orderDT null or empty")
    private LocalDateTime orderDT;
    @NotBlank(message = "deliveryDT null or empty")
    private LocalDateTime deliveryDT;

    @NotNull(message = "userId null")
    @NegativeOrZero(message = "userId negative or zero")
    private Integer userId;
    @NotNull(message = "orderId null")
    @NegativeOrZero(message = "orderId negative or zero")
    private Integer orderId;
    @NotNull(message = "courierId null")
    @NegativeOrZero(message = "courierId negative or zero")
    private Integer courierId;

    @NotNull(message = "regionId null")
    @NegativeOrZero(message = "regionId negative or zero")
    private Integer regionId;
    @NotNull(message = "cityId null")
    @NegativeOrZero(message = "cityId negative or zero")
    private Integer cityId;
    @NotNull(message = "landmarkId null")
    @NegativeOrZero(message = "landmarkId negative or zero")
    private Integer landmarkId;
    @NotBlank(message = "address null or empty")
    private String address;
}
