package uz.nt.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.nt.orderservice.service.PaymentHistoryService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment-history")
public class PaymentHistoryController {
    private final PaymentHistoryService paymentHistoryService;


}
