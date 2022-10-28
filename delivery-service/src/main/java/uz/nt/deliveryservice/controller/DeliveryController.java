package uz.nt.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shared.libs.dto.ResponseDto;
import uz.nt.deliveryservice.service.DeliveryService;

import java.io.IOException;
import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController {
    private final DeliveryService deliveryService;
    @GetMapping("/distance-cost")
    public String distanceCost(@RequestParam double endLat, @RequestParam double endLng) throws IOException, InterruptedException {
        return deliveryService.distanceCost(endLat, endLng);
    }
}
