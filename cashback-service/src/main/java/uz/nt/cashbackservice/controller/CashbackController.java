package uz.nt.cashbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.CashbackDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.Entity.Cashback;
import uz.nt.cashbackservice.service.Main.CashbackService;

@RestController
@RequestMapping("/cashback")
@RequiredArgsConstructor
public class CashbackController {

    private final CashbackService cashbackService;

    @PutMapping("/subtract")
    public void subtractCashback(@RequestParam Integer userId, @RequestParam Double cashbackAmount){
        cashbackService.subtractUserCashback(userId, cashbackAmount);
    }

    @GetMapping("/get-cashback")
    public ResponseDto<CashbackDto> getCashBack(@RequestParam Integer userId){
        return cashbackService.getCashbackByUserId(userId);
    }

    @PutMapping("/change-percent")
    public void changePercent(@RequestParam Integer userId, @RequestParam Double totalPrice){
        cashbackService.calculateCashbackForUser(userId, totalPrice);
    }

    @PostMapping("add-cashback")
    public ResponseDto<CashbackDto> addCashback(@RequestBody CashbackDto cashbackDto){
        return cashbackService.addCashback(cashbackDto);
    }

}
