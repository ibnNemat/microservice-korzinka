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
    @Override
    public ResponseDto addOrderProducts(Integer order_id, Integer product_id, Double amount) {
        try {
            ResponseDto<Boolean> checkProductAmount = productClient.checkAmountProduct(product_id, amount);
            if (!checkProductAmount.getSuccess() || !checkProductAmount.getResponseData()){
                return ResponseDto.builder()
                        .code(-5)
                        .message("We don't have products in that many amounts!")
                        .build();
            }

            return saveOrUpdateOrderProduct(product_id, order_id, amount);
        }catch (Exception e){
            return ResponseDto.builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    private ResponseDto saveOrUpdateOrderProduct(Integer product_id, Integer order_id, Double amount) {
        Optional<OrderProducts> optional = orderProductsRepository
                .findByOrderIdAndProductId(order_id, product_id);

        OrderProducts orderProduct;
        if (optional.isPresent() && optional.get().getAmount()!= null){
            Integer orderedProductId = optional.get().getId();
            amount += optional.get().getAmount();
            orderProduct = new OrderProducts(orderedProductId, order_id, product_id, amount);
        }else {
            orderProduct = new OrderProducts(null, order_id, product_id, amount);
        }
        orderProductsRepository.save(orderProduct);

        return ResponseDto.builder()
                .code(200)
                .success(true)
                .message("Successfully saved")
                .build();
    }

    @Override
    public ResponseDto<OrderProductsDto> getById(Integer id) {
        try {
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
                    .message("Successfully get")
                    .success(true)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.<OrderProductsDto>builder()
                    .code(-1)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
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
        try {
            OrderProducts orderProducts = orderProductsMapper.toEntity(orderProductsDto);
            if (orderProductsRepository.existsById(orderProducts.getId())){

                orderProductsRepository.save(orderProducts);

                return ResponseDto.builder()
                        .code(200)
                        .message("Successfully updated")
                        .success(true)
                        .build();
            }
            return ResponseDto.builder()
                    .code(-4)
                    .message("Not found")
                    .success(false)
                    .build();
        }catch (Exception e) {
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(500)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto deleteById(Integer id) {
        try {
            if (orderProductsRepository.existsById(id)){
                orderProductsRepository.deleteById(id);

                return ResponseDto.builder()
                        .code(200)
                        .message("Successfully deleted")
                        .success(true)
                        .build();
            }

            return ResponseDto.builder()
                    .code(-4)
                    .message("Not found")
                    .success(false)
                    .build();
        } catch (Exception e){
            log.error("Error deleting 'Order Product' by id: " + e.getMessage());

            return ResponseDto.builder()
                    .code(500)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto deleteByOrderId(Integer orderId) {
        try {
            if (orderProductsRepository.existsByOrderId(orderId)) {
                orderProductsRepository.deleteByOrderId(orderId);

                return ResponseDto.builder()
                        .code(200)
                        .message("Successfully deleted")
                        .success(true)
                        .build();
            }
            return ResponseDto.builder()
                    .code(-4)
                    .message("Not found")
                    .success(false)
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());

            return ResponseDto.builder()
                    .code(500)
                    .message(e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto deleteByProductIdAndOrderId(Integer orderId, Integer productId) {
        try {
            orderProductsRepository.deleteByProductIdAndOrderId(productId, orderId);

            return ResponseDto.builder()
                    .code(200)
                    .message("Successfully deleted")
                    .success(true)
                    .build();
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseDto.builder()
                    .code(500)
                    .message(e.getMessage())
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
            Integer user_id = order.getUserId();
            Double total_price = order.getTotal_price();

            if (userOrders.containsKey(user_id)){
                userOrders.put(user_id, userOrders.get(user_id) +total_price);
            }else{
                userOrders.put(user_id, total_price);
            }
        }

        return userOrders;
    }

    @Override
    public ResponseDto<HashMap<Integer, Double>> quantityOrderedProductsPerMonth() {
        try{
            HashMap<Integer, Double> map = hashMapResponse(1);
            return ResponseDto.<HashMap<Integer, Double>>builder()
                    .code(500)
                    .success(true)
                    .responseData(map)
                    .message("OK")
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<HashMap<Integer, Double>>builder()
                    .code(500)
                    .message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<HashMap<Integer, Double>> quantityOrderedProductsPerQuarter() {
        try{
            HashMap<Integer, Double> map = hashMapResponse(3);
            return ResponseDto.<HashMap<Integer, Double>>builder()
                    .code(500)
                    .success(true)
                    .responseData(map)
                    .message("OK")
                    .build();
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseDto.<HashMap<Integer, Double>>builder()
                    .code(500)
                    .message(e.getMessage())
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
