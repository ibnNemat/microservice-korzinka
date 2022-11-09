package uz.nt.deliveryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.distance.DistanceInfo;
import uz.nt.deliveryservice.client.DistanceClient;
import uz.nt.deliveryservice.service.DeliveryService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Service
@Slf4j
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DistanceClient distanceClient;
    private final String ORIGINS = "41.2859494,69.2029124";
//    41.2854528,69.203931

    private final String DEPARTURE_TIME = "now";

    @Value("${distance.security.key}")
    private final String KEY;
    private final Double COST_PER_KILOMETER = 1500D;
    @Override
    public ResponseDto<BigDecimal> distanceCost(String destinations) throws IOException, InterruptedException {
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
                cost = COST_PER_KILOMETER * dis;
            }catch (Exception e){
                log.error(e.getMessage());
                cost = null;
            }
            if (cost == null){
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shared.libs.dto.ResponseDto;
import uz.nt.deliveryservice.dto.DeliveryDto;
import uz.nt.deliveryservice.entity.Delivery;
import uz.nt.deliveryservice.repository.DeliveryRepository;
import uz.nt.deliveryservice.service.DeliveryService;
import uz.nt.deliveryservice.service.DeliveryStatusService;
import uz.nt.deliveryservice.service.mapper.DeliveryMap;
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
