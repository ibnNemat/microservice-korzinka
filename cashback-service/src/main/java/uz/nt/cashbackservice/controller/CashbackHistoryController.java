package uz.nt.cashbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shared.libs.dto.CashbackHistoryDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.service.Main.CashbackHistoryService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class CashbackHistoryController {

    private final CashbackHistoryService cashbackHistoryService;

    @GetMapping("/get-by-card-id")
    public ResponseDto<List<CashbackHistoryDto>> getCashbackHistoryByCardId(@RequestParam Integer cardId, HttpServletRequest request){
        return cashbackHistoryService.getCashbackHistoryByCardId(cardId, request);
    }

    @GetMapping("/get-between")
    public ResponseDto<List<CashbackHistoryDto>> getCashbackHistoryBetween(@RequestParam Date date, HttpServletRequest request){
        return cashbackHistoryService.getCashbackHistoryBetween(date, request);
    }
}
