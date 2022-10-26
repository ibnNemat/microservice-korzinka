package uz.nt.cashbackservice.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.ResponseDto;
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
    public ResponseDto<CashbackCardDto> addCashback(CashbackCardDto cashbackDto) {
        return null;
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


