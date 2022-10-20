package uz.nt.productservice.service.mapper;

import org.mapstruct.Mapper;
import shared.libs.dto.ProductDto;
import uz.nt.productservice.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductDto productDto);

    ProductDto toDto(Product product);
}
