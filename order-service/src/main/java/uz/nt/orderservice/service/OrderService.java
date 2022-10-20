package uz.nt.orderservice.service;

import uz.nt.orderservice.dto.OrderDto;
import uz.nt.orderservice.dto.ResponseDto;
import org.springframework.data.domain.Page;

public interface OrderService {
    ResponseDto addOrderIfNotExistUserOrders(Integer product_id, Integer amount);
    ResponseDto<OrderDto> getById(Integer id);
    ResponseDto<Page<OrderDto>> getAllOrders();
    ResponseDto updateOrderPayed(Integer user_id);
    ResponseDto updateOrder(OrderDto orderDto);
    ResponseDto deleteById(Integer id);
}
