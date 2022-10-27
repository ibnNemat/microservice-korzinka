package uz.nt.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.service.DiscountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discount")
public class DiscountController {

    private final DiscountService service;

    @GetMapping
    public void pagination(){
        service.pagination(null, null);
    }
}
