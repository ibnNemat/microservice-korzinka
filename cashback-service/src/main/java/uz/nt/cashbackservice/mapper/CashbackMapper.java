package uz.nt.cashbackservice.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.CashbackDto;
import uz.nt.cashbackservice.Entity.Cashback;

@Mapper(componentModel = "spring")
public interface CashbackMapper {

    CashbackDto toDto(Cashback cashback);
    Cashback toEntity(CashbackDto cashbackDto);

}
