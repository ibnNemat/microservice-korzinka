package uz.nt.orderservice.service;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.OrderProductsDto;
import uz.nt.orderservice.dto.OrderedProductsDetail;
import org.springframework.data.domain.Page;

import java.lang.reflect.Method;
import java.util.List;

public interface OrderProductsService {
    ResponseDto addOrderProducts(Integer order_id, Integer product_id, Double amount);
    ResponseDto<OrderProductsDto> getById(Integer id);
    ResponseDto<Page<OrderProductsDto>> getAllOrderProductsByPage(Integer page, Integer size);
    ResponseDto updateOrderProducts(OrderProductsDto orderProductsDto);
    ResponseDto deleteById(Integer id);
    ResponseDto deleteByOrderId(Integer orderId);
    ResponseDto deleteByProductIdAndOrderId(Integer orderId, Integer productId);

    List<OrderedProductsDetail> getOrderedProductsToPayFor(Integer order_id);
    ResponseDto<Page<OrderProductsDto>> responseDtoWithLink(Integer page, Integer size, Method method, ResponseDto<Page<OrderProductsDto>> responseDto);
}
