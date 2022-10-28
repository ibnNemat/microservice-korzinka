package uz.nt.cashbackservice.service.Main;


import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.entity.CashbackCard;

public interface CashbackCardService {

    ResponseDto<CashbackCard> getCashbackById(Integer cashbackId);

    ResponseDto<Boolean>  subtractUserCashback(Integer userId, Double cashback);

    ResponseDto<Boolean>  increaseCashbackForUser(Integer userId, Double totalPrice);


    ResponseDto<CashbackCardDto> addCashback(CashbackCardDto cashbackDto);

    ResponseDto<Boolean> deleteCashBack(Integer cashbackCardId);

    void increaseCashbackForMoreBought(Integer cardId, Double amount);

}
