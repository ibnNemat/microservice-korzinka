package uz.nt.orderservice.service;

import org.springframework.hateoas.Link;
import uz.nt.orderservice.dto.OrderDto;
import uz.nt.orderservice.dto.ResponseDto;
import org.springframework.data.domain.Page;

import java.lang.reflect.Method;

public interface OrderService {
    ResponseDto addOrderIfNotExistUserOrders(Integer product_id, Integer amount);
    ResponseDto<OrderDto> getById(Integer id);
    ResponseDto<Page<OrderDto>> getAllOrdersByPage(Integer page, Integer size);
    ResponseDto<Page<OrderDto>> responseDtoWithLink(Integer page, Integer size,
                                                    Method method, ResponseDto<Page<OrderDto>> responseDto);
    ResponseDto updateOrderPayed(Integer user_id);
    ResponseDto updateOrder(OrderDto orderDto);
    ResponseDto deleteById(Integer id);
}
