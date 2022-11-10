package uz.nt.cashbackservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;
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

    private Long barcode;

    private Long getBarcode(){
        if(barcode == null){
            barcode = cashbackCardRepository.getMaxBarcode();
            return (barcode != null) ? ++barcode : 2398389489L;
        }
        return ++barcode;
    }

    @Override
    public ResponseDto<CashbackCardDto> getCashbackById(Integer cashbackId, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;
        if(cashbackId != null) {
            Optional<CashbackCard> cashbackCard = cashbackCardRepository.findById(cashbackId);

            if (cashbackCard.isPresent()) {
                CashbackCardDto cashbackCardDto = cashbackMapper.toDto(cashbackCard.get());
                message = messageSource.getMessage("get.success", new String[]{}, locale);
                return ResponseDto.<CashbackCardDto>builder()
                        .code(200)
                        .success(true)
                        .message(message)
                        .responseData(cashbackCardDto)
                        .build();
            }
            message = messageSource.getMessage("not.found", new String[]{} , locale);

            return ResponseDto.<CashbackCardDto>builder()
                    .code(-2)
                    .success(false)
                    .message(message)
                    .build();
        }
        message = messageSource.getMessage("error", new String[]{} , locale);

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
            message = messageSource.getMessage("not.found", new String[]{}, locale);
            return ResponseDto.<CashbackCardDto>builder()
                    .code(-2)
                    .success(false)
                    .message(message)
                    .build();

        }
        message = messageSource.getMessage("error", new String[]{}, locale);
            return ResponseDto.<CashbackCardDto>builder()
                    .code(-1)
                    .success(false)
                    .message(message)
                    .build();

    }



    @Transactional
    @Override
    public ResponseDto<CashbackCardDto> subtractUserCashback(Integer userId, Double amount, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;

        if(userId != null && amount != null) {

            CashbackCard card = cashbackCardRepository.findCashbackCardByUserId(userId);

            if (Optional.ofNullable(card).isPresent()) {
                Double updatedAmount = card.getAmount() - amount;
                card.setAmount(updatedAmount);
                addCashbackHistory(card.getId(), card.getAmount() - amount, amount, card.getAmount(), new Date());
                message = messageSource.getMessage("operation.success", new String[]{}, locale);
                return ResponseDto.<CashbackCardDto>builder()
                        .code(200)
                        .responseData(cashbackMapper.toDto(card))
                        .success(true)
                        .message(message)
                        .build();

            }
            message = messageSource.getMessage("not.found", new String[]{}, locale);
            return ResponseDto.<CashbackCardDto>builder()
                    .code(-2)
                    .responseData(null)
                    .success(false)
                    .message(message)
                    .build();
        }
        message = messageSource.getMessage("error", new String[]{}, locale);
        return ResponseDto.<CashbackCardDto>builder()
                .code(-1)
                .responseData(null)
                .success(false)
                .message(message)
                .build();
    }


    @Transactional
    @Override
    public ResponseDto<CashbackCardDto> increaseCashbackForUser(Integer userId, Double totalPrice, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;
        if(userId != null && totalPrice != null) {
            CashbackCard card = cashbackCardRepository.findCashbackCardByUserId(userId);

            if (Optional.ofNullable(card).isPresent()) {
                Double updatedAmount = card.getAmount() + (totalPrice / 100);
                card.setAmount(updatedAmount);

                addCashbackHistory(card.getId(), card.getAmount() - totalPrice / 100, totalPrice / 100, card.getAmount(), new Date());
                message = messageSource.getMessage("operation.success", new String[]{}, locale);
                return ResponseDto.<CashbackCardDto>builder()
                        .code(200)
                        .responseData(cashbackMapper.toDto(card))
                        .success(true)
                        .message(message)
                        .build();
            }
            message = messageSource.getMessage("not.found", new String[]{}, locale);
            return ResponseDto.<CashbackCardDto>builder()
                    .code(-2)
                    .responseData(null)
                    .success(false)
                    .message(message)
                    .build();
        }

        message = messageSource.getMessage("error", new String[]{}, locale);
        return ResponseDto.<CashbackCardDto>builder()
                .code(-1)
                .responseData(null)
                .success(false)
                .message(message)
                .build();
    }


    @Override
    public ResponseDto<CashbackCardDto> addCashback(HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;
        CashbackCard card = null;
        try{
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDto userDto){
                Integer userId = userDto.getId();
                card = CashbackCard.builder().amount(5000D).userId(userId).barcode(getBarcode()).build();
                cashbackCardRepository.save(card);

                message = messageSource.getMessage("add.success", new String[]{}, locale);

                return ResponseDto.<CashbackCardDto>builder()
                        .success(true).code(200)
                        .message(message)
                        .responseData(cashbackMapper.toDto(card))
                        .build();
            }
        }catch (Exception e){
            message = messageSource.getMessage("error", new String[]{}, locale);
            return ResponseDto.<CashbackCardDto>builder()
                    .success(false).code(-1)
                    .message(message.concat(": ").concat(e.getMessage()))
                    .responseData(null)
                    .build();
        }
        message = messageSource.getMessage("operation.failed", new String[]{}, locale);
        return ResponseDto.<CashbackCardDto>builder()
                .success(false).code(-1)
                .message(message)
                .responseData(null)
                .build();
    }


    @Override
    public ResponseDto<Boolean> deleteCashBackCardById(Integer cashbackCardId, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;

        if(cashbackCardId != null) {
            if(cashbackCardRepository.existsById(cashbackCardId)) {
                cashbackCardRepository.deleteById(cashbackCardId);
                message = messageSource.getMessage("operation.success", new String[]{}, locale);
                return ResponseDto.<Boolean>builder()
                        .code(200)
                        .responseData(true)
                        .success(true)
                        .message(message)
                        .build();
            }
            message = messageSource.getMessage("not.found", new String[]{}, locale);
            return ResponseDto.<Boolean>builder()
                    .code(-2)
                    .responseData(false)
                    .success(false)
                    .message(message)
                    .build();
        }

        message = messageSource.getMessage("error", new String[]{}, locale);
        return ResponseDto.<Boolean>builder()
                .code(-1)
                .responseData(false)
                .success(false)
                .message(message)
                .build();
    }


    @Override
    public ResponseDto<Boolean> deleteCashbackCardIdByUserId(Integer userId, HttpServletRequest request) {
        Locale locale = request.getLocale();
        String message;

        if(userId != null) {
            if(cashbackCardRepository.existsByUserId(userId)) {
                cashbackCardRepository.deleteCashbackCardByUserId(userId);
                message = messageSource.getMessage("operation.success", new String[]{}, locale);
                return ResponseDto.<Boolean>builder()
                        .code(200)
                        .responseData(true)
                        .success(true)
                        .message(message)
                        .build();
            }
            message = messageSource.getMessage("not.found", new String[]{}, locale);
            return ResponseDto.<Boolean>builder()
                    .code(-2)
                    .responseData(false)
                    .success(false)
                    .message(message)
                    .build();
        }

        message = messageSource.getMessage("error", new String[]{}, locale);
        return ResponseDto.<Boolean>builder()
                .code(-1)
                .responseData(false)
                .success(false)
                .message(message)
                .build();
    }


    @Transactional
    @Override
    public void increaseCashbackForMoreBought(Integer userId, Double amount) {
        CashbackCard card = cashbackCardRepository.findCashbackCardByUserId(userId);
        if(Optional.ofNullable(card).isPresent()){
            Double money = (amount / 100) + card.getAmount();
            card.setAmount(money);
            cashbackCardRepository.save(card);
            addCashbackHistory(card.getId(), money - (amount / 100), amount / 100, money, new Date());
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


