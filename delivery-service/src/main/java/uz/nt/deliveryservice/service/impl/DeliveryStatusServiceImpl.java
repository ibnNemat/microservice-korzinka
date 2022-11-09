package uz.nt.deliveryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import uz.nt.deliveryservice.dto.DeliveryStatusDto;
import uz.nt.deliveryservice.entity.Delivery;
import uz.nt.deliveryservice.entity.DeliveryStatus;
import uz.nt.deliveryservice.repository.DeliveryRepository;
import uz.nt.deliveryservice.repository.DeliveryStatusRepository;
import uz.nt.deliveryservice.service.DeliveryStatusService;
import uz.nt.deliveryservice.service.mapper.DeliveryStatusMap;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryStatusServiceImpl implements DeliveryStatusService {

    private final DeliveryStatusRepository deliveryStatusRepository;

    private final DeliveryRepository deliveryRepository;

    @Override
    public ResponseDto<DeliveryStatusDto> createDeliveryStatus(Integer deliveryId) {
        DeliveryStatus deliveryStatus = DeliveryStatus.builder().deliveryId(deliveryId).build();

        deliveryStatus = deliveryStatusRepository.save(deliveryStatus);

        return ResponseDto.<DeliveryStatusDto>builder().code(0).success(true).message("Ok").responseData(DeliveryStatusMap.toDto(deliveryStatus)).build();
    }

    @Override
    public ResponseDto<DeliveryStatusDto> updateByDeliveryId(Integer deliveryId) {
        try {
            if (deliveryId == null || deliveryId < 1)
                return ResponseDto.<DeliveryStatusDto>builder().code(1).success(false).message("DeliveryId is null or negative").build();

            Optional<DeliveryStatus> optional = deliveryStatusRepository.findByDeliveryId(deliveryId);

            if (optional.isEmpty()) {
                Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);

                if (optionalDelivery.isEmpty())
                    return ResponseDto.<DeliveryStatusDto>builder().code(2).success(false).message("Not found by this deliveryId").build();

                return ResponseDto.<DeliveryStatusDto>builder().code(3).success(false).message("Canceled").build();
            }

            DeliveryStatus deliveryStatus = optional.get();

            if (deliveryStatus.isAccepted()) {
                if (deliveryStatus.isInPreparation()) {
                    if (deliveryStatus.isOnWay()) {
                        deliveryStatus.setDelivered(true);
                    }
                    else deliveryStatus.setOnWay(true);
                }
                else deliveryStatus.setInPreparation(true);
            }
            else deliveryStatus.setAccepted(true);

            deliveryStatus = deliveryStatusRepository.save(deliveryStatus);
            return ResponseDto.<DeliveryStatusDto>builder().code(0).success(true).message("Updated").responseData(DeliveryStatusMap.toDto(deliveryStatus)).build();

        } catch (Exception e) {
            return ResponseDto.<DeliveryStatusDto>builder().code(-1).success(false).message(e.getMessage()).build();
        }
    }

    @Override
    public ResponseDto<DeliveryStatusDto> deleteByDeliveryId(Integer deliveryId) {
        Optional<DeliveryStatus> optional = deliveryStatusRepository.findByDeliveryId(deliveryId);

        deliveryStatusRepository.deleteByDeliveryId(deliveryId);

        return ResponseDto.<DeliveryStatusDto>builder().code(0).success(true).message("Ok").responseData(DeliveryStatusMap.toDto(optional.get())).build();
    }

    @Override
    public ResponseDto<DeliveryStatusDto> getByDeliveryIdAndPhone(Integer deliveryId, String deliveryPhone) {
        try {
            if (deliveryId == null || deliveryId < 1)
                return ResponseDto.<DeliveryStatusDto>builder().code(1).success(false).message("DeliveryId is null or negative").build();

            if (deliveryPhone == null || deliveryPhone.equals(""))
                return ResponseDto.<DeliveryStatusDto>builder().code(1).success(false).message("Phone is null or empty").build();

            Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);

            if (optionalDelivery.isEmpty() || !optionalDelivery.get().getPhone().equals("+"+deliveryPhone))
                return ResponseDto.<DeliveryStatusDto>builder().code(2).success(false).message("Not found by this deliveryId and phone").build();

            if (optionalDelivery.get().isCanceled())
                return ResponseDto.<DeliveryStatusDto>builder().code(3).success(false).message("Canceled").build();

            Optional<DeliveryStatus> optional = deliveryStatusRepository.findByDeliveryId(deliveryId);

            return ResponseDto.<DeliveryStatusDto>builder().code(0).success(true).message("Ok").responseData(DeliveryStatusMap.toDto(optional.get())).build();

        } catch (Exception e) {
            return ResponseDto.<DeliveryStatusDto>builder().code(-1).success(false).message(e.getMessage()).build();
        }
    }
}
