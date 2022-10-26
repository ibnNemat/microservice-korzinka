package uz.nt.orderservice.service.mapper;
import uz.nt.orderservice.dto.OrderDto;
import uz.nt.orderservice.entity.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Orders toEntity(OrderDto orderDto);
    OrderDto toDto(Orders orders);
}
