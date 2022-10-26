package uz.nt.cashbackservice.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.CashbackHistoryDto;
import uz.nt.cashbackservice.entity.CashbackHistory;

@Mapper(componentModel = "spring")
public interface CashbackHistoryMapper {

    CashbackHistory toEntity(CashbackHistoryDto dto);

    CashbackHistoryDto toDto(CashbackHistory entity);

}
