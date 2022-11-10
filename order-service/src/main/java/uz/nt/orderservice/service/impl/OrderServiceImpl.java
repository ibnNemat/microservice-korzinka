package uz.nt.orderservice.service.impl;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import shared.libs.dto.*;
import uz.nt.orderservice.entity.OrderedProductsRedis;
import uz.nt.orderservice.repository.OrderedProductsRedisRepository;
import uz.nt.orderservice.client.CashbackClient;
import uz.nt.orderservice.client.ProductClient;
import uz.nt.orderservice.client.UserCardClient;
import uz.nt.orderservice.dto.*;
import uz.nt.orderservice.entity.Orders;
import uz.nt.orderservice.scheduled.TimerTaskOrderedProducts;
import uz.nt.orderservice.service.PaymentHistoryService;
import uz.nt.orderservice.dto.OrderDto;
import uz.nt.orderservice.repository.OrderRepository;
import uz.nt.orderservice.service.OrderProductsService;
import uz.nt.orderservice.service.OrderService;
import uz.nt.orderservice.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.nt.orderservice.dto.PaymentDetails;
import shared.libs.response.standart.*;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.util.*;

import static shared.libs.response.standart.ResponseCode.*;

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
    private static ResourceBundle bundle;
    private final OrderedProductsRedisRepository redisRepository;
    private final TimerTaskOrderedProducts timerTask;

    public Map<Integer, ProductDto> buildHashMap(List<OrderedProductsDetail> list) {
        List<Integer> productIdList = new ArrayList<>();
        for (OrderedProductsDetail op : list) {
            productIdList.add(op.getProductId());
        }
        return productClient.getProductDtoList(productIdList)
                .getResponseData();
    }
    @Transactional
    @Override
    public ResponseDto<List<OrderedProductsDetail>> addOrder(List<OrderedProductsDetail> list){
        try{
            if (list == null){
                return ResponseDto.<List<OrderedProductsDetail>>builder()
                        .code(NULL_VALUE)
                        .message("OrderProducts list is null")
                        .build();
            }

            List<OrderedProductsDetail> productsNotEnoughAmount = checkProductAmount(list);
            if (productsNotEnoughAmount != null && productsNotEnoughAmount.size() > 0){
                return ResponseDto.<List<OrderedProductsDetail>>builder()
                        .code(DATABASE_ERROR)
                        .message("some products are not enough in the database")
                        .responseData(productsNotEnoughAmount)
                        .build();
            }

            ResponseDto<Integer> responseDto = addOrderIfNotExistUserOrders(list);
            Integer orderId;
            if (!responseDto.getSuccess() || responseDto.getResponseData() == null){
                return ResponseDto.<List<OrderedProductsDetail>>builder()
                        .code(SERVER_ERROR)
                        .message("Error while saving orderedProducts")
                        .build();
            }
            orderId = responseDto.getResponseData();

            List<OrderedProductsDetail> orderedProductsList = new ArrayList<>();
            for (OrderedProductsDetail op: list){
                orderedProductsList.add(new OrderedProductsDetail(
                        op.getProductId(), op.getPrice(),op.getAmount())
                );
            }

            OrderedProductsRedis orderedProductsRedis = new OrderedProductsRedis(orderId, orderedProductsList);
            redisRepository.save(orderedProductsRedis);
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDto userDto) {
                timerTask.holdingTheOrderForFifteenMinutes(orderId, userDto.getId());
            }

            return ResponseDto.<List<OrderedProductsDetail>>builder()
                    .code(OK)
                    .success(true)
                    .message("Successfully saved orderedProducts to Database")
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<List<OrderedProductsDetail>>builder()
                    .code(SERVER_ERROR)
                    .message(e.getMessage())
                    .build();
        }
    }

    public List<OrderedProductsDetail> checkProductAmount(List<OrderedProductsDetail> list){
        Map<Integer, ProductDto> map = buildHashMap(list);

        if (map == null) return null;

        List<OrderedProductsDetail> productsNotEnoughAmount = new ArrayList<>();
        for (OrderedProductsDetail op : list) {
            ProductDto productInDateBase = map.get(op.getProductId());
            if (productInDateBase.getAmount() < op.getAmount()){
                productsNotEnoughAmount.add(
                        new OrderedProductsDetail(op.getProductId(), null, productInDateBase.getAmount())
                );
            }
        }

        return productsNotEnoughAmount;
    }


    public ResponseDto<Integer> addOrderIfNotExistUserOrders(
            List<OrderedProductsDetail> orderedProductsDetails) throws Exception{
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            Integer userId;
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDto user) {
                userId = user.getId();
            }else {
                return ResponseDto.<Integer>builder()
                        .code(NOT_FOUND)
                        .message("Authorization expired")
                        .success(false)
                        .build();
            }

            Optional<Orders> optionalOrder = orderRepository
                    .findUserOrderByUserIdWherePayedIsFalse(userId);
            int orderId;

            if (optionalOrder.isPresent()){
                Orders orders = optionalOrder.get();

                orderId = orders.getId();
            }else{
                Orders orders1 = new Orders();
                orders1.setId(1);
                orders1.setUserId(userId);
                orderRepository.save(orders1);

                orderId = orderRepository.getMax();
            }

            orderProductsService.addOrderProducts(orderId, orderedProductsDetails);

            return ResponseDto.<Integer>builder()
                    .code(OK)
                    .success(true)
                    .responseData(orderId)
                    .message(bundle.getString("response.success"))
                    .build();
    }

    @Override
    public ResponseDto<OrderDto> getById(Integer id) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            Optional<Orders> optionalOrders = orderRepository.findById(id);
             if (optionalOrders.isPresent()) {
                Orders orders = optionalOrders.get();

                OrderDto orderDto = orderMapper.toDto(orders);

                return ResponseDto.<OrderDto>builder()
                        .code(OK)
                        .success(true)
                        .message(bundle.getString("response.success"))
                        .responseData(orderDto)
                        .build();
            }
            return ResponseDto.<OrderDto>builder()
                    .code(NOT_FOUND)
                    .message(bundle.getString("response.not_found"))
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<OrderDto>builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<OrderDto>> getAllOrdersByPage(Integer page, Integer size) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            if (page == null || size == null) {
                return ResponseDto.<Page<OrderDto>>builder()
                        .code(NULL_VALUE)
                        .message("Page or size is null")
                        .success(false)
                        .build();
            }
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<OrderDto> productDtoList = orderRepository.findAll(pageRequest).map(orderMapper::toDto);
            return ResponseDto.<Page<OrderDto>>builder()
                    .code(OK)
                    .success(true)
                    .message(bundle.getString("response.success"))
                    .responseData(productDtoList)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<Page<OrderDto>>builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<OrderDto>> responseDtoWithLink(Integer page, Integer size, Method method, ResponseDto<Page<OrderDto>> responseDto) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

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
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<OrderDto> updateOrder(OrderDto orderDto) {
        try{
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            Optional<Orders> optionalOrders = orderRepository.findById(orderDto.getId());
            if (optionalOrders.isPresent()) {
                Orders orders = optionalOrders.get();

                OrderDto orderDto1 = orderMapper.toDto(orders);

                return ResponseDto.<OrderDto>builder()
                        .code(OK)
                        .success(true)
                        .message(bundle.getString("response.success"))
                        .responseData(orderDto1)
                        .build();
            }

            return ResponseDto.<OrderDto>builder()
                    .code(NOT_FOUND)
                    .message(bundle.getString("response.not_found"))
                    .success(false)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<OrderDto>builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<OrderDto> deleteById(Integer id) {
        try{
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            if (orderRepository.existsById(id)) {
                orderRepository.deleteById(id);

                return ResponseDto.<OrderDto>builder()
                        .code(ResponseCode.OK)
                        .success(true)
                        .message(bundle.getString("response.success"))
                        .build();
            }

            return ResponseDto.<OrderDto>builder()
                    .code(NOT_FOUND)
                    .message(bundle.getString("response.not_found"))
                    .success(false)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<OrderDto>builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public Boolean updateOrderTotalPrice(Integer orderId, Double totalPrice) {
        try{
            orderRepository.updateOrderTotalPrice(orderId, totalPrice);
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
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            Integer userId;
            if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDto user) {
                 userId = user.getId();
            }else {
                return ResponseDto.<OrderDto>builder()
                        .code(NOT_FOUND)
                        .message("Authorization expired")
                        .success(false)
                        .build();
            }
            Orders order = orderRepository.getByUserIdAndPayedIsFalse(userId);
            if (order == null) {
                return ResponseDto.<OrderDto>builder()
                        .code(NOT_FOUND)
                        .message("User is not found!")
                        .success(false)
                        .build();
            }
            return finalPayFor(order.getId(), userId, paymentDetails);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<OrderDto>builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    private ResponseDto finalPayFor(Integer orderId, Integer userId, PaymentDetails paymentDetails){
        bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

        List<OrderedProductsDetail> orderedProducts = orderProductsService.getOrderedProductsToPayFor(orderId);

        List<OrderedProductsDetail> productsNotEnoughAmount = checkProductAmount(orderedProducts);

        if (productsNotEnoughAmount != null && productsNotEnoughAmount.size() > 0){
            return ResponseDto.<List<OrderedProductsDetail>>builder()
                    .code(NOT_FOUND)
                    .message("some products are not enough in the database")
                    .responseData(productsNotEnoughAmount)
                    .build();
        }

        Double cashbackMoney = paymentDetails.getCashbackMoney();
        CardDto cardDto = userCardClient.getCardById(paymentDetails.getCardId()).getResponseData();
        Double account = cardDto.getAccount();
        Double totalPrice = paymentDetails.getForDelivery();

        for (OrderedProductsDetail op: orderedProducts){
            totalPrice += op.getPrice() * op.getAmount();
        }

        if (totalPrice - cashbackMoney > account){
            return ResponseDto.builder()
                    .code(NOT_FOUND)
                    .success(false)
                    .message("Your balance is not enough!!!")
                    .build();
        }

        orderRepository.updateOrderPayed(userId);

        Double cardPayment = cardDto.getAccount()-(totalPrice-cashbackMoney);
        cardDto.setAccount(cardPayment);
        userCardClient.updateCard(cardDto);

        if(!updateOrderTotalPrice(orderId, totalPrice)){
            return ResponseDto.builder()
                    .code(DATABASE_ERROR)
                    .message("Error while updating total_price of order")
                    .success(false)
                    .build();
        }

        if (cashbackMoney != 0) {
             cashbackClient.subtractCashback(userId, cashbackMoney);
        }

        cashbackClient.calculateCashbackForEachShopping(userId, cashbackMoney);
        return savePaymentHistory(cardDto, cardPayment, cashbackMoney, totalPrice, userId);
    }

    public ResponseDto savePaymentHistory(CardDto cardDto, Double cardPayment, Double cashbackPayment, Double totalPrice, Integer userId){
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            PaymentHistoryDto paymentHistory = PaymentHistoryDto.builder()
                    .cardId(cardDto.getId())
                    .userId(userId)
                    .cardPayment(cardPayment)
                    .cashbackPayment(cashbackPayment)
                    .totalPrice(totalPrice)
                    .status("OK")
                    .description("Successfully payed")
                    .build();
            paymentHistoryService.addHistory(paymentHistory);

            return ResponseDto.builder()
                    .code(OK)
                    .success(true)
                    .message("Successfully Payed!")
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<UserOrderedProducts>> getAllUsersOrderProductsIsPayedFalse() {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            ArrayList<Integer> list = new ArrayList<>();
            UserDto userDto = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if(userDto == null) {
                return ResponseDto.<List<UserOrderedProducts>>builder()
                        .code(NULL_VALUE)
                        .success(false)
                        .message("UserDto is null")
                        .build();
            }

            Orders orders = orderRepository.getByUserIdAndPayedIsFalse(userDto.getId());
            if(orders == null){
                return ResponseDto.<List<UserOrderedProducts>>builder()
                        .code(NULL_VALUE)
                        .success(false)
                        .message("Order is null")
                        .build();
            }

            OrderDto orderDto = orderMapper.toDto(orders);

            for (OrderProductsDto op : orderDto.getOrderProducts()) {
                list.add(op.getOrderId());
            }
            return addToListUserOrderedProducts(list, orderDto);

        } catch (Exception i){
            log.error("GetAllUsersOrderProducts: " + i.getMessage());
            return ResponseDto.<List<UserOrderedProducts>>builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed")+ " : " + i.getMessage())
                    .success(false)
                    .build();
        }
    }
    private ResponseDto<List<UserOrderedProducts>> addToListUserOrderedProducts(ArrayList<Integer> list, OrderDto orderDto) {
        bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

        Map<Integer, ProductDto> map = productClient.getProductDtoList(list).getResponseData();
        UserOrderedProducts orderedProducts = new UserOrderedProducts();
        ArrayList<UserOrderedProducts> userOrderedProducts = new ArrayList<>();

        if (map == null){
            return ResponseDto.<List<UserOrderedProducts>>builder()
                    .code(NOT_FOUND)
                    .message("userOrderProduct is not found")
                    .success(false)
                    .build();
        }

        for (OrderProductsDto dto : orderDto.getOrderProducts()) {
            orderedProducts.setOrderId(dto.getOrderId());
            orderedProducts.setAmountOrder(dto.getAmount());
            orderedProducts.setNameProduct(map.get(dto.getProductId()).getName());
            orderedProducts.setType(map.get(dto.getProductId()).getType().getName());

            userOrderedProducts.add(orderedProducts);
        }

        return ResponseDto.<List<UserOrderedProducts>>builder()
                .code(OK)
                .success(true)
                .message(bundle.getString("response.success"))
                .responseData(userOrderedProducts)
                .build();
    }
}
