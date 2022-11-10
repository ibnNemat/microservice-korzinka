package uz.nt.userservice.service;

import shared.libs.dto.ResponseDto;
import shared.libs.dto.CardDto;

import java.util.List;

public interface CardService {
    ResponseDto<List<CardDto>> getAllCards();
    ResponseDto<List<CardDto>> getCardsByUserId(Integer user_id);
    ResponseDto<CardDto> getCardById(Integer card_id);

    ResponseDto deleteCardById(Integer id);

    ResponseDto updateCard(CardDto cardDto);

    ResponseDto addCard(CardDto cardDto);
}
