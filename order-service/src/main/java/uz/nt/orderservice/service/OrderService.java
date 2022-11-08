package uz.nt.orderservice.service;

import org.springframework.data.domain.Page;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.OrderDto;
import shared.libs.dto.OrderedProductsDetail;
import uz.nt.orderservice.dto.PaymentDetails;
import uz.nt.orderservice.dto.UserOrderedProducts;

import java.lang.reflect.Method;
import java.util.List;

public interface OrderService {
    List<OrderedProductsDetail> checkProductAmount(List<OrderedProductsDetail> list);
    ResponseDto<List<OrderedProductsDetail>> addOrder(List<OrderedProductsDetail> list);
    ResponseDto<OrderDto> getById(Integer id);
    ResponseDto<Page<OrderDto>> getAllOrdersByPage(Integer page, Integer size);
    ResponseDto<Page<OrderDto>> responseDtoWithLink(Integer page, Integer size,
                                                    Method method, ResponseDto<Page<OrderDto>> responseDto);
    ResponseDto<OrderDto> updateOrder(OrderDto orderDto);
    ResponseDto<OrderDto> deleteById(Integer id);
    Boolean updateOrderTotalPrice(Integer orderId, Double totalPrice);

    ResponseDto<OrderDto> payForOrders(PaymentDetails paymentDetails);
    ResponseDto<List<UserOrderedProducts>> getAllUsersOrderProductsIsPayedFalse();
}
