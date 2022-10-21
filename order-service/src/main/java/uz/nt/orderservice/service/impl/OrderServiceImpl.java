package uz.nt.orderservice.service.impl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.OrderDto;
import uz.nt.orderservice.dto.OrderedProductsDetail;
import uz.nt.orderservice.entity.Order;
import uz.nt.orderservice.repository.OrderRepository;
import uz.nt.orderservice.service.OrderProductsService;
import uz.nt.orderservice.service.OrderService;
import uz.nt.orderservice.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderProductsService orderProductsService;

    @Override
    public ResponseDto addOrderIfNotExistUserOrders(Integer product_id, Integer amount) {
        Optional<Order> optionalOrder = orderRepository.findUserOrderByUserIdWherePayedIsFalse(1);
        int order_id;

        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();

            order_id = order.getId();
        }else{
            Order order1 = new Order();
            order1.setId(1);
            orderRepository.save(order1);

            order_id = orderRepository.getMax();

        }

        orderProductsService.addOrderProducts(order_id, amount, product_id);

        return ResponseDto.builder()
                .code(200)
                .success(true)
                .message("Successfully saved")
                .build();
    }

    @Override
    public ResponseDto<OrderDto> getById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto<Page<OrderDto>> getAllOrdersByPage(Integer page, Integer size) {
        try {
            if (page == null || size == null) {
                return ResponseDto.<Page<OrderDto>>builder()
                        .code(-100)
                        .message("Page or size is null")
                        .build();
            }
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<OrderDto> productDtoList = orderRepository.findAll(pageRequest).map(orderMapper::toDto);
            return ResponseDto.<Page<OrderDto>>builder()
                    .code(200)
                    .success(true)
                    .message("OK")
                    .responseData(productDtoList)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<Page<OrderDto>>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<OrderDto>> responseDtoWithLink(Integer page, Integer size, Method method, ResponseDto<Page<OrderDto>> responseDto) {
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
            return ResponseDto.<Page<OrderDto>>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto updateOrder(OrderDto orderDto) {
        return null;
    }

    @Override
    public ResponseDto deleteById(Integer id) {
        return null;
    }

    @Override
    public ResponseDto payForOrders(Integer user_id, Double account) {
        Integer orderId = orderRepository.getByUser_idAndPayedIsFalse(user_id);

        if (orderId == null){
            return ResponseDto.builder()
                    .code(-2343)
                    .success(false)
                    .message("User is not found!")
                    .build();
        }
        List<OrderedProductsDetail> orderedProducts = orderProductsService.getOrderedProductsToPayFor(orderId);

        Double total_price = 0D;
        for (OrderedProductsDetail op: orderedProducts){
            total_price += op.getPrice()*op.getAmount();
        }

        if (total_price > account){
            return ResponseDto.builder()
                    .code(-2)
                    .success(false)
                    .message("Your balance is not enough!!!")
                    .build();
        }

        orderRepository.updateOrderPayed(user_id);
//        UserService.updateUserAccount(user_id, total_price);
        return ResponseDto.builder()
                .code(200)
                .success(true)
                .message("Successfully ordered!")
                .build();
    }
}
