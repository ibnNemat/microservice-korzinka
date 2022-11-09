package uz.nt.deliveryservice.service;

import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import uz.nt.deliveryservice.dto.DeliveryStatusDto;

@Service
public interface DeliveryStatusService {

    ResponseDto<DeliveryStatusDto> createDeliveryStatus(Integer deliveryId);

    ResponseDto<DeliveryStatusDto> updateByDeliveryId(Integer deliveryId);

    ResponseDto<DeliveryStatusDto> deleteByDeliveryId(Integer deliveryId);

    ResponseDto<DeliveryStatusDto> getByDeliveryIdAndPhone(Integer deliveryId, String deliveryPhone);
}
