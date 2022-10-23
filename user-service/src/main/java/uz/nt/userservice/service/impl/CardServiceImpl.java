package uz.nt.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shared.libs.dto.CardDto;
import shared.libs.dto.CardTypeDto;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.service.CardService;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
    @Override
    public ResponseDto<List<CardDto>> getAllCards() {
        return null;
    }

    @Override
    public ResponseDto<List<CardDto>> getCardsByUserId(Integer user_id) {
        return null;
    }

    @Override
    public ResponseDto<CardDto> getCardById(Integer card_id) {
        return null;
    }

    @Override
    public ResponseDto deleteCardById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto updateCard(CardDto cardDto) {
        return null;
    }

    @Override
    public ResponseDto addCard(CardDto cardDto) {
        return null;
    }
}
