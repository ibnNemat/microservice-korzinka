package uz.nt.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;
import uz.nt.deliveryservice.dto.DeliveryDto;
import uz.nt.deliveryservice.service.DeliveryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/price")
    public ResponseDto<Double> calculateDeliveryPrice(@RequestParam Double lat, Double lon) {
        return deliveryService.calculateDeliveryPrice(lat, lon);
    }

    @PostMapping
    public ResponseDto<DeliveryDto> addDelivery(@RequestBody @Valid DeliveryDto deliveryDto) {
        return deliveryService.addDelivery(deliveryDto);
    }

    @PatchMapping("/{id}")
    public ResponseDto<DeliveryDto> cancelById(@PathVariable Integer id) {
        return deliveryService.cancelById(id);
    }
}
