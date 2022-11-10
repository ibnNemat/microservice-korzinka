package uz.nt.cashbackservice.service.Main;


import shared.libs.dto.CashbackDto;
import shared.libs.dto.ResponseDto;

public interface CashbackService {

    void subtractUserCashback(Integer userId, Double cashback);

    void calculateCashbackForUser(Integer userId, Double totalPrice);

    ResponseDto<CashbackDto> getCashbackByUserId(Integer userId);

    ResponseDto<CashbackDto> addCashback(CashbackDto cashbackDto);
}
