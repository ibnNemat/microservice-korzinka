package uz.nt.userservice.service.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.CardTypeDto;
import uz.nt.userservice.entity.CardType;

@Mapper(componentModel = "spring")
public interface CardTypeMapper {
    CardType toEntity(CardTypeDto cardTypeDto);
    CardTypeDto toDto(CardType cardType);
}
