package uz.nt.orderservice.service.impl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.OrderProductsDto;
import uz.nt.orderservice.dto.OrderedProductsDetail;
import uz.nt.orderservice.entity.OrderProducts;
import uz.nt.orderservice.repository.OrderProductsRepository;
import uz.nt.orderservice.repository.helperRepository.OrderProductRepositoryHelper;
import uz.nt.orderservice.service.OrderProductsService;
import uz.nt.orderservice.service.mapper.OrderProductsMapper;
import uz.nt.productservice.service.ProductService;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProductsServiceImpl implements OrderProductsService {
    private final OrderProductsRepository orderProductsRepository;
    private final OrderProductsMapper orderProductsMapper;
    private final ProductService productService;
    private final OrderProductRepositoryHelper orderProductRepositoryHelper;
    @Override
    public ResponseDto addOrderProducts(Integer order_id, Integer product_id, Integer amount) {
        Boolean isProductEnough = productService.updateAmount(product_id, amount);

        if(isProductEnough){
            OrderProducts orderProduct = new OrderProducts(null, order_id, product_id, amount);
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
        try {
            if (page == null || size == null) {
                return ResponseDto.<Page<OrderProductsDto>>builder()
                        .code(-100)
                        .message("Page or size is null")
                        .build();
            }
            PageRequest pageRequest = PageRequest.of(page, size);

            Page<OrderProductsDto> productDtoList = orderProductsRepository.findAll(pageRequest)
                    .map(orderProductsMapper::toDto);

            return ResponseDto.<Page<OrderProductsDto>>builder()
                    .code(200)
                    .success(true)
                    .message("OK")
                    .responseData(productDtoList)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<Page<OrderProductsDto>>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
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
        List<OrderedProductsDetail> orderedProductDetails = orderProductRepositoryHelper.getOrderderedProductDetails(order_id);
        return orderedProductDetails;
    }

    @Override
    public ResponseDto<Page<OrderProductsDto>> responseDtoWithLink(Integer page, Integer size, Method method, ResponseDto<Page<OrderProductsDto>> responseDto) {
        try {
            Map<String, Integer> mapParams = new HashMap<>();
            mapParams.put("page", page+1);
            mapParams.put("size", size);

            Link link = WebMvcLinkBuilder.linkTo(method)
                    .withRel(IanaLinkRelations.NEXT)
                    .expand(mapParams);
            return responseDto.add(link);

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<Page<OrderProductsDto>>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }
}
