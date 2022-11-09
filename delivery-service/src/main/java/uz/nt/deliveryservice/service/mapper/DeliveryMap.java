package uz.nt.deliveryservice.service.mapper;

import uz.nt.deliveryservice.dto.DeliveryDto;
import uz.nt.deliveryservice.entity.Delivery;

public class DeliveryMap {

    public static DeliveryDto toDto(Delivery delivery) {
        return DeliveryDto.builder()
                .id(delivery.getId())
                .name(delivery.getName())
                .phone(delivery.getPhone())
                .orderDT(delivery.getOrderDT())
                .deliveryDT(delivery.getDeliveryDT())
                .express(delivery.isExpress())
                .comment(delivery.getComment())
                .userId(delivery.getUserId())
                .orderId(delivery.getOrderId())
                .courierId(delivery.getCourierId())
                .payment(delivery.isPayment())
                .price(delivery.getPrice())
                .regionId(delivery.getRegionId())
                .cityId(delivery.getCityId())
                .landmarkId(delivery.getLandmarkId())
                .address(delivery.getAddress())
                .canceled(delivery.isCanceled())
                .build();
    }

    public static Delivery toEntity(DeliveryDto deliveryDto) {
        return Delivery.builder()
                .id(deliveryDto.getId())
                .name(deliveryDto.getName())
                .phone(deliveryDto.getPhone())
                .orderDT(deliveryDto.getOrderDT())
                .deliveryDT(deliveryDto.getDeliveryDT())
                .express(deliveryDto.isExpress())
                .comment(deliveryDto.getComment())
                .userId(deliveryDto.getUserId())
                .orderId(deliveryDto.getOrderId())
                .courierId(deliveryDto.getCourierId())
                .payment(deliveryDto.isPayment())
                .price(deliveryDto.getPrice())
                .regionId(deliveryDto.getRegionId())
                .cityId(deliveryDto.getCityId())
                .landmarkId(deliveryDto.getLandmarkId())
                .address(deliveryDto.getAddress())
                .canceled(deliveryDto.isCanceled())
                .build();
    }
}
