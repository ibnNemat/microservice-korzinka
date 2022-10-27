package uz.nt.cashbackservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.CashbackDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.UserDto;
import uz.nt.cashbackservice.entity.CashbackCard;
import uz.nt.cashbackservice.feign.UserClient;
import uz.nt.cashbackservice.mapper.CashbackCardMapper;
import uz.nt.cashbackservice.repository.CashbackCardRepository;
import uz.nt.cashbackservice.service.Main.CashbackCardService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CashbackCardServiceImpl implements CashbackCardService {

    private final CashbackCardRepository cashbackCardRepository;
    private final CashbackCardMapper cashbackMapper;
    private final UserClient userClient;


    @Override
    public ResponseDto<CashbackCard> getCashbackById(Integer cashbackId) {
        Optional<CashbackCard> cashbackCard = cashbackCardRepository.findById(cashbackId);
        if (cashbackCard.isPresent()){
            return ResponseDto.<CashbackCard>builder()
                    .code(200)
                    .success(true)
                    .message("ok")
                    .responseData(cashbackCard.get())
                    .build();
        }
        return ResponseDto.<CashbackCard>builder()
                .code(-1)
                .success(false)
                .message("ok")
                .build();
    }

    @Override
    public ResponseDto<Boolean> subtractUserCashback(Integer userId, Double amount) {
        Integer cardId = userClient.getCashbackCardId(userId).getResponseData();
        Optional<CashbackCard> cashbackCard = cashbackCardRepository.findById(cardId);
        if (cashbackCard.isPresent()){
            CashbackCard card = cashbackCard.get();
            card.setAmount(card.getAmount() - amount);

            return ResponseDto.<Boolean>builder()
                    .code(200)
                    .responseData(true)
                    .success(true)
                    .message("ok")
                    .build();

        }
        return ResponseDto.<Boolean>builder()
                .code(-1)
                .success(false)
                .message("ok")
                .build();
    }

    @Override
    public ResponseDto<Boolean>  increaseCashbackForUser(Integer userId, Double totalPrice) {
        Integer cardId = userClient.getCashbackCardId(userId).getResponseData();
        Optional<CashbackCard> cashbackCard = cashbackCardRepository.findById(cardId);
        if (cashbackCard.isPresent()){

            CashbackCard card = cashbackCard.get();
            card.setAmount(card.getAmount() + totalPrice / 100);

            return ResponseDto.<Boolean>builder()
                    .code(200)
                    .responseData(true)
                    .success(true)
                    .message("ok")
                    .build();

        }
        return ResponseDto.<Boolean>builder()
                .code(-1)
                .success(false)
                .message("ok")
                .build();
    }

    @Override
    @Transactional
    public ResponseDto<CashbackCardDto> add() {
        int userId;
        if (SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal() instanceof UserDto userDto){
            userId = userDto.getId();

            CashbackCard cashback = CashbackCard.builder().amount(5000D).barcode(RandomStringUtils.random(16)).userId(userId).build();

            cashbackCardRepository.save(cashback);

            CashbackCardDto cashbackCardDto = cashbackMapper.toDto(cashback);

            return ResponseDto.<CashbackCardDto>builder()
                    .success(true)
                    .message("OK").
                    responseData(cashbackCardDto).
                    build();
        } else {
            return ResponseDto.<CashbackCardDto>builder()
                    .success(false)
                    .message("NO")
                    .build();
        }

    }




    @Override
    public ResponseDto<Boolean> deleteCashBack(Integer cashbackCardId) {
        return ResponseDto.<Boolean>builder()
                .code(200)
                .responseData(true)
                .success(true)
                .message("ok")
                .build();
    }


    @Override
    public void increaseCashbackForMoreBought(Integer cardId, Double amount) {
        amount = amount / 100;
        CashbackCard card = getCashbackById(cardId).getResponseData();
        card.setAmount(card.getAmount() + amount);

        cashbackCardRepository.save(card);
    }


}


