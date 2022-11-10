package uz.nt.deliveryservice.service.mapper;

import uz.nt.deliveryservice.dto.DeliveryStatusDto;
import uz.nt.deliveryservice.entity.DeliveryStatus;

public class DeliveryStatusMap {

    public static DeliveryStatusDto toDto(DeliveryStatus deliveryStatus) {
        return DeliveryStatusDto.builder()
                .id(deliveryStatus.getId())
                .deliveryId(deliveryStatus.getDeliveryId())
                .accepted(deliveryStatus.isAccepted())
                .inPreparation(deliveryStatus.isInPreparation())
                .onWay(deliveryStatus.isOnWay())
                .delivered(deliveryStatus.isDelivered())
                .build();
    }

    public static DeliveryStatus toEntity(DeliveryStatusDto deliveryStatusDto) {
        return DeliveryStatus.builder()
                .id(deliveryStatusDto.getId())
                .deliveryId(deliveryStatusDto.getDeliveryId())
                .accepted(deliveryStatusDto.isAccepted())
                .inPreparation(deliveryStatusDto.isInPreparation())
                .onWay(deliveryStatusDto.isOnWay())
                .delivered(false)
                .build();
    }
}
