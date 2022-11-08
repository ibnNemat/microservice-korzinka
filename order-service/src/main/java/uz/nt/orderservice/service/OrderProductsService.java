package uz.nt.orderservice.service;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.OrderProductsDto;
import shared.libs.dto.OrderedProductsDetail;
import org.springframework.data.domain.Page;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public interface OrderProductsService {
    ResponseDto addOrderProducts(Integer orderId, List<OrderedProductsDetail> list);
    ResponseDto<OrderProductsDto> getById(Integer id);
    ResponseDto<Page<OrderProductsDto>> getAllOrderProductsByPage(Integer page, Integer size);
    ResponseDto updateOrderProducts(OrderProductsDto orderProductsDto);
    ResponseDto deleteById(Integer id);
    ResponseDto deleteByOrderId(Integer orderId);
    ResponseDto deleteByProductIdAndOrderId(Integer orderId, Integer productId);

    ResponseDto<HashMap<Integer, Double>> quantityOrderedProductsPerMonth();
    ResponseDto<HashMap<Integer, Double>> quantityOrderedProductsPerQuarter();
    List<OrderedProductsDetail> getOrderedProductsToPayFor(Integer orderId);
    ResponseDto<Page<OrderProductsDto>> responseDtoWithLink(Integer page, Integer size, Method method, ResponseDto<Page<OrderProductsDto>> responseDto);
}
