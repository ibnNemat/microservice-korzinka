package uz.nt.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.CardDto;
import uz.nt.userservice.service.CardService;
import uz.nt.userservice.service.impl.CardServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cards")
public class CardController implements CardService {
    private final CardServiceImpl cardService;
    @GetMapping("/get-all-cards")
    public ResponseDto<List<CardDto>> getAllCards() {
        return cardService.getAllCards();
    }

    @GetMapping("by-user-id/{user_id}")
    public ResponseDto<List<CardDto>> getCardsByUserId(@PathVariable Integer user_id) {
        return cardService.getCardsByUserId(user_id);
    }

    @GetMapping("/by-id/{cardId}")
    public ResponseDto<CardDto> getCardById(@PathVariable Integer cardId) {
        return cardService.getCardById(cardId);
    }

    @DeleteMapping
    public ResponseDto deleteCardById(@PathVariable Integer id) {
        return cardService.deleteCardById(id);
    }

    @PutMapping
    public ResponseDto updateCard(@RequestBody CardDto cardDto) {
        return cardService.updateCard(cardDto);
    }

    @PostMapping()
    public ResponseDto addCard(@RequestBody CardDto cardDto) {
        return cardService.addCard(cardDto);
    }
}
