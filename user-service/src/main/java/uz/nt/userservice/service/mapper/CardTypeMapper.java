package uz.nt.userservice.service.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.CardDto;
import shared.libs.dto.CardTypeDto;
import uz.nt.userservice.entity.Card;
import uz.nt.userservice.entity.CardType;

@Mapper(componentModel = "spring")
public interface CardTypeMapper {
    CardType toEntity(CardDto cardDto);
    CardTypeDto toDto(Card card);
}
