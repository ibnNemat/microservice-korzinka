package uz.nt.userservice.service.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.CardDto;
import uz.nt.userservice.entity.Card;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toEntity(CardDto cardDto);
    CardDto toDto(Card card);
}
