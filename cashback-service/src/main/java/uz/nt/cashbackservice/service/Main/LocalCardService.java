package uz.nt.cashbackservice.service.Main;

import shared.libs.dto.LocalCardDto;
import shared.libs.dto.ResponseDto;
import java.util.List;

public interface LocalCardService {

    ResponseDto<LocalCardDto> addLocalCard(LocalCardDto cashCardDto);

    ResponseDto<LocalCardDto> deleteLocalCardDto(LocalCardDto cashCardDto);

    ResponseDto<LocalCardDto> getLocalCardDtoById(Integer id);

    ResponseDto<LocalCardDto> updateLocalCardDto(LocalCardDto cashCardDto);

    ResponseDto<List<LocalCardDto>> getAllLocalCardDto();

}

