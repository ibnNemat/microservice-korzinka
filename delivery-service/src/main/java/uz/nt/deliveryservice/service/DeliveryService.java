package uz.nt.deliveryservice.service;

import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import uz.nt.deliveryservice.dto.DeliveryDto;

@Service
public interface DeliveryService {
    ResponseDto<Double> calculateDeliveryPrice(Double lat, Double lon);

    ResponseDto<DeliveryDto> addDelivery(DeliveryDto deliveryDto);

    ResponseDto<DeliveryDto> cancelById(Integer id);
}
