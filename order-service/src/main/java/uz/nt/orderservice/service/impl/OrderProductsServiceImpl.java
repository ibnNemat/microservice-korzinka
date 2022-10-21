package uz.nt.orderservice.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.OrderProductsDto;
import uz.nt.orderservice.dto.OrderedProductsDetail;
import uz.nt.orderservice.entity.OrderProducts;
import uz.nt.orderservice.repository.OrderProductsRepository;
import uz.nt.orderservice.service.OrderProductsService;
import uz.nt.orderservice.service.mapper.OrderProductsMapper;
import uz.nt.productservice.entity.Product;
import uz.nt.productservice.service.ProductService;

import java.lang.reflect.Method;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProductsServiceImpl implements OrderProductsService {
    private final OrderProductsRepository orderProductsRepository;
    private final OrderProductsMapper orderProductsMapper;
    private final ProductService productService;
    @Override
    public ResponseDto addOrderProducts(Integer order_id, Integer product_id, Integer amount) {
        Boolean isProductEnough = productService.updateAmount(product_id, amount);

        if(isProductEnough){
            Product product = new Product();
            product.setId(product_id);
            OrderProducts orderProduct = new OrderProducts(null, order_id, product, amount);
            orderProductsRepository.save(orderProduct);

            return ResponseDto.builder()
                    .code(200)
                    .success(true)
                    .message("Successfully saved")
                    .build();
        }

        return ResponseDto.builder()
                .code(-5)
                .success(false)
                .message("We don't have products in that many amounts!")
                .build();

    }

    @Override
    public ResponseDto<OrderProductsDto> getById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto<Page<OrderProductsDto>> getAllOrderProductsByPage(Integer page, Integer size) {
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

    @Override
    public List<OrderedProductsDetail> getOrderedProductsToPayFor(Integer order_id) {
        return null;
    }

    @Override
    public ResponseDto<Page<OrderProductsDto>> responseDtoWithLink(Integer page, Integer size, Method method, ResponseDto<Page<OrderProductsDto>> responseDto) {
        return null;
    }
}
