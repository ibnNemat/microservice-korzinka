package uz.nt.orderservice.service.mapper;

import uz.nt.orderservice.dto.OrderProductsDto;
import uz.nt.orderservice.entity.OrderProducts;
import org.mapstruct.Mapper;
import shared.libs.dto.OrderProductDto;

@Mapper(componentModel = "spring")
public interface OrderProductsMapper {
    OrderProducts toEntity(OrderProductsDto orderProductsDto);
    OrderProductDto toDto(OrderProducts order);
}
