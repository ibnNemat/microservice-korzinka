package uz.nt.userservice.service.mapper;

import org.mapstruct.Mapper;
import uz.nt.userservice.dto.CardDto;
import uz.nt.userservice.entity.Card;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card toEntity(CardDto cardDto);
    CardDto toDto(Card card);
}
