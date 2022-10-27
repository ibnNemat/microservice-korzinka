package uz.nt.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.PaymentHistoryDto;
import uz.nt.orderservice.entity.PaymentHistory;
import uz.nt.orderservice.service.PaymentHistoryService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment-history")
public class PaymentHistoryController {
    private final PaymentHistoryService paymentHistoryService;

    @PostMapping
    public ResponseDto addHistory(@RequestBody PaymentHistoryDto paymentHistoryDto){
        return paymentHistoryService.addHistory(paymentHistoryDto);
    }

    @GetMapping("/{id}")
    public ResponseDto<PaymentHistoryDto> getById(@PathVariable Integer id){
        return paymentHistoryService.getById(id);
    }

    @GetMapping("/all")
    public ResponseDto<List<PaymentHistoryDto>> getAllHistories(){
        return paymentHistoryService.getAllHistories();
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteById(@PathVariable Integer id){
        return paymentHistoryService.deleteById(id);
    }

}
