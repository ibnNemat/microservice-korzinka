package uz.nt.userservice.service;
import shared.libs.dto.CardDto;
import shared.libs.dto.ResponseDto;
import shared.libs.dto.CardTypeDto;
import java.util.List;

public interface CardService {
    ResponseDto<List<CardDto>> getAllCards();

    ResponseDto<CardDto> getCardById(Integer id);

    ResponseDto deleteCardById(Integer id);

    ResponseDto updateCard(CardTypeDto cardTypeDto);

    ResponseDto addCard(CardDto cardDto);
}
