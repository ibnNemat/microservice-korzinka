package uz.nt.userservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.userservice.dto.CardDto;
import uz.nt.userservice.dto.CardTypeDto;
import uz.nt.userservice.entity.Card;
import uz.nt.userservice.entity.CardType;

@Mapper(componentModel = "spring")
public interface CardTypeMapper {
    CardType toEntity(CardDto cardDto);
    CardTypeDto toDto(Card card);
}
