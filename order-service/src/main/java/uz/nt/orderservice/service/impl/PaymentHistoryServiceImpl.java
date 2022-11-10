package uz.nt.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import shared.libs.response.standart.ResponseCode;
import uz.nt.orderservice.dto.PaymentHistoryDto;
import uz.nt.orderservice.entity.PaymentHistory;
import uz.nt.orderservice.repository.PaymentHistoryRepository;
import uz.nt.orderservice.service.PaymentHistoryService;
import uz.nt.orderservice.service.mapper.PaymentHistoryMapper;

import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentHistoryServiceImpl implements PaymentHistoryService {
    private final PaymentHistoryRepository historyRepository;
    private final PaymentHistoryMapper historyMapper;
    private static ResourceBundle bundle;

    @Override
    public ResponseDto addHistory(PaymentHistoryDto paymentHistoryDto) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            PaymentHistory paymentHistory = historyMapper.toEntity(paymentHistoryDto);

            historyRepository.save(paymentHistory);

            return ResponseDto.builder()
                    .code(ResponseCode.OK)
                    .message(bundle.getString("response.success"))
                    .success(true)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(ResponseCode.SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<PaymentHistoryDto> getById(Integer id) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            Optional<PaymentHistory> paymentHistory = historyRepository.findById(id);

            if (paymentHistory.isEmpty()){
                return ResponseDto.<PaymentHistoryDto>builder()
                        .code(ResponseCode.NOT_FOUND)
                        .message(bundle.getString("response.not_found"))
                        .success(false)
                        .build();
            }

            PaymentHistoryDto paymentHistoryDto = historyMapper.toDto(paymentHistory.get());

            return ResponseDto.<PaymentHistoryDto>builder()
                    .code(ResponseCode.OK)
                    .message(bundle.getString("response.success"))
                    .responseData(paymentHistoryDto)
                    .success(true)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<PaymentHistoryDto>builder()
                    .code(ResponseCode.SERVER_ERROR)
                    .message(bundle.getString("response.failed.") + " " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<PaymentHistoryDto>> getAllHistories() {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            List<PaymentHistory> historyList = historyRepository.findAll();

            if (historyList.isEmpty()){
                return ResponseDto.<List<PaymentHistoryDto>>builder()
                        .code(ResponseCode.NOT_FOUND)
                        .message(bundle.getString("response.not_found"))
                        .success(true)
                        .build();
            }

            List<PaymentHistoryDto> paymentHistoryDtos = historyList.stream()
                    .map((historyMapper::toDto)).toList();

            return ResponseDto.<List<PaymentHistoryDto>>builder()
                    .code(ResponseCode.OK)
                    .message(bundle.getString("response.success"))
                    .responseData(paymentHistoryDtos)
                    .success(true)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<List<PaymentHistoryDto>>builder()
                    .code(ResponseCode.SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto deleteById(Integer id) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            Optional<PaymentHistory> paymentHistory = historyRepository.findById(id);

            if (paymentHistory.isEmpty()){
                return ResponseDto.builder()
                        .code(ResponseCode.NOT_FOUND)
                        .message(bundle.getString("response.not_found"))
                        .success(false)
                        .build();
            }

            historyRepository.delete(paymentHistory.get());

            return ResponseDto.builder()
                    .code(ResponseCode.OK)
                    .message(bundle.getString("response.deleted"))
                    .success(true)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(ResponseCode.SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " " + e.getMessage())
                    .success(false)
                    .build();
        }
    }
}
