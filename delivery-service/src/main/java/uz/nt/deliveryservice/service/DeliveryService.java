package uz.nt.deliveryservice.service;

import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;

import java.io.IOException;
import java.math.BigDecimal;

public interface DeliveryService {
    ResponseDto<BigDecimal> distanceCost(String destinations) throws IOException, InterruptedException;
}
