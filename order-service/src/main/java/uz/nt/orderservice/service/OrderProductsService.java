package uz.nt.orderservice.service;

import uz.nt.orderservice.dto.OrderProductsDto;
import uz.nt.orderservice.dto.ResponseDto;
import org.springframework.data.domain.Page;

public interface OrderProductsService {
    ResponseDto addOrderProducts(Integer order_id, Integer product_id, Integer amount);
    ResponseDto<OrderProductsDto> getById(Integer id);
    ResponseDto<Page<OrderProductsDto>> getAllOrderProducts();
    ResponseDto updateOrderProducts(OrderProductsDto orderProductsDto);
    ResponseDto deleteById(Integer id);
}
