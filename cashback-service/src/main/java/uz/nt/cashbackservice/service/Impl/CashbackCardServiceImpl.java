package uz.nt.cashbackservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.CashbackHistoryDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.entity.CashbackCard;
import uz.nt.cashbackservice.entity.CashbackHistory;
import uz.nt.cashbackservice.mapper.CashbackCardMapper;
import uz.nt.cashbackservice.repository.CashbackCardRepository;
import uz.nt.cashbackservice.service.Main.CashbackCardService;
import uz.nt.cashbackservice.service.Main.CashbackHistoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashbackCardServiceImpl implements CashbackCardService {

    private final CashbackCardRepository cashbackCardRepository;
    private final CashbackCardMapper cashbackMapper;
    private final MessageSource messageSource;
    private final CashbackHistoryService cashbackHistoryService;


    @Override
    public ResponseDto<CashbackCardDto> getCashbackById(Integer cashbackId, HttpServletRequest request) {
        Optional<CashbackCard> cashbackCard = cashbackCardRepository.findById(cashbackId);
        Locale locale = request.getLocale();
        String message;
        if (cashbackCard.isPresent()){
            CashbackCardDto cashbackCardDto = cashbackMapper.toDto(cashbackCard.get());
            message = messageSource.getMessage("get.success", new String[]{} , locale);
            return ResponseDto.<CashbackCardDto>builder()
                    .code(200)
                    .success(true)
                    .message(message)
                    .responseData(cashbackCardDto)
                    .build();
        }
        message = messageSource.getMessage("not.found", new String[]{} , locale);

        return ResponseDto.<CashbackCardDto>builder()
                .code(-1)
                .success(false)
                .message(message)
                .build();
    }


    @Override
    public ResponseDto<CashbackCardDto> getCashbackCardByUserId(Integer userId, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;
        if(userId != null) {
            CashbackCard cashbackCard = cashbackCardRepository.findCashbackCardByUserId(userId);
            if (cashbackCard != null) {
                CashbackCardDto cashbackCardDto = cashbackMapper.toDto(cashbackCard);
                message = messageSource.getMessage("get.success", new String[]{} , locale);
                return ResponseDto.<CashbackCardDto>builder()
                        .code(200)
                        .success(true)
                        .message(message)
                        .responseData(cashbackCardDto)
                        .build();
            }
        }
        message = messageSource.getMessage("not.found", new String[]{}, locale);
            return ResponseDto.<CashbackCardDto>builder()
                    .code(-1)
                    .success(false)
                    .message(message)
                    .build();

    }



    @Transactional
    @Override
    public ResponseDto<Boolean> subtractUserCashback(Integer userId, Double amount, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;
        if(userId != null && amount != null) {

            CashbackCard card = cashbackCardRepository.findCashbackCardByUserId(userId);

            if (Optional.ofNullable(card).isPresent()) {
                message = messageSource.getMessage("operation.success", new String[]{}, locale);
                Double updatedAmount = card.getAmount() - amount;
                card.setAmount(updatedAmount);

                addCashbackHistory(card.getId(), card.getAmount() - amount, amount, card.getAmount(), new Date());

                return ResponseDto.<Boolean>builder()
                        .code(200)
                        .responseData(true)
                        .success(true)
                        .message(message)
                        .build();

            }
        }
        message = messageSource.getMessage("error", new String[]{}, locale);
        return ResponseDto.<Boolean>builder()
                .code(-1)
                .success(false)
                .message(message)
                .build();
    }


    @Transactional
    @Override
    public ResponseDto<Boolean> increaseCashbackForUser(Integer userId, Double totalPrice, HttpServletRequest request) {
        CashbackCard card = cashbackCardRepository.findCashbackCardByUserId(userId);
        Locale locale = request.getLocale();
        String message;

        if (Optional.ofNullable(card).isPresent()){
            Double updatedAmount = card.getAmount() + (totalPrice / 100);
            card.setAmount(updatedAmount);

            addCashbackHistory(card.getId(), card.getAmount() - totalPrice / 100, totalPrice / 100, card.getAmount(), new Date());
            message = messageSource.getMessage("operation.success", new String[]{}, locale);
            return ResponseDto.<Boolean>builder()
                    .code(200)
                    .responseData(true)
                    .success(true)
                    .message(message)
                    .build();

        }

        message = messageSource.getMessage("error", new String[]{}, locale);
        return ResponseDto.<Boolean>builder()
                .code(-1)
                .success(false)
                .message(message)
                .build();
    }


    @Override
    public ResponseDto<CashbackCardDto> addCashback() {
        return null;
    }


    @Override
    public ResponseDto<Boolean> deleteCashBackCardById(Integer cashbackCardId, HttpServletRequest request) {

        Locale locale = request.getLocale();
        String message;

        if(cashbackCardId != null) {

            cashbackCardRepository.deleteById(cashbackCardId);
            message = messageSource.getMessage("delete.success", new String[]{}, locale);
            return ResponseDto.<Boolean>builder()
                    .code(200)
                    .responseData(true)
                    .success(true)
                    .message(message)
                    .build();
        }
        message = messageSource.getMessage("error", new String[]{}, locale);
        return ResponseDto.<Boolean>builder()
                .code(-1)
                .success(false)
                .message(message)
                .build();
    }


    @Override
    public ResponseDto<Boolean> deleteCashbackCardIdByUserId(Integer userId, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;
        if(userId != null) {
            Boolean result = cashbackCardRepository.deleteCashbackCardByUserId(userId);

            message = messageSource.getMessage("delete.success", new String[]{}, locale);
            return ResponseDto.<Boolean>builder()
                    .code(200)
                    .responseData(result)
                    .success(true)
                    .message(message)
                    .build();
        }

        message = messageSource.getMessage("error", new String[]{}, locale);
        return ResponseDto.<Boolean>builder()
                .code(-1)
                .success(false)
                .message(message)
                .build();
    }


    @Transactional
    @Override
    public void increaseCashbackForMoreBought(Integer userId, Double amount) {
        CashbackCard card = cashbackCardRepository.findCashbackCardByUserId(userId);
        if(Optional.ofNullable(card).isPresent()){
            Double updatedAmount = (amount / 100) + card.getAmount();
            card.setAmount(updatedAmount);
            cashbackCardRepository.save(card);
        }
    }


    private void addCashbackHistory(Integer cardId, Double before, Double amount, Double after, Date date){
        CashbackHistory history = CashbackHistory.builder()
                                        .cardId(cardId)
                                        .beforeTransaction(before)
                                        .transactionAmount(amount)
                                        .afterTransaction(after)
                                        .transactionDate(date)
                                        .build();
        cashbackHistoryService.addCashbackHistory(history);
    }

}


