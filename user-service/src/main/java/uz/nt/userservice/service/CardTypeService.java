package uz.nt.userservice.service;
import shared.libs.dto.CardTypeDto;
import shared.libs.dto.ResponseDto;

import java.util.List;

public interface CardTypeService {
    ResponseDto<List<CardTypeDto>> getAllCardTypes();

    ResponseDto<CardTypeDto> getCardTypeById(Integer id);


    ResponseDto deleteById(Integer id);

    ResponseDto update(CardTypeDto cardTypeDto);

    ResponseDto add(CardTypeDto cardTypeDto);
}
