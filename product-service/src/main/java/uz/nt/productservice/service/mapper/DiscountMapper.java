package uz.nt.productservice.service.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.DiscountDto;
import uz.nt.productservice.entity.Discount;

@Mapper(componentModel = "spring")
public interface DiscountMapper {

    Discount toEntity(DiscountDto discountDto);

    DiscountDto toDto(Discount discount);
}
