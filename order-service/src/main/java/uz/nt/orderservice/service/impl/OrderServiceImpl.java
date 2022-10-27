package uz.nt.orderservice.service.impl;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import shared.libs.dto.CardDto;
import shared.libs.dto.ProductDto;
import shared.libs.dto.UserDto;
import uz.nt.orderservice.client.CashbackClient;
import uz.nt.orderservice.client.ProductClient;
import uz.nt.orderservice.client.UserCardClient;
import uz.nt.orderservice.dto.*;
import uz.nt.orderservice.entity.Orders;
import uz.nt.orderservice.service.PaymentHistoryService;
import shared.libs.dto.ResponseDto;
import uz.nt.orderservice.dto.OrderDto;
import uz.nt.orderservice.dto.OrderedProductsDetail;
import uz.nt.orderservice.entity.PaymentHistory;
import uz.nt.orderservice.repository.OrderRepository;
import uz.nt.orderservice.service.OrderProductsService;
import uz.nt.orderservice.service.OrderService;
import uz.nt.orderservice.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.nt.orderservice.dto.PaymentDetails;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderProductsService orderProductsService;
    private final PaymentHistoryService paymentHistoryService;
    private final UserCardClient userCardClient;
    private final CashbackClient cashbackClient;

    private final ProductClient productClient;


    @Override
    @Transactional
    public ResponseDto<OrderDto> addOrderIfNotExistUserOrders(Integer product_id, Double amount) {
        try{
            UserDto userDto = (UserDto) SecurityContextHolder.getContext()
                            .getAuthentication()
                            .getPrincipal();

            Optional<Orders> optionalOrder = orderRepository
                    .findUserOrderByUserIdWherePayedIsFalse(userDto.getId());
            int order_id;

            if (optionalOrder.isPresent()){
                Orders orders = optionalOrder.get();

                order_id = orders.getId();
            }else{
                Orders orders1 = new Orders();
                orders1.setId(1);
                orders1.setUserId(userDto.getId());
                orderRepository.save(orders1);

                order_id = orderRepository.getMax();
            }

            orderProductsService.addOrderProducts(order_id, product_id, amount);

            return ResponseDto.<OrderDto>builder()
                    .code(200)
                    .success(true)
                    .message("Successfully saved")
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<OrderDto>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<OrderDto> getById(Integer id) {
        try {
            Optional<Orders> optionalOrders = orderRepository.findById(id);
             if (optionalOrders.isPresent()) {
                Orders orders = optionalOrders.get();

                OrderDto orderDto = orderMapper.toDto(orders);

                return ResponseDto.<OrderDto>builder()
                        .code(200)
                        .success(true)
                        .message("OK")
                        .responseData(orderDto)
                        .build();
            }
            return ResponseDto.<OrderDto>builder()
                    .code(-4)
                    .message("Not found")
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<OrderDto>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
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
    public ResponseDto<OrderDto> updateOrder(OrderDto orderDto) {
        try{
            Optional<Orders> optionalOrders = orderRepository.findById(orderDto.getId());
            if (optionalOrders.isPresent()) {
                Orders orders = optionalOrders.get();

                OrderDto orderDto1 = orderMapper.toDto(orders);

                return ResponseDto.<OrderDto>builder()
                        .code(200)
                        .success(true)
                        .message("Successfully updated")
                        .responseData(orderDto1)
                        .build();
            }

            return ResponseDto.<OrderDto>builder()
                    .code(-4)
                    .message("Not found")
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<OrderDto>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<OrderDto> deleteById(Integer id) {
        try{
            if (orderRepository.existsById(id)) {
                orderRepository.deleteById(id);

                return ResponseDto.<OrderDto>builder()
                        .code(200)
                        .success(true)
                        .message("Successfully deleted")
                        .build();
            }

            return ResponseDto.<OrderDto>builder()
                    .code(-4)
                    .message("Not found")
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<OrderDto>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public Boolean updateOrderTotalPrice(Integer order_id, Double total_price) {
        try{
            orderRepository.updateOrderTotalPrice(order_id, total_price);
            return true;

        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    @Transactional
    public ResponseDto<OrderDto> payForOrders(PaymentDetails paymentDetails) {
        try{
            Integer user_id;
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDto user) {
                 user_id = user.getId();
            }else {
                return ResponseDto.<OrderDto>builder()
                        .code(-3)
                        .message("Authorization expired")
                        .build();
            }
            Orders orderId = orderRepository.getByUserIdAndPayedIsFalse(user_id);
            if (orderId == null) {
                return ResponseDto.<OrderDto>builder()
                        .code(-2343)
                        .message("User is not found!")
                        .build();
            }
            return finalPayFor(orderId.getId(), user_id, paymentDetails);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<OrderDto>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    private ResponseDto finalPayFor(Integer orderId, Integer user_id, PaymentDetails paymentDetails){
        List<OrderedProductsDetail> orderedProducts = orderProductsService.getOrderedProductsToPayFor(orderId);
        Double cashback_money = paymentDetails.getCashbackMoney();
        CardDto cardDto = userCardClient.getCardById(paymentDetails.getCardId()).getResponseData();
        Double account = cardDto.getAccount();
        Double total_price = paymentDetails.getForDelivery();

        for (OrderedProductsDetail op: orderedProducts){
            total_price += op.getPrice() * op.getAmount();
        }

        if (total_price-cashback_money > account){
            return ResponseDto.builder()
                    .code(-2)
                    .success(false)
                    .message("Your balance is not enough!!!")
                    .build();
        }

        orderRepository.updateOrderPayed(user_id);

        Double card_payment = cardDto.getAccount()-(total_price-cashback_money);
        cardDto.setAccount(card_payment);
        userCardClient.updateCard(cardDto);

        if(!updateOrderTotalPrice(orderId, total_price)){
            return ResponseDto.builder()
                    .code(500)
                    .message("Error while updating total_price of order")
                    .build();
        }

//        ResponseDto<Boolean> responseDto = productClient.update(product_id, amount);
//        return ResponseDto.builder()
//                .code(-5)
//                .message("We don't have products in that many amounts!")
//                .build();

        if (cashback_money != 0) {
             cashbackClient.subtractCashback(user_id, cashback_money);
        }

        cashbackClient.calculateCashbackForEachShopping(user_id, cashback_money);
        return savePaymentHistory(cardDto, card_payment, cashback_money, total_price, user_id);
    }

    public ResponseDto savePaymentHistory(CardDto cardDto, Double card_payment, Double cashback_payment, Double total_price, Integer user_id){
        try {
            PaymentHistoryDto paymentHistory = PaymentHistoryDto.builder()
                    .card_id(cardDto.getId())
                    .user_id(user_id)
                    .card_payment(card_payment)
                    .cashback_payment(cashback_payment)
                    .total_price(total_price)
                    .status("OK")
                    .description("Successfully payed")
                    .build();
            paymentHistoryService.addHistory(paymentHistory);

            return ResponseDto.builder()
                    .code(200)
                    .success(true)
                    .message("Successfully Payed!")
                    .build();
        }catch (Exception e){
            return ResponseDto.builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    public Double sumAllOfUserOrderedProductsMonthly(){
        return null;
    }

    @Override
    public ResponseDto<List<UserOrderedProducts>> getAllUsersOrderProductsIsPayedFalse() {
        try {
            ArrayList<Integer> list = new ArrayList<>();
            if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
                return ResponseDto.<List<UserOrderedProducts>>builder()
                        .code(-4)
                        .success(false)
                        .message("SecurityContextHolder getPrincipal isNull")
                        .build();
            }
            UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Orders orders = orderRepository.getByUserIdAndPayedIsFalse(userDto.getId());
            if(orders == null){
                return ResponseDto.<List<UserOrderedProducts>>builder()
                        .code(-4)
                        .success(false)
                        .message("Orders isNull")
                        .build();
            }

            OrderDto orderDto = orderMapper.toDto(orders);

            for (OrderProductsDto op : orderDto.getOrderProducts()) {
                list.add(op.getOrder_id());
            }
            return addToListUserOrderedProducts(list, orderDto);

        } catch (Exception i){
            log.error("GetAllUsersOrderProducts: " + i);
            return ResponseDto.<List<UserOrderedProducts>>builder()
                    .code(-1)
                    .message(i.getMessage())
                    .success(false)
                    .build();
        }
    }
    private ResponseDto<List<UserOrderedProducts>> addToListUserOrderedProducts(ArrayList<Integer> list, OrderDto orderDto) {
        Map<Integer, ProductDto> map = productClient.getShownDtoList(list).getResponseData();
        UserOrderedProducts orderedProducts = new UserOrderedProducts();
        ArrayList<UserOrderedProducts> userOrderedProducts = new ArrayList<>();

        if (map == null){
            return ResponseDto.<List<UserOrderedProducts>>builder()
                    .code(-1)
                    .message("userOrderProduct is not found")
                    .success(false)
                    .build();
        }

        for (OrderProductsDto dto : orderDto.getOrderProducts()) {
            orderedProducts.setOrderId(dto.getOrder_id());
            orderedProducts.setAmountOrder(dto.getAmount());
            orderedProducts.setNameProduct(map.get(dto.getProduct_id()).getName());
            orderedProducts.setType(map.get(dto.getProduct_id()).getType().getName());

            userOrderedProducts.add(orderedProducts);
        }

        return ResponseDto.<List<UserOrderedProducts>>builder()
                .code(0)
                .success(true)
                .message("OK")
                .responseData(userOrderedProducts)
                .build();
    }


}
