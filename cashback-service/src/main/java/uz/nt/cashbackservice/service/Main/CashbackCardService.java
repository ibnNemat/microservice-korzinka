package uz.nt.cashbackservice.service.Main;


import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.ResponseDto;

import javax.servlet.http.HttpServletRequest;

public interface CashbackCardService {

    ResponseDto<CashbackCardDto> getCashbackById(Integer cashbackId, HttpServletRequest request);

    ResponseDto<Boolean>  subtractUserCashback(Integer userId, Double cashback, HttpServletRequest request);

    ResponseDto<Boolean>  increaseCashbackForUser(Integer userId, Double totalPrice, HttpServletRequest request);


    ResponseDto<CashbackCardDto> addCashback();

    ResponseDto<Boolean> deleteCashBackCardById(Integer cashbackCardId, HttpServletRequest request);

    ResponseDto<CashbackCardDto> getCashbackCardByUserId(Integer userId, HttpServletRequest request);

    ResponseDto<Boolean> deleteCashbackCardIdByUserId(Integer userId, HttpServletRequest request);

    void increaseCashbackForMoreBought(Integer cardId, Double amount);

}
