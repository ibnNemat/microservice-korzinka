package uz.nt.deliveryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shared.libs.dto.ResponseDto;
import uz.nt.deliveryservice.dto.DeliveryDto;
import uz.nt.deliveryservice.entity.Delivery;
import uz.nt.deliveryservice.repository.DeliveryRepository;
import uz.nt.deliveryservice.service.DeliveryService;
import uz.nt.deliveryservice.service.DeliveryStatusService;
import uz.nt.deliveryservice.service.mapper.DeliveryMap;

import java.sql.SQLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final DeliveryStatusService deliveryStatusService;

    @Override
    public ResponseDto<Double> calculateDeliveryPrice(Double lat, Double lon) {
        return null;
    }

    @Transactional(rollbackFor = {SQLException.class})
    @Override
    public ResponseDto<DeliveryDto> addDelivery(DeliveryDto deliveryDto) {
        Delivery delivery = DeliveryMap.toEntity(deliveryDto);

        delivery = deliveryRepository.save(delivery);

        deliveryStatusService.createDeliveryStatus(delivery.getId());

        return ResponseDto.<DeliveryDto>builder().code(0).success(true).message("Ok").responseData(DeliveryMap.toDto(delivery)).build();
    }

    @Transactional
    @Override
    public ResponseDto<DeliveryDto> cancelById(Integer id) {
        Optional<Delivery> optional = deliveryRepository.findById(id);

        if (optional.isEmpty())
            return ResponseDto.<DeliveryDto>builder().code(2).success(false).message("Not found").build();

        Delivery delivery = optional.get();
        delivery.setCanceled(true);

        delivery = deliveryRepository.save(delivery);

        deliveryStatusService.deleteByDeliveryId(delivery.getId());

        return ResponseDto.<DeliveryDto>builder().code(0).success(true).message("Canceled").responseData(DeliveryMap.toDto(delivery)).build();
    }
}
