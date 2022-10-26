package uz.nt.productservice.service.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.UnitDto;
import uz.nt.productservice.entity.Unit;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    Unit toEntity(UnitDto unitDto);

    UnitDto toDto(Unit unit);
}
