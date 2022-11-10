package uz.nt.orderservice.service.impl;
import shared.libs.response.standart.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import shared.libs.dto.ResponseDto;
import shared.libs.utils.MyDateUtil;
import uz.nt.orderservice.client.ProductClient;
import uz.nt.orderservice.dto.OrderProductsDto;
import shared.libs.dto.OrderedProductsDetail;
import uz.nt.orderservice.entity.OrderProducts;
import uz.nt.orderservice.entity.Orders;
import uz.nt.orderservice.repository.OrderProductsRepository;
import uz.nt.orderservice.repository.OrderRepository;
import uz.nt.orderservice.repository.OrderedProductsRedisRepository;
import uz.nt.orderservice.repository.helperRepository.OrderProductRepositoryHelper;
import uz.nt.orderservice.service.OrderProductsService;
import uz.nt.orderservice.service.mapper.OrderProductsMapper;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static shared.libs.response.standart.ResponseCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProductsServiceImpl implements OrderProductsService {
    private final OrderProductsRepository orderProductsRepository;
    private final OrderProductsMapper orderProductsMapper;
    private final ProductClient productClient;
    private final OrderProductRepositoryHelper orderProductRepositoryHelper;
    private final OrderRepository orderRepository;
    private static ResourceBundle bundle;
    private final OrderedProductsRedisRepository redisRepository;

    @Override
    public ResponseDto addOrderProducts(Integer orderId, List<OrderedProductsDetail> orderedProductsDetails) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            return saveOrUpdateOrderProduct(orderId, orderedProductsDetails);
        }catch (Exception e){
            return ResponseDto.builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    private ResponseDto saveOrUpdateOrderProduct(Integer orderId, List<OrderedProductsDetail> orderedProductsDetails) {
        bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());
        Orders order = orderRepository.findById(orderId).get();
        List<OrderProducts> orderProductsInDateBase = order.getOrderProducts();

        Map<Integer, OrderProducts> orderProductMap = new HashMap<>();
        for (OrderProducts op: orderProductsInDateBase){
            orderProductMap.put(op.getProductId(), op);
        }

        OrderProducts orderProducts;
        for (OrderedProductsDetail op: orderedProductsDetails){
            if (orderProductMap.containsKey(op.getProductId())){
                orderProducts = orderProductMap.get(op.getProductId());
                orderProducts.setAmount(orderProducts.getAmount()+op.getAmount());
            }else{
                orderProducts = new OrderProducts(null, orderId, op.getProductId(), op.getAmount());
            }
            orderProductsRepository.save(orderProducts);

            // TODO: Map<productId, amount> yasab parametriga shu mapni qaytaradigan
            //  qilib o'zgartirish kerak, fordan tashqarida bo'ladi shunda
            productClient.subtractAmount(op.getProductId(), op.getAmount());
        }

        return ResponseDto.builder()
                .code(OK)
                .success(true)
                .message(bundle.getString("response.success"))
                .build();
    }

    @Override
    public ResponseDto<OrderProductsDto> getById(Integer id) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            Optional<OrderProducts> orderProducts = orderProductsRepository.findById(id);

            if (orderProducts.isEmpty()){
                return ResponseDto.<OrderProductsDto>builder()
                        .code(NOT_FOUND)
                        .message("Order Product not found")
                        .success(false)
                        .build();
            }
            OrderProductsDto orderProductsDto = orderProductsMapper.toDto(orderProducts.get());

            return ResponseDto.<OrderProductsDto>builder()
                    .code(OK)
                    .responseData(orderProductsDto)
                    .message(bundle.getString("response.success"))
                    .success(true)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<OrderProductsDto>builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed")+ " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<OrderProductsDto>> getAllOrderProductsByPage(Integer page, Integer size) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            if (page == null || size == null) {
                return ResponseDto.<Page<OrderProductsDto>>builder()
                        .code(NULL_VALUE)
                        .message("Page or size is null")
                        .build();
            }
            PageRequest pageRequest = PageRequest.of(page, size);

            Page<OrderProductsDto> productDtoList = orderProductsRepository.findAll(pageRequest)
                    .map(orderProductsMapper::toDto);

            return ResponseDto.<Page<OrderProductsDto>>builder()
                    .code(OK)
                    .success(true)
                    .message(bundle.getString("response.success"))
                    .responseData(productDtoList)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<Page<OrderProductsDto>>builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto updateOrderProducts(OrderProductsDto orderProductsDto) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            OrderProducts orderProducts = orderProductsMapper.toEntity(orderProductsDto);
            if (orderProductsRepository.existsById(orderProducts.getId())){

                orderProductsRepository.save(orderProducts);

                return ResponseDto.builder()
                        .code(OK)
                        .message(bundle.getString("response.success"))
                        .success(true)
                        .build();
            }
            return ResponseDto.builder()
                    .code(NOT_FOUND)
                    .message(bundle.getString("response.not_found"))
                    .success(false)
                    .build();
        }catch (Exception e) {
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(-1)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto deleteById(Integer id) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            if (orderProductsRepository.existsById(id)){
                orderProductsRepository.deleteById(id);

                return ResponseDto.builder()
                        .code(OK)
                        .message(bundle.getString("response.success"))
                        .success(true)
                        .build();
            }

            return ResponseDto.builder()
                    .code(ResponseCode.NOT_FOUND)
                    .message(bundle.getString("response.not_found"))
                    .success(false)
                    .build();
        } catch (Exception e){
            log.error("Error deleting 'Order Product' by id: " + e.getMessage());

            return ResponseDto.builder()
                    .code(ResponseCode.DATABASE_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto deleteByOrderId(Integer orderId) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            if (orderProductsRepository.existsByOrderId(orderId)) {
                orderProductsRepository.deleteByOrderId(orderId);

                return ResponseDto.builder()
                        .code(OK)
                        .message(bundle.getString("response.deleted"))
                        .success(true)
                        .build();
            }
            return ResponseDto.builder()
                    .code(ResponseCode.NOT_FOUND)
                    .message(bundle.getString("response.not_found"))
                    .success(false)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(ResponseCode.DATABASE_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto deleteByProductIdAndOrderId(Integer orderId, Integer productId) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            orderProductsRepository.deleteByProductIdAndOrderId(productId, orderId);

            return ResponseDto.builder()
                    .code(OK)
                    .message(bundle.getString("response.deleted"))
                    .success(true)
                    .build();
        }catch (Exception e) {
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(ResponseCode.DATABASE_ERROR)
                    .message(bundle.getString("response.failed")+ " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    public HashMap<Integer, Double> hashMapResponse(Integer monthlyOrQuarterly) throws Exception {
        Date date = new Date();
        LocalDate localDate = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(date));
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonth().getValue();
        int year = localDate.getYear();

        if (monthlyOrQuarterly == 1){
            if (month == 1){
                year -= year;
                month = 12;
            }else {
                month -= 1;
            }
        }else{
            if (month < 4){
                month += 9;
                year -=year;
            }
        }
        String stringDate = year + "-" + month + "-" + day;
        Date date1 = MyDateUtil.parseToDate(stringDate);

        java.sql.Date fromDate = new java.sql.Date(date1.getTime());
        java.sql.Date currentDate = new java.sql.Date(date.getTime());

        List<Orders> ordersList = orderRepository.userPayedOrderedProducts(fromDate, currentDate);

        return sumAllOrderedProductUsers(ordersList);
    }

    public HashMap<Integer, Double> sumAllOrderedProductUsers(List<Orders> ordersList){

        HashMap<Integer, Double> userOrders = new HashMap<>();
        for (Orders order: ordersList){
            Integer userId = order.getUserId();
            Double totalPrice = order.getTotalPrice();

            if (userOrders.containsKey(userId)){
                userOrders.put(userId, userOrders.get(userId) + totalPrice);
            }else{
                userOrders.put(userId, totalPrice);
            }
        }

        return userOrders;
    }

    @Override
    public ResponseDto<HashMap<Integer, Double>> quantityOrderedProductsPerMonth() {
        try{
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            HashMap<Integer, Double> map = hashMapResponse(1);
            return ResponseDto.<HashMap<Integer, Double>>builder()
                    .code(OK)
                    .success(true)
                    .responseData(map)
                    .message(bundle.getString("response.success"))
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<HashMap<Integer, Double>>builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<HashMap<Integer, Double>> quantityOrderedProductsPerQuarter() {
        try{
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            HashMap<Integer, Double> map = hashMapResponse(3);
            return ResponseDto.<HashMap<Integer, Double>>builder()
                    .code(OK)
                    .success(true)
                    .responseData(map)
                    .message(bundle.getString("response.success"))
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<HashMap<Integer, Double>>builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public List<OrderedProductsDetail> getOrderedProductsToPayFor(Integer order_id) {
        List<OrderedProductsDetail> orderedProductDetails = orderProductRepositoryHelper.getOrderedProductDetails(order_id);
        return orderedProductDetails;
    }

    @Override
    public ResponseDto<Page<OrderProductsDto>> responseDtoWithLink(Integer page, Integer size, Method method, ResponseDto<Page<OrderProductsDto>> responseDto) {
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
            return ResponseDto.<Page<OrderProductsDto>>builder()
                    .code(SERVER_ERROR)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }
}
