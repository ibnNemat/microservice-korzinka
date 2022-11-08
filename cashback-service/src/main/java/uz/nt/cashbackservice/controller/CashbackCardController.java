package uz.nt.cashbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.CashbackCardDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.service.Main.CashbackCardService;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cashback")
@RequiredArgsConstructor
public class CashbackCardController {

    private final CashbackCardService cashbackCardService;

    @PostMapping
    public ResponseDto<CashbackCardDto> addCashback(HttpServletRequest request){
        return cashbackCardService.addCashback(request);
    }

    @GetMapping("/get-by-id")
    public ResponseDto<CashbackCardDto> getCashbackCardById(@RequestParam Integer cardId, HttpServletRequest request){
        return cashbackCardService.getCashbackById(cardId, request);
    }

    @GetMapping("/get-by-userid")
    public ResponseDto<CashbackCardDto> getCashbackCardByUserId(@RequestParam Integer userId, HttpServletRequest request){
        return cashbackCardService.getCashbackCardByUserId(userId, request);
    }

    @PutMapping("/calculate-cashback")
    public ResponseDto<CashbackCardDto> increase(@RequestParam Integer userId, @RequestParam Double cashbackAmount, HttpServletRequest request){
        return cashbackCardService.increaseCashbackForUser(userId, cashbackAmount, request);
    }

    @PutMapping("/subtract")
    public ResponseDto<CashbackCardDto> subtractCashback(@RequestParam Integer userId, @RequestParam Double cashbackAmount, HttpServletRequest request){
        return cashbackCardService.subtractUserCashback(userId, cashbackAmount, request);
    }

    @DeleteMapping("del-by-id")
    public ResponseDto<Boolean> deleteCashbackCardById(@RequestParam Integer cardId, HttpServletRequest request){
        return cashbackCardService.deleteCashBackCardById(cardId, request);
    }

    @DeleteMapping("del-by-userid")
    public ResponseDto<Boolean> deleteCashbackCardByUserId(@RequestParam Integer userId, HttpServletRequest request){
        return cashbackCardService.deleteCashbackCardIdByUserId(userId, request);
    }

}
