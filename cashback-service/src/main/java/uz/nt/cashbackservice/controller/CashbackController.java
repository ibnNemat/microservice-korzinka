package uz.nt.cashbackservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.CashbackDto;
import shared.libs.dto.ResponseDto;
import uz.nt.cashbackservice.service.Main.CashbackService;

@RestController
@RequestMapping("/cashback")
@RequiredArgsConstructor
public class CashbackController {

    private final CashbackService cashbackService;

    @PostMapping
    public ResponseDto<CashbackDto> add(){
        return cashbackService.add();
    }

}
