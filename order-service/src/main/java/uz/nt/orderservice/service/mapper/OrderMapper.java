package uz.nt.orderservice.service.mapper;
import uz.nt.orderservice.dto.OrderDto;
import uz.nt.orderservice.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderDto orderDto);
    OrderDto toDto(Order order);
}
