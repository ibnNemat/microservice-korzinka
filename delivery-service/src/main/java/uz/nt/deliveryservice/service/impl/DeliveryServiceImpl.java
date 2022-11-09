package uz.nt.deliveryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.distance.DistanceInfo;
import uz.nt.deliveryservice.client.DistanceClient;
import uz.nt.deliveryservice.dto.DeliveryDto;
import uz.nt.deliveryservice.entity.Delivery;
import uz.nt.deliveryservice.repository.DeliveryRepository;
import uz.nt.deliveryservice.service.DeliveryService;
import uz.nt.deliveryservice.service.DeliveryStatusService;
import uz.nt.deliveryservice.service.mapper.DeliveryMap;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final DeliveryStatusService deliveryStatusService;
    private final DistanceClient distanceClient;
    @Value("${origins}")
    private String ORIGINS;
//    41.2854528,69.203931

    @Value("${departure.time}")
    private String DEPARTURE_TIME;

    @Value("${distance.security.key}")
    private String KEY;
    @Value("${cost.per.kilometer}")
    private Double COST_PER_KILOMETER;
    @Override
    public ResponseDto<BigDecimal> distanceCost(String destinations) {
        Double cost;
        try {
            DistanceInfo distanceInfo = distanceClient.getResponseBetweenTwoPoints(
                    ORIGINS,
                    destinations,
                    DEPARTURE_TIME,
                    KEY
            );
            String distance = distanceInfo.
                    getRows().get(0)
                    .getElements().get(0)
                    .getDistance().getText();

            Double dis = Double.parseDouble(distance.substring(0, distance.indexOf(" ")));
            cost = dis * COST_PER_KILOMETER;
        } catch (Exception e) {
            log.error(e.getMessage());
            cost = null;
        }
        if (cost == null) {
            return ResponseDto.<BigDecimal>builder()
                    .code(-1)
                    .message("Error in determining the distance price")
                    .success(false)
                    .build();
        }
        return ResponseDto.<BigDecimal>builder()
                .code(0)
                .success(true)
                .message("Ok")
                .responseData(BigDecimal.valueOf(cost))
                .build();
    }

    @Transactional
    public ResponseDto<DeliveryDto> addDelivery(DeliveryDto delivreyDto) {
        Delivery delivery = DeliveryMap.toEntity(delivreyDto);

        delivery = deliveryRepository.save(delivery);

        deliveryStatusService.createDeliveryStatus(delivery.getId());

        return ResponseDto.<DeliveryDto>builder()
                .code(0).success(true)
                .message("Ok")
                .responseData(DeliveryMap.toDto(delivery))
                .build();
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
