package uz.nt.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.service.CashbackService;

@RestController
@RequestMapping("/cashback")
@RequiredArgsConstructor
public class CashbackController {

    private final CashbackService cashbackService;

//    @GetMapping("/cashbackId")
//    public ResponseDto<Integer> getCashbackCardId(@RequestParam Integer id){
//        return cashbackService.getCashbackCardId(id);
//    }
}
