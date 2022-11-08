package uz.nt.cashbackservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.CashbackHistoryDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.entity.CashbackHistory;
import uz.nt.cashbackservice.mapper.CashbackHistoryMapper;
import uz.nt.cashbackservice.repository.CashbackHistoryRepository;
import uz.nt.cashbackservice.service.Main.CashbackHistoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CashbackHistoryServiceImpl implements CashbackHistoryService {

    private final CashbackHistoryRepository cashbackHistoryRepository;
    private final CashbackHistoryMapper cashbackHistoryMapper;
    private final MessageSource messageSource;

    @Override
    public CashbackHistory addCashbackHistory(CashbackHistory cashbackHistory) {
        return cashbackHistoryRepository.save(cashbackHistory);
    }


    @Override
    public ResponseDto<Boolean> deleteCashbackHistoryByCardId(Integer cashbackCardId, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;
        if(cashbackCardId != null){
            cashbackHistoryRepository.deleteCashbackHistoryByCardId(cashbackCardId);

            message = messageSource.getMessage("operation.success", new String[]{} , locale);

            return ResponseDto.<Boolean>builder()
                    .code(200)
                    .responseData(true)
                    .success(true)
                    .message(message)
                    .build();
        }
        message = messageSource.getMessage("error", new String[]{} , locale);
        return ResponseDto.<Boolean>builder()
                .code(-1)
                .responseData(false)
                .success(false)
                .message(message)
                .build();
    }

    @Override
    public ResponseDto<List<CashbackHistoryDto>> getCashbackHistoryByCardId(Integer cashbackCardId, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;
        if(cashbackCardId != null) {
            List<CashbackHistoryDto> list = cashbackHistoryRepository.findAllByCardId(cashbackCardId)
                    .stream().map(cashbackHistoryMapper::toDto).toList();
            message = messageSource.getMessage("get.success", new String[]{} , locale);

            return ResponseDto.<List<CashbackHistoryDto>>builder()
                        .code(200)
                        .responseData(list)
                        .success(true)
                        .message(message)
                        .build();
        }
        message = messageSource.getMessage("error", new String[]{} , locale);

        return ResponseDto.<List<CashbackHistoryDto>>builder()
                .code(-1)
                .success(false)
                .message(message)
                .build();
    }

    @Override
    public ResponseDto<List<CashbackHistoryDto>> getCashbackHistoryBetween(Integer cardId, Date date, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;
        if(date != null) {
            List<CashbackHistoryDto> list = cashbackHistoryRepository.
                    findAllByCardIdAndTransactionDateBetween(cardId, date, new Date())
                    .stream().map(cashbackHistoryMapper::toDto).toList();
            message = messageSource.getMessage("get.success", new String[]{} , locale);

            return ResponseDto.<List<CashbackHistoryDto>>builder()
                    .code(200)
                    .responseData(list)
                    .success(true)
                    .message(message)
                    .build();
        }
        message = messageSource.getMessage("error", new String[]{} , locale);

        return ResponseDto.<List<CashbackHistoryDto>>builder()
                .code(-1)
                .success(false)
                .message(message)
                .build();
    }
}
