package uz.nt.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.nt.orderservice.dto.OrderProductsDto;
import uz.nt.orderservice.dto.ResponseDto;
import uz.nt.orderservice.repository.OrderProductsRepository;
import uz.nt.orderservice.service.OrderProductsService;
import uz.nt.orderservice.service.mapper.OrderProductsMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProductsServiceImpl implements OrderProductsService {
    private final OrderProductsRepository productsRepository;
    private final OrderProductsMapper orderProductsMapper;
    @Override
    public ResponseDto addOrderProducts(Integer order_id, Integer product_id, Integer amount) {
        return null;
    }

    @Override
    public ResponseDto<OrderProductsDto> getById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto<Page<OrderProductsDto>> getAllOrderProducts() {
        return null;
    }

    @Override
    public ResponseDto updateOrderProducts(OrderProductsDto orderProductsDto) {
        return null;
    }

    @Override
    public ResponseDto deleteById(Integer id) {
        return null;
    }
}
