package uz.nt.userservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import shared.libs.dto.CardTypeDto;
import shared.libs.dto.ResponseDto;
import uz.nt.userservice.service.CardTypeService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardTypeServiceImpl implements CardTypeService {

    @Override
    public ResponseDto<List<CardTypeDto>> getAllCardTypes() {
        return null;
    }

    @Override
    public ResponseDto<CardTypeDto> getCardTypeById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto deleteById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto update(CardTypeDto cardTypeDto) {
        return null;
    }

    @Override
    public ResponseDto add(CardTypeDto cardTypeDto) {
        return null;
    }
}
