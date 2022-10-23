package uz.nt.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.PaymentHistoryDto;
import uz.nt.orderservice.entity.PaymentHistory;
import uz.nt.orderservice.repository.PaymentHistoryRepository;
import uz.nt.orderservice.service.PaymentHistoryService;
import uz.nt.orderservice.service.mapper.PaymentHistoryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentHistoryServiceImpl implements PaymentHistoryService {
    private final PaymentHistoryRepository historyRepository;
    private final PaymentHistoryMapper historyMapper;
    @Override
    public ResponseDto addHistory(PaymentHistory paymentHistory) {
        return null;
    }

    @Override
    public ResponseDto<PaymentHistoryDto> getById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto<List<PaymentHistoryDto>> getAllHistories() {
        return null;
    }

    @Override
    public ResponseDto deleteById(Integer id) {
        return null;
    }
}
