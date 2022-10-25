package uz.nt.cashbackservice.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.CashbackDto;
import uz.nt.cashbackservice.entity.Cashback;

@Mapper(componentModel = "spring")
public interface CashbackMapper {

    CashbackDto toDto(Cashback cashback);
    Cashback toEntity(CashbackDto cashbackDto);

}
