package uz.nt.cashbackservice.service.Main;


import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.ResponseDto;
import javax.servlet.http.HttpServletRequest;

public interface CashbackCardService {

    ResponseDto<CashbackCardDto> addCashback(HttpServletRequest request);
    ResponseDto<CashbackCardDto> getCashbackById(Integer cashbackId, HttpServletRequest request);
    ResponseDto<CashbackCardDto> getCashbackCardByUserId(Integer userId, HttpServletRequest request);
    ResponseDto<CashbackCardDto>  subtractUserCashback(Integer userId, Double cashback, HttpServletRequest request);
    ResponseDto<CashbackCardDto>  increaseCashbackForUser(Integer userId, Double totalPrice, HttpServletRequest request);
    ResponseDto<Boolean> deleteCashBackCardById(Integer cashbackCardId, HttpServletRequest request);
    ResponseDto<Boolean> deleteCashbackCardIdByUserId(Integer userId, HttpServletRequest request);

    void increaseCashbackForMoreBought(Integer userId, Double amount);

}
