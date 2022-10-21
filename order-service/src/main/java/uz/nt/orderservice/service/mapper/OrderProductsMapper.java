package uz.nt.orderservice.service.mapper;

import uz.nt.orderservice.dto.OrderProductsDto;
import uz.nt.orderservice.entity.OrderProducts;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderProductsMapper {
    OrderProducts toEntity(OrderProductsDto orderProductsDto);
    OrderProductsDto toDto(OrderProducts order);
}
