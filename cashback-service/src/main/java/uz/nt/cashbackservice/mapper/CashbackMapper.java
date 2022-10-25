package uz.nt.cashbackservice.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.CashbackCardDto;
import uz.nt.cashbackservice.entity.CashbackCard;

@Mapper(componentModel = "spring")
public interface CashbackMapper {

    CashbackCardDto toDto(CashbackCard cashback);
    CashbackCard toEntity(CashbackCardDto cashbackDto);

}
