package uz.nt.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.DiscountDto;
import shared.libs.dto.ResponseDto;
import uz.nt.productservice.service.DiscountService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discount")
public class DiscountController {

    private final DiscountService service;

    @GetMapping("/pagination")
    public ResponseDto<Page<DiscountDto>> pagination(@RequestParam(required = false) Integer page,
                                                     @RequestParam(required = false) Integer size){
        return service.pagination(page, size);
    }

    @PostMapping
    public ResponseDto<DiscountDto> add(@RequestBody DiscountDto discountDto){
        return service.add(discountDto);
    }

    @PutMapping
    public ResponseDto<DiscountDto> update(@RequestBody DiscountDto discountDto){
        return service.update(discountDto);
    }
}
