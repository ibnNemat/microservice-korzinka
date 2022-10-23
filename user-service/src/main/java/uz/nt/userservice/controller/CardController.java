package uz.nt.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.CardDto;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.service.CardService;
import uz.nt.userservice.service.impl.CardServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardController implements CardService {
    private final CardServiceImpl cardService;


    @GetMapping("get-all-cards")
    public ResponseDto<List<CardDto>> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping()
    public ResponseDto<List<CardDto>> getCardsByUserId(Integer user_id) {
        return cardService.getCardsByUserId(user_id);
    }

    @GetMapping()
    public ResponseDto<CardDto> getCardById(Integer card_id) {
        return cardService.getCardById(card_id);
    }

    @DeleteMapping()
    public ResponseDto deleteCardById(Integer id) {
        return cardService.deleteCardById(id);
    }

    @PatchMapping()
    public ResponseDto updateCard(CardDto cardDto) {
        return cardService.updateCard(cardDto);
    }

    @PostMapping()
    public ResponseDto addCard(CardDto cardDto) {
        return cardService.addCard(cardDto);
    }
}
