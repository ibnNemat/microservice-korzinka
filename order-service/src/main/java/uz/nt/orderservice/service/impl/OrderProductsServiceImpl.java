package uz.nt.orderservice.service.impl;
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
import uz.nt.orderservice.dto.OrderedProductsDetail;
import uz.nt.orderservice.entity.OrderProducts;
import uz.nt.orderservice.entity.Orders;
import uz.nt.orderservice.repository.OrderProductsRepository;
import uz.nt.orderservice.repository.OrderRepository;
import uz.nt.orderservice.repository.helperRepository.OrderProductRepositoryHelper;
import uz.nt.orderservice.service.OrderProductsService;
import uz.nt.orderservice.service.OrderService;
import uz.nt.orderservice.service.mapper.OrderProductsMapper;

import javax.transaction.Transactional;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProductsServiceImpl implements OrderProductsService {
    private final OrderProductsRepository orderProductsRepository;
    private final OrderProductsMapper orderProductsMapper;
    private final ProductClient productClient;
    private final OrderProductRepositoryHelper orderProductRepositoryHelper;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private static ResourceBundle bundle;

    @Transactional
    @Override
    public ResponseDto addOrderProducts(Integer orderId, Integer productId, Double amount) {
        try {
            bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

            ResponseDto<Boolean> checkProductAmount = productClient.checkAmountProduct(productId, amount);
            if (!checkProductAmount.getSuccess() || !checkProductAmount.getResponseData()){
                return ResponseDto.builder()
                        .code(-5)
                        .message("We don't have products in that many amounts!")
                        .success(false)
                        .build();
            }

            return saveOrUpdateOrderProduct(productId, orderId, amount);
        }catch (Exception e){
            return ResponseDto.builder()
                    .code(-1)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    private ResponseDto saveOrUpdateOrderProduct(Integer productId, Integer orderId, Double amount) {
        bundle = ResourceBundle.getBundle("message", LocaleContextHolder.getLocale());

        Optional<OrderProducts> optional = orderProductsRepository
                .findByOrderIdAndProductId(orderId, productId);

        OrderProducts orderProduct;
        if (optional.isPresent() && optional.get().getAmount()!= null){
            Integer orderedProductId = optional.get().getId();
            amount += optional.get().getAmount();
            orderProduct = new OrderProducts(orderedProductId, orderId, productId, amount);
        }else {
            orderProduct = new OrderProducts(null, orderId, productId, amount);
        }
        orderProductsRepository.save(orderProduct);

        return ResponseDto.builder()
                .code(0)
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
                        .code(-4)
                        .message("Order Product not found")
                        .success(false)
                        .build();
            }
            OrderProductsDto orderProductsDto = orderProductsMapper.toDto(orderProducts.get());

            return ResponseDto.<OrderProductsDto>builder()
                    .code(0)
                    .responseData(orderProductsDto)
                    .message(bundle.getString("response.success"))
                    .success(true)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<OrderProductsDto>builder()
                    .code(-1)
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
                        .code(-100)
                        .message("Page or size is null")
                        .build();
            }
            PageRequest pageRequest = PageRequest.of(page, size);

            Page<OrderProductsDto> productDtoList = orderProductsRepository.findAll(pageRequest)
                    .map(orderProductsMapper::toDto);

            return ResponseDto.<Page<OrderProductsDto>>builder()
                    .code(0)
                    .success(true)
                    .message(bundle.getString("response.success"))
                    .responseData(productDtoList)
                    .build();

        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<Page<OrderProductsDto>>builder()
                    .code(-1)
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
                        .code(0)
                        .message(bundle.getString("response.success"))
                        .success(true)
                        .build();
            }
            return ResponseDto.builder()
                    .code(-4)
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
                        .code(0)
                        .message(bundle.getString("response.success"))
                        .success(true)
                        .build();
            }

            return ResponseDto.builder()
                    .code(-4)
                    .message(bundle.getString("response.not_found"))
                    .success(false)
                    .build();
        } catch (Exception e){
            log.error("Error deleting 'Order Product' by id: " + e.getMessage());

            return ResponseDto.builder()
                    .code(-1)
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
                        .code(0)
                        .message(bundle.getString("response.deleted"))
                        .success(true)
                        .build();
            }
            return ResponseDto.builder()
                    .code(-4)
                    .message(bundle.getString("response.not_found"))
                    .success(false)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(-1)
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
                    .code(0)
                    .message(bundle.getString("response.deleted"))
                    .success(true)
                    .build();
        }catch (Exception e) {
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(-1)
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
                    .code(0)
                    .success(true)
                    .responseData(map)
                    .message(bundle.getString("response.success"))
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<HashMap<Integer, Double>>builder()
                    .code(-1)
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
                    .code(0)
                    .success(true)
                    .responseData(map)
                    .message(bundle.getString("response.success"))
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<HashMap<Integer, Double>>builder()
                    .code(-1)
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
                    .code(-1)
                    .message(bundle.getString("response.failed") + " : " + e.getMessage())
                    .success(false)
                    .build();
        }
    }
}
