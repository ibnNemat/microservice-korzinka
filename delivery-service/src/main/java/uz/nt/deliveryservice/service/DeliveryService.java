package uz.nt.deliveryservice.service;

import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import uz.nt.deliveryservice.dto.DeliveryDto;

import java.math.BigDecimal;

@Service
public interface DeliveryService {

    ResponseDto<BigDecimal> distanceCost(String destinations);

    ResponseDto<DeliveryDto> addDelivery(DeliveryDto deliveryDto);

    ResponseDto<DeliveryDto> cancelById(Integer id);
}
