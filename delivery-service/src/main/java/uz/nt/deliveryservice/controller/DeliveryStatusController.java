package uz.nt.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;
import uz.nt.deliveryservice.dto.DeliveryStatusDto;
import uz.nt.deliveryservice.service.DeliveryStatusService;

@RestController
@RequestMapping("/status")
@RequiredArgsConstructor
public class DeliveryStatusController {

    private final DeliveryStatusService deliveryStatusService;

    @GetMapping
    public ResponseDto<DeliveryStatusDto> getByDeliveryIdAndPhone(@RequestParam Integer deliveryId, String deliveryPhone) {
        return deliveryStatusService.getByDeliveryIdAndPhone(deliveryId, deliveryPhone);
    }

    @PatchMapping("/{deliveryId}")
    public ResponseDto<DeliveryStatusDto> updateByDeliveryId(@PathVariable Integer deliveryId) {
        return deliveryStatusService.updateByDeliveryId(deliveryId);
    }
}
