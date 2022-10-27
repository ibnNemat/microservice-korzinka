package uz.nt.orderservice.service;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.PaymentHistoryDto;
import uz.nt.orderservice.entity.PaymentHistory;

import java.util.List;

public interface PaymentHistoryService {
    ResponseDto addHistory(PaymentHistoryDto paymentHistoryDto);
    ResponseDto<PaymentHistoryDto> getById(Integer id);
    ResponseDto<List<PaymentHistoryDto>> getAllHistories();
    ResponseDto deleteById(Integer id);
}
