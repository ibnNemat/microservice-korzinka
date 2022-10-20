package uz.nt.productservice.service.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.ProductTypeDto;
import uz.nt.productservice.entity.ProductType;

@Mapper(componentModel = "spring")
public interface ProductTypeMapper {

    ProductType toEntity(ProductTypeDto dto);

    ProductTypeDto toDto(ProductType entity);
}
