package uz.nt.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.dto.CardTypeDto;
import uz.nt.userservice.service.CardTypeService;
import uz.nt.userservice.service.impl.CardTypeServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("card-type")
public class CardTypeController implements CardTypeService {
    private final CardTypeServiceImpl cardTypeService;


    @GetMapping
    public ResponseDto<List<CardTypeDto>> getAllCardTypes() {
        return cardTypeService.getAllCardTypes();
    }

    @GetMapping("by-id")
    public ResponseDto<CardTypeDto> getCardTypeById(Integer id) {
        return cardTypeService.getCardTypeById(id);
    }

    @DeleteMapping
    public ResponseDto deleteById(Integer id) {
        return cardTypeService.deleteById(id);
    }

    @PatchMapping
    public ResponseDto update(CardTypeDto cardTypeDto) {
        return cardTypeService.update(cardTypeDto);
    }

    @PostMapping
    public ResponseDto add(CardTypeDto cardTypeDto) {
        return cardTypeService.getAllCardTypes();
    }
}
