package uz.nt.orderservice.service;

import org.springframework.data.domain.Page;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.OrderDto;
import uz.nt.orderservice.dto.PaymentDetails;
import uz.nt.orderservice.dto.UserOrderedProducts;

import java.lang.reflect.Method;
import java.util.List;

public interface OrderService {
    ResponseDto<OrderDto> addOrderIfNotExistUserOrders(Integer product_id, Double amount);
    ResponseDto<OrderDto> getById(Integer id);
    ResponseDto<Page<OrderDto>> getAllOrdersByPage(Integer page, Integer size);
    ResponseDto<Page<OrderDto>> responseDtoWithLink(Integer page, Integer size,
                                                    Method method, ResponseDto<Page<OrderDto>> responseDto);
    ResponseDto<OrderDto> updateOrder(OrderDto orderDto);
    ResponseDto<OrderDto> deleteById(Integer id);
    Boolean updateOrderTotalPrice(Integer order_id, Double total_price);

    ResponseDto<OrderDto> payForOrders(PaymentDetails paymentDetails);
    ResponseDto<List<UserOrderedProducts>> getAllUsersOrderProductsIsPayedFalse();
}
